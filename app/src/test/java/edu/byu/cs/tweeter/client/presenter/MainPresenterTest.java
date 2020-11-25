package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowingServiceInterface;
import edu.byu.cs.tweeter.model.service.LogoutServiceInterface;
import edu.byu.cs.tweeter.model.service.StatusServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class MainPresenterTest {
    private StatusRequest request;
    private StatusResponse response;
    private StatusServiceInterface mockStatusServiceInterface;
    private MainPresenter presenter;

    private LogoutRequest logoutRequest;
    private LogoutResponse logoutResponse;
    private LogoutServiceInterface mockLogoutServiceInterface;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        logoutRequest = new LogoutRequest(user1, new AuthToken());
        logoutResponse = new LogoutResponse("logged out");

        request = new StatusRequest(user1, "123");
        response = new StatusResponse(true);

        mockStatusServiceInterface = Mockito.mock(StatusServiceInterface.class);
        Mockito.when(mockStatusServiceInterface.postStatus(request)).thenReturn(response);

        mockLogoutServiceInterface = Mockito.mock(LogoutServiceInterface.class);
        Mockito.when(mockLogoutServiceInterface.logout(logoutRequest)).thenReturn(logoutResponse);

        presenter = Mockito.spy(new MainPresenter(new MainPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutServiceInterface);
        Mockito.when(presenter.getStatusService()).thenReturn(mockStatusServiceInterface);
    }

    @Test
    public void testPostStatus_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockStatusServiceInterface.postStatus(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.postStatus(request));
    }

    @Test
    public void testPostStatus_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockStatusServiceInterface.postStatus(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.postStatus(request);
        });
    }

    @Test
    public void testLogout_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutServiceInterface.logout(logoutRequest)).thenReturn(logoutResponse);

        Assertions.assertEquals(logoutResponse, presenter.logout(logoutRequest));
    }

    @Test
    public void testLogout_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutServiceInterface.logout(logoutRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.logout(logoutRequest);
        });
    }
}
