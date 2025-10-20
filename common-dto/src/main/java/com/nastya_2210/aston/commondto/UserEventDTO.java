package com.nastya_2210.aston.commondto;

public class UserEventDTO {
    private String operation;
    private String email;

    public UserEventDTO() {}

    public UserEventDTO(String email, String operation) {
        this.email = email;
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public String getEmail() {
        return email;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
