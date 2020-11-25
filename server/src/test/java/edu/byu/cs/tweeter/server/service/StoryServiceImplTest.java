package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class StoryServiceImplTest {
    public List<Status> story;

    private User user1 = new User("Allen", "Anderson",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    private StoryServiceImpl storyServiceSpy;
    private StoryDAO mockStoryDAO;
    private StoryRequest validRequest;
    private StoryResponse successResponse;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        story = new ArrayList<>();
        story.add(new Status(user1, "Hi"));
        story.add(new Status(user1, "Hi 2"));
        story.add(new Status(user1, "Hi 3"));

        validRequest = new StoryRequest(10, new Status(user1, "Hi 2"));
        successResponse = new StoryResponse(story, false);

        mockStoryDAO = Mockito.mock(StoryDAO.class);
        Mockito.when(mockStoryDAO.getStory(validRequest)).thenReturn(successResponse);

        storyServiceSpy = Mockito.spy(StoryServiceImpl.class);
        Mockito.when(storyServiceSpy.getStoryDAO()).thenReturn(mockStoryDAO);
    }

    @Test
    public void testStory() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }
}
