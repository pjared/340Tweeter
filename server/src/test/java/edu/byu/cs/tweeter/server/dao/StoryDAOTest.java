package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryDAOTest {
    private StoryDAO storyDAOSpy;
    public List<Status> story;

    private User user1 = new User("Allen", "Anderson",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    @BeforeEach
    void setup() {
        storyDAOSpy = Mockito.spy(new StoryDAO());
    }

    @Test
    public void getStoryTest() throws IOException {
        story = new ArrayList<>();
        story.add(new Status(user1, "Hi"));
        story.add(new Status(user1, "Hi 2"));
        story.add(new Status(user1, "Hi 3"));

        StoryRequest request = new StoryRequest(10, new Status(user1, "Hi"));

        StoryResponse wantedResponse = new StoryResponse(story, false);
        Mockito.when(storyDAOSpy.getStory(request)).thenReturn(wantedResponse);

        StoryResponse response = storyDAOSpy.getStory(request);

        Assertions.assertEquals(wantedResponse, response);
        Assertions.assertEquals(story, response.getStory());
    }
}
