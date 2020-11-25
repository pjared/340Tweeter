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
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceProxyTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private LogoutServiceInterfaceProxy loginServiceSpy;

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        validRequest = new LogoutRequest(user1, new AuthToken());
        invalidRequest = new LogoutRequest(user1, new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LogoutResponse("logged out");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(validRequest, LogoutServiceInterfaceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new LogoutResponse("An exception occurred");
        Mockito.when(mockServerFacade.logout(invalidRequest, LogoutServiceInterfaceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        loginServiceSpy = Mockito.spy(new LogoutServiceInterfaceProxy());
        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void loginTest() throws IOException, TweeterRemoteException {
        LogoutResponse response = loginServiceSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void loginTestInvalidUser() throws IOException, TweeterRemoteException {
        LogoutResponse response = loginServiceSpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
