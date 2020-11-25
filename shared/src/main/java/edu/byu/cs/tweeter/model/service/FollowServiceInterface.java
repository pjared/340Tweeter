package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public interface FollowServiceInterface {
    FollowResponse isFollowing(FollowRequest request) throws IOException, TweeterRemoteException;

    FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException;

    FollowResponse unFollow(FollowRequest request) throws IOException, TweeterRemoteException;
}
