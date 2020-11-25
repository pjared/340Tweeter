package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

public class FeedServiceImplTest {
    List<Status> feed;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user3 = new User("Chris", "Colston", MALE_IMAGE_URL);

    private FeedRequest validRequest;
    private FeedResponse successResponse;
    private FeedDAO mockFeedDAO;
    private FeedServiceImpl feedServiceSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        Status feed1 = new Status(user1, "Hi");
        Status feed2 = new Status(user2, "Hi 2");
        Status feed3 = new Status(user3, "Hi 3");
        feed = new ArrayList<>();
        feed.add(feed1);
        feed.add(feed2);
        feed.add(feed3);

        validRequest = new FeedRequest(3, new Status(user1, "Hi 3"));
        successResponse = new FeedResponse(Arrays.asList(feed1, feed2, feed3), false);

        mockFeedDAO = Mockito.mock(FeedDAO.class);
        Mockito.when(mockFeedDAO.getFeed(validRequest)).thenReturn(successResponse);

        feedServiceSpy = Mockito.spy(FeedServiceImpl.class);
        Mockito.when(feedServiceSpy.getFeedDAO()).thenReturn(mockFeedDAO);
    }

    @Test
    public void testFeed() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceSpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

}
