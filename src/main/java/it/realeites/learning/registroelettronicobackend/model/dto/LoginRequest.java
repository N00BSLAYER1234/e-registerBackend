package it.realeites.learning.registroelettronicobackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest", description = "Request for user login")
public class LoginRequest {
    
    @Schema(description = "Username or email")
    private String username;
    
    @Schema(description = "Password")
    private String password;
    
    @Schema(description = "User type (student or tutor)")
    private String userType;

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}