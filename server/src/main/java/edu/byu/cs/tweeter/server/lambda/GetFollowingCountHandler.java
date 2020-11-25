package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.FollowingServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.service.FeedServiceImpl;
import edu.byu.cs.tweeter.server.service.FollowingServiceImpl;

public class GetFollowingCountHandler implements RequestHandler<FollowingRequest, FollowingResponse> {
    @Override
    public FollowingResponse handleRequest(FollowingRequest request, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        return service.getFollowees(request);
    }
}
