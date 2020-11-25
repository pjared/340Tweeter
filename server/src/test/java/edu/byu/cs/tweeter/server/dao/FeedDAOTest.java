package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class FeedDAOTest {
    private FeedDAO feedDAOSpy;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private Status lastStatus = new Status(user1, "Hi");

    List<Status> getDummyStatus() {
        //User should be following all these, and also see these statuses
        return Arrays.asList(new Status(user1, "Hi"),new Status(user4, "Hi 2")
                ,new Status(user5, "Hi 3"),new Status(user5, "Hi 3"),
                new Status(user5, "Hi 3"),new Status(user5, "Hi 3"),
                new Status(user5, "Hi 3"),new Status(user5, "Hi 3")
                ,new Status(user5, "Hi 3"), new Status(user1, "Hi"),new Status(user4, "Hi 2")
                ,new Status(user5, "Hi 3"), new Status(user1, "Hi"),new Status(user4, "Hi 2")
                ,new Status(user5, "Hi 3") );
    }

    @BeforeEach
    void setup() {
        feedDAOSpy = Mockito.spy(new FeedDAO());
    }

    @Test
    void testGetFeed() {
        List<Status> followees = Collections.emptyList();
        FeedRequest request = new FeedRequest(10, lastStatus);

        FeedResponse wantedResponse = new FeedResponse(getDummyStatus(), false);
        Mockito.when(feedDAOSpy.getFeed(request)).thenReturn(wantedResponse);

        FeedResponse response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(wantedResponse, response);
        /*Assertions.assertEquals(0, response.getFollowees().size());
        Assertions.assertFalse(response.getHasMorePages());*/
    }

    @Test
    void testGetFeed_noFeed() {
        List<Status> followees = Collections.emptyList();
        FeedRequest request = new FeedRequest(10, lastStatus);
        FeedResponse wantedResponse = new FeedResponse(followees, false);
        Mockito.when(feedDAOSpy.getFeed(request)).thenReturn(wantedResponse);


        FeedResponse response = feedDAOSpy.getFeed(request);

        Assertions.assertEquals(0, response.getFeed().size());
        Assertions.assertFalse(response.getHasMorePages());
    }
}
