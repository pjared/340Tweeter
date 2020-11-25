package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LoginServiceInterface;
import edu.byu.cs.tweeter.model.service.RegisterServiceInterface;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class LoginPresenterTest {

    private LoginRequest request;
    private LoginResponse response;
    private RegisterRequest registerRequest;
    private RegisterResponse registerResponse;
    private LoginServiceInterface mockLoginServiceInterface;
    private RegisterServiceInterface mockRegisterServiceInterface;
    private LoginPresenter presenter;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        request = new LoginRequest("TestUser", "123");
        response = new LoginResponse(user1, new AuthToken());

        mockLoginServiceInterface = Mockito.mock(LoginServiceInterface.class);
        Mockito.when(mockLoginServiceInterface.login(request)).thenReturn(response);

        mockRegisterServiceInterface = Mockito.mock(RegisterServiceInterface.class);
        registerRequest = new RegisterRequest("TestUser", "123", "test",
                "123", MALE_IMAGE_URL);
        registerResponse = new RegisterResponse(user1, new AuthToken());

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterServiceInterface);
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginServiceInterface);
    }

    @Test
    public void testLogin_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginServiceInterface.login(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.login(request));
    }

    @Test
    public void testLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginServiceInterface.login(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.login(request);
        });
    }

    @Test
    public void testRegister_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterServiceInterface.register(registerRequest)).thenReturn(registerResponse);

        Assertions.assertEquals(registerResponse, presenter.register(registerRequest));
    }

    @Test
    public void testRegister_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterServiceInterface.register(registerRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.register(registerRequest);
        });
    }
}
