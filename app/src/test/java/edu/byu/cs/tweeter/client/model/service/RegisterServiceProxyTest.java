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
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterServiceProxyTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private RegisterServiceInterfaceProxy registerServiceSpy;

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        validRequest = new RegisterRequest("TestUser", "123", "test",
                "123", MALE_IMAGE_URL);
        invalidRequest = new RegisterRequest("TestUser", "123", "test",
                null, MALE_IMAGE_URL);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RegisterResponse(user1, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest, RegisterServiceInterfaceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("An exception occurred");
        Mockito.when(mockServerFacade.register(invalidRequest, RegisterServiceInterfaceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        registerServiceSpy = Mockito.spy(new RegisterServiceInterfaceProxy());
        Mockito.when(registerServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void registerTest() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceSpy.register(validRequest);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void registerTestInvalidUser() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceSpy.register(invalidRequest);
        Assertions.assertFalse(response.isSuccess());
    }
}
