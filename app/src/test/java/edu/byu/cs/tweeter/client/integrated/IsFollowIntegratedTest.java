package edu.byu.cs.tweeter.client.integrated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.FollowingServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class IsFollowIntegratedTest {
    private FollowServiceInterfaceProxy followingService;

    @Test
    public void testGetFollowing() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "testuser",null);
        User user2 = new User("FirstName", "LastName", "testuser",null);

        followingService = new FollowServiceInterfaceProxy();
        FollowRequest validRequest = new FollowRequest(currentUser, user2);
        FollowResponse response = followingService.isFollowing(validRequest);

        Assertions.assertTrue(response.isSuccess());
    }
}
