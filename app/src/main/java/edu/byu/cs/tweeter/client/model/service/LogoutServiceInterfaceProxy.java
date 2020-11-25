package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LogoutServiceInterface;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceInterfaceProxy implements LogoutServiceInterface {

    static final String URL_PATH = "/logout";

    @Override
    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        LogoutResponse logoutResponse = serverFacade.logout(request, URL_PATH);

        return logoutResponse;
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
