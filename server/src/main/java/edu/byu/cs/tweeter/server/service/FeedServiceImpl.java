package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.FeedServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

public class FeedServiceImpl implements FeedServiceInterface {
    @Override
    public FeedResponse getFeed(FeedRequest request) {
        return getFeedDAO().getFeed(request);
    }

    FeedDAO getFeedDAO() {return new FeedDAO();}
}
