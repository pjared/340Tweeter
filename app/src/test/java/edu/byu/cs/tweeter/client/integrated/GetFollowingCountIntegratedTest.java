package edu.byu.cs.tweeter.client.integrated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowerServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.FollowingServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class GetFollowingCountIntegratedTest {
    private FollowerServiceInterfaceProxy followingService;

    @Test
    public void testGetFollowing() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "testuser",null);

        followingService = new FollowerServiceInterfaceProxy();
        FollowerRequest validRequest = new FollowerRequest(currentUser, 10, null);
        FollowerResponse response = followingService.getFollowers(validRequest);

        Assertions.assertEquals(10, response.getFollowers().size());
    }
}
