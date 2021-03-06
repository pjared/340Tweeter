package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.StoryServiceInterface;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryServiceInterfaceProxy implements StoryServiceInterface {

    static final String URL_PATH = "/getstory";

    @Override
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException {
        StoryResponse response = getServerFacade().getStory(request, URL_PATH);
        if(response.isSuccess()) {
            loadImages(response);
        }
        return response;
    }

    private void loadImages(StoryResponse response) throws IOException {
        List<User> allUsers = new ArrayList<>();
        for(Status status: response.getStory()) {
            allUsers.add(status.getPoster());
        }
        for(User user : allUsers) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
