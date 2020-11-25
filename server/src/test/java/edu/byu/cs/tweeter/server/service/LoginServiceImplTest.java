package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginServiceImplTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

    private LoginRequest validRequest;
    private LoginResponse successResponse;
    private LoginServiceImpl loginServiceSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        validRequest = new LoginRequest("TestUser", "123");
        successResponse = new LoginResponse(user1, new AuthToken());
        loginServiceSpy = Mockito.spy(LoginServiceImpl.class);
        Mockito.when(loginServiceSpy.login(validRequest)).thenReturn(successResponse);
    }

    @Test
    public void testLogin() throws IOException, TweeterRemoteException {

        LoginResponse response = loginServiceSpy.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }
}
