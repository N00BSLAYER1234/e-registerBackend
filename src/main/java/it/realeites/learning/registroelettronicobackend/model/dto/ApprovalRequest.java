package it.realeites.learning.registroelettronicobackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApprovalRequest", description = "Request for approval/disapproval")
public class ApprovalRequest {
    
    @Schema(description = "Tutor ID")
    private Integer tutorId;
    
    @Schema(description = "Action (approve/disapprove)")
    private String action;

    public ApprovalRequest() {
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}