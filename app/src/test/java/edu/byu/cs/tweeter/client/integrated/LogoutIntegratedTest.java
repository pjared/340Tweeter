package edu.byu.cs.tweeter.client.integrated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.LogoutServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutIntegratedTest {
    private LogoutServiceInterfaceProxy service;

    @Test
    public void logout() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        LogoutRequest request = new LogoutRequest(user, new AuthToken());
        service = new LogoutServiceInterfaceProxy();
        LogoutResponse response = service.logout(request);

        Assertions.assertTrue(response.isWasLoggedOut());
    }
}
