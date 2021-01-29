package com.coding.bank.system.repository;

import java.util.Optional;

import com.coding.bank.system.models.ERole;
import com.coding.bank.system.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
