package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class UserPresenterTest {
    private FollowRequest followRequest;
    private FollowResponse followResponse;
    private FollowRequest unFollowRequest;
    private FollowResponse unFollowResponse;
    private FollowRequest isFollowingRequest;
    private FollowResponse isFollowingResponse;
    private FollowServiceInterface mockFollowServiceInterface;
    private UserPresenter presenter;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user3 = new User("Chris", "Colston", MALE_IMAGE_URL);

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        followRequest = new FollowRequest(user1, user3);
        followResponse = new FollowResponse(true, new AuthToken());

        unFollowRequest = new FollowRequest(user2, user3);
        unFollowResponse = new FollowResponse(true, new AuthToken());

        isFollowingRequest = new FollowRequest(user1, user2);
        isFollowingResponse = new FollowResponse(true, new AuthToken());

        // Create a mock FollowingService
        mockFollowServiceInterface = Mockito.mock(FollowServiceInterface.class);
        Mockito.when(mockFollowServiceInterface.follow(followRequest)).thenReturn(followResponse);
        Mockito.when(mockFollowServiceInterface.isFollowing(isFollowingRequest)).thenReturn(isFollowingResponse);
        Mockito.when(mockFollowServiceInterface.unFollow(unFollowRequest)).thenReturn(unFollowResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new UserPresenter(new UserPresenter.View() {}));
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowServiceInterface);
    }

    @Test
    public void testFollow_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceInterface.follow(followRequest)).thenReturn(followResponse);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(followResponse, presenter.follow(followRequest));
    }

    @Test
    public void testFollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceInterface.follow(followRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.follow(followRequest);
        });
    }

    @Test
    public void testUnFollow_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceInterface.unFollow(unFollowRequest)).thenReturn(unFollowResponse);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(unFollowResponse, presenter.unFollow(unFollowRequest));
    }

    @Test
    public void testUnFollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceInterface.unFollow(unFollowRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.unFollow(unFollowRequest);
        });
    }

    @Test
    public void testIsFollow_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceInterface.isFollowing(isFollowingRequest)).thenReturn(isFollowingResponse);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(isFollowingResponse, presenter.isFollowing(isFollowingRequest));
    }

    @Test
    public void testIsFollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceInterface.isFollowing(isFollowingRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.isFollowing(isFollowingRequest);
        });
    }
}
