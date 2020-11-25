package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowServiceImplTest {
    private FollowRequest validRequest;
    private FollowResponse successResponse;
    private FollowServiceImpl followServiceSpy;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        followServiceSpy = Mockito.spy(FollowServiceImpl.class);

        validRequest = new FollowRequest(user1, user2);
        successResponse = new FollowResponse(true, new AuthToken());
        Mockito.when(followServiceSpy.follow(validRequest)).thenReturn(successResponse);
        Mockito.when(followServiceSpy.unFollow(validRequest)).thenReturn(successResponse);
        Mockito.when(followServiceSpy.isFollowing(validRequest)).thenReturn(successResponse);
    }

    @Test
    public void testFollow() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceSpy.follow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testUnFollow() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceSpy.unFollow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testIsFollow() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceSpy.isFollowing(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

}
