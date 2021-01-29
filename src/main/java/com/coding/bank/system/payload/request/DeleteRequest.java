package com.coding.bank.system.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DeleteRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
