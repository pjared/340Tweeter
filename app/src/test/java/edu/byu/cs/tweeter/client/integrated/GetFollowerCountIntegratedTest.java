package edu.byu.cs.tweeter.client.integrated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowingServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class GetFollowerCountIntegratedTest {

    private FollowingServiceInterfaceProxy followingService;

    @Test
    public void testGetFollowing() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "testuser",null);

        followingService = new FollowingServiceInterfaceProxy();
        FollowingRequest validRequest = new FollowingRequest(currentUser, 10, null);
        FollowingResponse response = followingService.getFollowees(validRequest);

        Assertions.assertEquals(10, response.getFollowees().size());
    }
}
