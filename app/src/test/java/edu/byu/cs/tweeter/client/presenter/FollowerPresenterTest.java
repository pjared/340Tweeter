package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FeedServiceInterface;
import edu.byu.cs.tweeter.model.service.FollowerServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowerPresenterTest {

    private FollowerRequest request;
    private FollowerResponse response;
    private FollowerServiceInterface mockFollowerServiceInterface;
    private FollowerPresenter presenter;


    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1", "test1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2", "test12",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3","test123",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new FollowerRequest(currentUser, 3, null);
        response = new FollowerResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        mockFollowerServiceInterface = Mockito.mock(FollowerServiceInterface.class);
        Mockito.when(mockFollowerServiceInterface.getFollowers(request)).thenReturn(response);

        presenter = Mockito.spy(new FollowerPresenter(new FollowerPresenter.View() {}));
        Mockito.when(presenter.getFollowerService()).thenReturn(mockFollowerServiceInterface);
    }

    @Test
    public void testGetFeed_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowerServiceInterface.getFollowers(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.getFollower(request));
    }

    @Test
    public void testGetFollowing_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowerServiceInterface.getFollowers(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollower(request);
        });
    }
}
