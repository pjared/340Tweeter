package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.UserServiceInterface;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

public class UserServiceImpl implements UserServiceInterface {
    @Override
    public UserResponse updateUser(UserRequest request) {
        return new UserResponse(request.getBaseUser());
    }
}
