package edu.byu.cs.tweeter.client.integrated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginIntegratedTest {
    private LoginServiceInterfaceProxy service;

    @Test
    public void login() throws IOException, TweeterRemoteException {
        LoginRequest request = new LoginRequest("dummyUserName", "dummyPassword");
        service = new LoginServiceInterfaceProxy();
        LoginResponse response = service.login(request);

        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        LoginResponse wantedResponse = new LoginResponse(user, new AuthToken());

        Assertions.assertEquals(wantedResponse.getUser(), response.getUser());
    }
}
