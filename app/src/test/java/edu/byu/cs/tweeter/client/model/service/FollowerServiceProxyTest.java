package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowerServiceProxyTest {
    private FollowerRequest validRequest;
    private FollowerRequest invalidRequest;

    private FollowerResponse successResponse;
    private FollowerResponse failureResponse;

    private FollowerServiceInterfaceProxy followerServiceSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        validRequest = new FollowerRequest(currentUser, 3, null);
        invalidRequest = new FollowerRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowerResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowers(validRequest, FollowerServiceInterfaceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new FollowerResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFollowers(invalidRequest, FollowerServiceInterfaceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followerServiceSpy = Mockito.spy(new FollowerServiceInterfaceProxy());
        Mockito.when(followerServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceSpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceSpy.getFollowers(validRequest);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceSpy.getFollowers(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
