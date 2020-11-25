package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest {
    private User follower;
    private User following;

    public FollowRequest() {
    }

    //THIS IS WHO THE USER IS FOLLOWING

    public FollowRequest(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

    public User getFollowing() {
        return following;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
