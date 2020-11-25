package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceImplTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

    private LogoutRequest validRequest;
    private LogoutResponse successResponse;
    private LogoutServiceImpl logoutServiceSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        validRequest = new LogoutRequest(user1, new AuthToken());
        successResponse = new LogoutResponse("logged out");
        logoutServiceSpy = Mockito.spy(LogoutServiceImpl.class);
        Mockito.when(logoutServiceSpy.logout(validRequest)).thenReturn(successResponse);
    }

    @Test
    public void testLogout() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }
}
