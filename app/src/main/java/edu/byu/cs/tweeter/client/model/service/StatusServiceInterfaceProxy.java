package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.StatusServiceInterface;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceInterfaceProxy implements StatusServiceInterface {

    static final String URL_PATH = "/poststatus";

    @Override
    public StatusResponse postStatus(StatusRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        StatusResponse statusResponse = serverFacade.postStatus(request, URL_PATH);

        if(statusResponse.isSuccess()) {

        }
        return statusResponse;
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
