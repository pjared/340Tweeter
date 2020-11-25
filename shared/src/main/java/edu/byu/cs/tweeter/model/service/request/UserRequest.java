package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class UserRequest {
    User baseUser;

    public UserRequest() {
    }

    public UserRequest(User baseUser) {
        this.baseUser = baseUser;
    }

    public User getBaseUser() {
        return baseUser;
    }

    public void setBaseUser(User baseUser) {
        this.baseUser = baseUser;
    }
}
