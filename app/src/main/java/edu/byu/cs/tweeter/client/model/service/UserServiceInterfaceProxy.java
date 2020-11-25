package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.UserServiceInterface;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

public class UserServiceInterfaceProxy implements UserServiceInterface {

    @Override
    public UserResponse updateUser(UserRequest request) throws IOException, TweeterRemoteException {
        String URL_PATH = "/getuser/" + request.getBaseUser().getAlias();
        return getServerFacade().updateUser(request, URL_PATH);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
