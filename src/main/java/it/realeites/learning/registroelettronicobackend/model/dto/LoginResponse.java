package it.realeites.learning.registroelettronicobackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponse", description = "Response for user login")
public class LoginResponse {
    
    @Schema(description = "Success flag")
    private boolean success;
    
    @Schema(description = "Response message")
    private String message;
    
    @Schema(description = "User details")
    private UtenteDTO user;
    
    @Schema(description = "User type")
    private String userType;

    public LoginResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UtenteDTO getUser() {
        return user;
    }

    public void setUser(UtenteDTO user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}