package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowServiceInterfaceProxy implements FollowServiceInterface {
    @Override
    public FollowResponse isFollowing(FollowRequest request) throws IOException, TweeterRemoteException {
        String URL_PATH = request.getFollower().getAlias() + "/isfollowing/" + request.getFollowing().getAlias();
        FollowResponse response = getServerFacade().isFollowing(request, URL_PATH);
        //response just returns
        return response;
    }

    @Override
    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException {
        String URL_PATH = request.getFollower().getAlias() + "/follow/" + request.getFollowing().getAlias();
        FollowResponse response = getServerFacade().follow(request, URL_PATH);
        //response should return isFollowing to true
        return response;
    }

    @Override
    public FollowResponse unFollow(FollowRequest request) throws IOException, TweeterRemoteException {
        String URL_PATH = request.getFollower().getAlias() + "/unfollow/" + request.getFollowing().getAlias();
        FollowResponse response = getServerFacade().unFollow(request, URL_PATH);
        //response should return isFollowing to false
        return response;
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
