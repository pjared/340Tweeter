package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.FollowerServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.FollowingServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.UserServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowServiceInterface;
import edu.byu.cs.tweeter.model.service.FollowerServiceInterface;
import edu.byu.cs.tweeter.model.service.FollowingServiceInterface;
import edu.byu.cs.tweeter.model.service.UserServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

public class UserPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to edu.byu.cs.shared.edu.byu.cs.tweeter.client.model updates
    }

    public UserPresenter(View view) {
        this.view = view;
    }

    FollowerServiceInterfaceProxy getFollowerService() {
        return new FollowerServiceInterfaceProxy();
    }

    public FollowResponse follow(FollowRequest followRequest) throws IOException, TweeterRemoteException {
        FollowServiceInterface followService = getFollowService();
        return followService.follow(followRequest);
    }

    public FollowResponse unFollow(FollowRequest followRequest) throws IOException, TweeterRemoteException {
        FollowServiceInterface followService = getFollowService();
        return followService.unFollow(followRequest);
    }

    public FollowResponse isFollowing(FollowRequest followRequest) throws IOException, TweeterRemoteException {
        FollowServiceInterface followService = getFollowService();
        return followService.isFollowing(followRequest);
    }

    FollowingServiceInterface getFollowingService() {
        return new FollowingServiceInterfaceProxy();
    }

    FollowServiceInterface getFollowService() {
        return new FollowServiceInterfaceProxy();
    }

    public UserResponse updateUser(UserRequest userRequest) throws IOException, TweeterRemoteException {
        UserServiceInterface userServiceInterface = getUserServiceInterface();
        return userServiceInterface.updateUser(userRequest);
    }

    UserServiceInterface getUserServiceInterface() {
        return new UserServiceInterfaceProxy();
    }
}
