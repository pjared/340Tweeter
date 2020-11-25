package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class UserResponse extends Response {
    public User user;

    public UserResponse(boolean success, String message) {
        super(success, message);
    }

    public UserResponse(User user) {
        super(true);
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
