package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.LogoutServiceInterface;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceImpl implements LogoutServiceInterface {

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        //TODO: Finish this when implementing server
        return new LogoutResponse(true);
    }

    //FeedDAO getFeedDAO() {return new FeedDAO();}
}
