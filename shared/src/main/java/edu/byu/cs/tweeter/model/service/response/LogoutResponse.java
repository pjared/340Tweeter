package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LogoutResponse extends Response {
    private boolean wasLoggedOut;

    public LogoutResponse(String message) {
        super(false, message);
    }

    public LogoutResponse(boolean logOut) {
        super(true, null);
        this.wasLoggedOut = logOut;
    }

    public boolean isWasLoggedOut() {
        return wasLoggedOut;
    }
}
