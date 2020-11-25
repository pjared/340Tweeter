package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.StoryServiceInterface;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class StoryServiceImpl implements StoryServiceInterface {
    @Override
    public StoryResponse getStory(StoryRequest request) {
        return getStoryDAO().getStory(request);
    }

    StoryDAO getStoryDAO() {return new StoryDAO();}
}
