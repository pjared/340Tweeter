package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowerServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowerServiceInterfaceProxy implements FollowerServiceInterface {

    static final String URL_PATH = "/getfollowers";

    @Override
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException, TweeterRemoteException {
        FollowerResponse response = getServerFacade().getFollowers(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(FollowerResponse response) throws IOException {
        for(User user : response.getFollowers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
