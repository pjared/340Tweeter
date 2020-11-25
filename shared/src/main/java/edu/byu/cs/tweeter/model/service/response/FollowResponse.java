package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowResponse extends Response {

    private boolean isFollowing;

    public FollowResponse(String message) {
        super(false, message);
    }

    public FollowResponse(boolean isFollowing, AuthToken authToken) {
        super(true, null);
        this.isFollowing = isFollowing;
    }

    public boolean isFollowing() {
        return isFollowing;
    }


    public void setFollowing(boolean following) {
        isFollowing = following;
    }
}