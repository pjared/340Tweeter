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
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceProxyTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private StatusServiceInterfaceProxy statusServiceSpy;

    private StatusRequest validRequest;
    private StatusRequest invalidRequest;

    private StatusResponse successResponse;
    private StatusResponse failureResponse;


    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        validRequest = new StatusRequest(user1, "123");
        invalidRequest = new StatusRequest(user1, "");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusResponse(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.postStatus(validRequest, StatusServiceInterfaceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new StatusResponse("An exception occurred");
        Mockito.when(mockServerFacade.postStatus(invalidRequest, StatusServiceInterfaceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        statusServiceSpy = Mockito.spy(new StatusServiceInterfaceProxy());
        Mockito.when(statusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void statusPostTest() throws IOException, TweeterRemoteException {
        StatusResponse response = statusServiceSpy.postStatus(validRequest);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void invalidStatusPostTest() throws IOException, TweeterRemoteException {
        StatusResponse response = statusServiceSpy.postStatus(invalidRequest);
        //Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.isSuccess());
    }
}
