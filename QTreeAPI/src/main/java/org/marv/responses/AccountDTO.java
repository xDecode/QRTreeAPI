package org.marv.responses;

public class AccountDTO {
    private String id;
    private String username;
    private boolean isActive;

    public AccountDTO(String id, String name, boolean isActive) {
        this.id = id;
        this.username = name;
        this.isActive = isActive;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
