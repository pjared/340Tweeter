package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.FollowerDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class FollowerServiceImplTest {
    private FollowerRequest validRequest;
    private FollowerResponse successResponse;
    private FollowerDAO mockFollowerDAO;
    private FollowerServiceImpl followerServiceSpy;

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
        successResponse = new FollowerResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        mockFollowerDAO = Mockito.mock(FollowerDAO.class);
        Mockito.when(mockFollowerDAO.getFollowers(validRequest)).thenReturn(successResponse);

        followerServiceSpy = Mockito.spy(FollowerServiceImpl.class);
        Mockito.when(followerServiceSpy.getFollowerDAO()).thenReturn(mockFollowerDAO);
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceSpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }
}
