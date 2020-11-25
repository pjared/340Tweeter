package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowerServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.FollowingServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.LogoutServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.StatusServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.UserServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowerServiceInterface;
import edu.byu.cs.tweeter.model.service.FollowingServiceInterface;
import edu.byu.cs.tweeter.model.service.LogoutServiceInterface;
import edu.byu.cs.tweeter.model.service.StatusServiceInterface;
import edu.byu.cs.tweeter.model.service.UserServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

public class MainPresenter {
    private final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public MainPresenter(View view) {
        this.view = view;
    }

    //-----------------------LOGOUT-------------------------------

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        LogoutServiceInterface logoutService = getLogoutService();
        return logoutService.logout(request);
    }

    LogoutServiceInterface getLogoutService() {
        return new LogoutServiceInterfaceProxy();
    }

    //-----------------------STATUSES-------------------------------
    public StatusResponse postStatus(StatusRequest statRequest) throws IOException, TweeterRemoteException {
        StatusServiceInterface statService = getStatusService();
        return statService.postStatus(statRequest);
    }

    StatusServiceInterface getStatusService() {
        return new StatusServiceInterfaceProxy();
    }

    //-----------------------FOLLOWING-----------------------------

    public UserResponse updateUser(UserRequest userRequest) throws IOException, TweeterRemoteException {
        UserServiceInterface userServiceInterface = getUserServiceInterface();
        return userServiceInterface.updateUser(userRequest);
    }

    UserServiceInterface getUserServiceInterface() {
        return new UserServiceInterfaceProxy();
    }
}
