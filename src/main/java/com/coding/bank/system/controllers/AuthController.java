package com.coding.bank.system.controllers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.coding.bank.system.models.ERole;
import com.coding.bank.system.models.Role;
import com.coding.bank.system.models.User;
import com.coding.bank.system.payload.request.DeleteRequest;
import com.coding.bank.system.payload.request.LoginRequest;
import com.coding.bank.system.payload.request.SignupRequest;
import com.coding.bank.system.payload.response.JwtResponse;
import com.coding.bank.system.payload.response.MessageResponse;
import com.coding.bank.system.repository.RoleRepository;
import com.coding.bank.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.bank.system.security.jwt.JwtUtils;
import com.coding.bank.system.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private final Map<String, String> tokenMap = new ConcurrentHashMap<>();

    /**
     *
     * @param loginRequest
     * @param httpSession
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpSession httpSession) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        httpSession.setAttribute("CURRENT_LOGGED_IN_USER", loginRequest.getUsername());

        tokenMap.put(loginRequest.getUsername(), jwt);

        //SecurityContextHolder.getContext().setAuthentication(new );
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> authenticateSignOutUser(HttpServletRequest request) {
        Object currentLoggedInUser = request.getSession().getAttribute("CURRENT_LOGGED_IN_USER");
        request.setAttribute("CURRENT_LOGGED_IN_USER", null);
        tokenMap.remove(currentLoggedInUser.toString());
        return ResponseEntity.ok(new MessageResponse(currentLoggedInUser + " - User Logged out successfully!"));
    }

    /**
     *
     * @param signUpRequest
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request) {

        Object currentLoggedInUser = request.getSession().getAttribute("CURRENT_LOGGED_IN_USER");
        if (currentLoggedInUser != null) {
            Optional<User> currentUser = userRepository.findByUsername(currentLoggedInUser.toString());
            Set<Role> roleList = new HashSet<>();
            if (currentUser.isPresent()) {
                roleList = currentUser.get().getRoles();
            }
            if (!(roleList.stream().anyMatch(emp -> emp.getName().toString().matches("ROLE_ADMIN")))) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: You have not logged-in as Admin, Only Admin role can add Employee!"));
            }
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {

        Object currentLoggedInUser = request.getSession().getAttribute("CURRENT_LOGGED_IN_USER");
        if (currentLoggedInUser != null) {
            Optional<User> currentUser = userRepository.findByUsername(currentLoggedInUser.toString());
            Set<Role> roleList = new HashSet<>();
            if (currentUser.isPresent()) {
                roleList = currentUser.get().getRoles();
            }
            if (!(roleList.stream().anyMatch(emp -> emp.getName().toString().matches("ROLE_ADMIN")))) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: You have not logged-in as Admin, Only Admin role can remove Employee!"));
            }
        }
        userRepository.delete(userRepository.findByUsername(deleteRequest.getUsername()).get());
        return ResponseEntity.ok(new MessageResponse("User Deleted successfully!"));
    }
}
