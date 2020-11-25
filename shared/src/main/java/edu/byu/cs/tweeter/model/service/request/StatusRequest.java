package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class StatusRequest {
    private User poster;
    private String statusMessage;

    public StatusRequest() {
    }

    public StatusRequest(User poster, String statusMessage) {
        this.poster = poster;
        this.statusMessage = statusMessage;
    }

    public User getPoster() {
        return poster;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
