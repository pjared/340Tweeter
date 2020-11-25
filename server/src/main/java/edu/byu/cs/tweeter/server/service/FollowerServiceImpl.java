package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowerServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.FollowerDAO;

public class FollowerServiceImpl implements FollowerServiceInterface {
    @Override
    public FollowerResponse getFollowers(FollowerRequest request) {
        return getFollowerDAO().getFollowers(request);
    }

    FollowerDAO getFollowerDAO() {return new FollowerDAO();}
}
