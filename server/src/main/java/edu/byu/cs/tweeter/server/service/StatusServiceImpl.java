package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.service.StatusServiceInterface;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceImpl implements StatusServiceInterface {
    @Override
    public StatusResponse postStatus(StatusRequest request) {
        StatusResponse statResponse = new StatusResponse(true);
        //Find the user in the data base and then add the post to their (list of posts?)
        return statResponse;
    }
}
