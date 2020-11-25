package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedServiceProxyTest {
    List<Status> feed;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user3 = new User("Chris", "Colston", MALE_IMAGE_URL);

    private FeedServiceInterfaceProxy feedService;

    private FeedRequest validRequest;
    private FeedRequest invalidRequest;

    private FeedResponse successResponse;
    private FeedResponse failureResponse;

    private FeedServiceInterfaceProxy feedServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        Status feed1 = new Status(user1, "Hi");
        Status feed2 = new Status(user2, "Hi 2");
        Status feed3 = new Status(user3, "Hi 3");
        feed = new ArrayList<>();
        feed.add(feed1);
        feed.add(feed2);
        feed.add(feed3);
        feedService = new FeedServiceInterfaceProxy();

        validRequest = new FeedRequest(3, new Status(user1, "Hi 3"));
        invalidRequest = new FeedRequest(0, new Status(user1, "Hi 8"));

        successResponse = new FeedResponse(Arrays.asList(feed1, feed2, feed3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest, FeedServiceInterfaceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new FeedResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFeed(invalidRequest, FeedServiceInterfaceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        feedServiceProxySpy = Mockito.spy(new FeedServiceInterfaceProxy());
        Mockito.when(feedServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testFeed() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxySpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testFeed_throwsException() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxySpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
