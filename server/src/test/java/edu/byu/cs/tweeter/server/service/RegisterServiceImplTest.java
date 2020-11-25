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
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterServiceImplTest {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

    private RegisterRequest validRequest;
    private RegisterResponse successResponse;
    private RegisterServiceImpl registerServiceSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        validRequest = new RegisterRequest("TestUser", "123", "test",
                "123", MALE_IMAGE_URL);
        successResponse = new RegisterResponse(user1, new AuthToken());
        registerServiceSpy = Mockito.spy(RegisterServiceImpl.class);
        Mockito.when(registerServiceSpy.register(validRequest)).thenReturn(successResponse);
    }

    @Test
    public void testRegister() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceSpy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }
}
