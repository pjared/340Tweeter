package edu.byu.cs.tweeter.client.integrated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.StatusServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.UserServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.UserServiceInterface;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

public class UpdateUserIntegratedTest {
    private UserServiceInterface service;

    @Test
    public void updateUser() throws IOException, TweeterRemoteException {
        User user = new User("Test", "User", "test123",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        UserRequest request = new UserRequest(user);
        service = new UserServiceInterfaceProxy();
        UserResponse response = service.updateUser(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(user, response.getUser());
    }
}
