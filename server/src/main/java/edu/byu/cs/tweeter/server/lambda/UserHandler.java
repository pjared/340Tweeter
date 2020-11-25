package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import edu.byu.cs.tweeter.server.service.UserServiceImpl;

public class UserHandler implements RequestHandler<UserRequest, UserResponse> {
    @Override
    public UserResponse handleRequest(UserRequest request, Context context) {
        UserServiceImpl service = new UserServiceImpl();
        return service.updateUser(request);
    }
}