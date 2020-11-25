package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowingServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class FollowingPresenterTest {

    private FollowingRequest request;
    private FollowingResponse response;
    private FollowingServiceInterface mockFollowingServiceInterface;
    private FollowingPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1", "test1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2", "test12",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3","test123",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new FollowingRequest(currentUser, 3, null);
        response = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        // Create a mock FollowingService
        mockFollowingServiceInterface = Mockito.mock(FollowingServiceInterface.class);
        Mockito.when(mockFollowingServiceInterface.getFollowees(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FollowingPresenter(new FollowingPresenter.View() {}));
        Mockito.when(presenter.getFollowingService()).thenReturn(mockFollowingServiceInterface);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowingServiceInterface.getFollowees(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFollowing(request));
    }

    @Test
    public void testGetFollowing_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowingServiceInterface.getFollowees(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollowing(request);
        });
    }
}