package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerRequest {
    private User following;
    private int limit;
    private User lastFollower;

    //THIS IS WHO THE USER IS FOLLOWING


    public FollowerRequest() {}

    public FollowerRequest(User follower, int limit, User lastFollower) {
        this.following = follower;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public User getFollowing() {
        return following;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollower() {
        return lastFollower;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollower(User lastFollower) {
        this.lastFollower = lastFollower;
    }
}
