package edu.byu.cs.tweeter.client.integrated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LogoutServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.StatusServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class PostStatusIntegratedTest {
    private StatusServiceInterfaceProxy service;

    @Test
    public void postStatus() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        StatusRequest request = new StatusRequest(user, "Hi 1");
        service = new StatusServiceInterfaceProxy();
        StatusResponse response = service.postStatus(request);

        Assertions.assertTrue(response.isWasPosted());
    }
}
