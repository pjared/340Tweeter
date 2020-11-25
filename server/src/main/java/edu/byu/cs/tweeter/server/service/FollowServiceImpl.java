package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowServiceImpl implements FollowServiceInterface {
    @Override
    public FollowResponse isFollowing(FollowRequest request) {
        User followee = request.getFollower();
        User toFollow = request.getFollowing();

        return new FollowResponse(true, new AuthToken());
    }

    @Override
    public FollowResponse follow(FollowRequest request) {
        User followee = request.getFollower();
        User toFollow = request.getFollowing();
        return new FollowResponse(true, new AuthToken());
    }

    @Override
    public FollowResponse unFollow(FollowRequest request) {
        User followee = request.getFollower();
        User toFollow = request.getFollowing();
        return new FollowResponse(true, new AuthToken());
    }
    //FeedDAO getFeedDAO() {return new FeedDAO();}
}
