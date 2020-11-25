package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowServiceProxyTest {
    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowServiceInterfaceProxy followServiceSpy;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user3 = new User("Chris", "Colston", MALE_IMAGE_URL);

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        //user2 follows user 3
        //user 1 does not follow user 2
        //user1 unfollows user 3
        //no set up due to dummy data

        //IS FOLLOWING-------------------------------------------------------
        String URL_PATH = user1.getAlias() + "/isfollowing" + user2.getAlias();
        validRequest = new FollowRequest(user1, user2);
        invalidRequest = new FollowRequest(null, null);
        successResponse = new FollowResponse(true, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.isFollowing(validRequest, URL_PATH)).thenReturn(successResponse);

        failureResponse = new FollowResponse("An exception occurred");
        Mockito.when(mockServerFacade.isFollowing(invalidRequest, URL_PATH)).thenReturn(failureResponse);

        //FOLLOW-------------------------------------------------------
        URL_PATH = user1.getAlias() + "/follow" + user3.getAlias();
        validRequest = new FollowRequest(user1, user3);
        successResponse = new FollowResponse(true, new AuthToken());
        Mockito.when(mockServerFacade.follow(validRequest, URL_PATH)).thenReturn(successResponse);

        //UNFOLLOW-------------------------------------------------------
        URL_PATH = user2.getAlias() + "/unfollow" + user3.getAlias();
        validRequest = new FollowRequest(user2, user3);
        successResponse = new FollowResponse(true, new AuthToken());
        Mockito.when(mockServerFacade.unFollow(validRequest, URL_PATH)).thenReturn(successResponse);

        followServiceSpy = Mockito.spy(new FollowServiceInterfaceProxy());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testIsFollowing() throws IOException, TweeterRemoteException {
        FollowRequest request = new FollowRequest(user1, user2);
        FollowResponse response = followServiceSpy.isFollowing(request);
        Assertions.assertTrue(response.isFollowing());
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceSpy.isFollowing(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testFollow() throws IOException, TweeterRemoteException {
        FollowRequest request = new FollowRequest(user1, user3);
        FollowResponse response = followServiceSpy.follow(request);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void testUnfollow() throws IOException, TweeterRemoteException {
        FollowRequest request = new FollowRequest(user2, user3);
        FollowResponse response = followServiceSpy.unFollow(request);
        Assertions.assertTrue(response.isSuccess());
    }
}
