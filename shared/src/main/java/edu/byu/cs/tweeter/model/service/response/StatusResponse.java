package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusResponse extends Response {

    private boolean wasPosted;

    public StatusResponse(String message) {
        super(false, message);
    }

    public StatusResponse(boolean wasPosted) {
        super(true, null);
        this.wasPosted = wasPosted;
    }

    public boolean isWasPosted() {
        return wasPosted;
    }
}
