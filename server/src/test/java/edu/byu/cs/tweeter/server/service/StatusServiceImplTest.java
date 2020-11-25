package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceImplTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

    private StatusServiceImpl statusServiceImplSpy;
    private StatusRequest validRequest;
    private StatusResponse successResponse;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        validRequest = new StatusRequest(user1, "123");
        successResponse = new StatusResponse(true);

        statusServiceImplSpy = Mockito.spy(StatusServiceImpl.class);
        Mockito.when(statusServiceImplSpy.postStatus(validRequest)).thenReturn(successResponse);
    }

    @Test
    public void statusPostTest() throws IOException, TweeterRemoteException {
        StatusResponse response = statusServiceImplSpy.postStatus(validRequest);
        Assertions.assertTrue(response.isSuccess());
    }
}
