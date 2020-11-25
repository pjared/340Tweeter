package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginServiceProxyTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private LoginServiceInterfaceProxy loginServiceSpy;

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        validRequest = new LoginRequest("TestUser", "123");
        invalidRequest = new LoginRequest("TestUser", "123");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginResponse(user1, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.login(validRequest, LoginServiceInterfaceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new LoginResponse("An exception occurred");
        Mockito.when(mockServerFacade.login(invalidRequest, LoginServiceInterfaceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        loginServiceSpy = Mockito.spy(new LoginServiceInterfaceProxy());
        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void loginTest() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceSpy.login(validRequest);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void loginTestInvalidUser() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceSpy.login(invalidRequest);
        Assertions.assertFalse(response.isSuccess());
    }
}
