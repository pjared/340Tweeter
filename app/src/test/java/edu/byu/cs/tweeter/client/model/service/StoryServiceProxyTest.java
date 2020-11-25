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
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryServiceProxyTest {
    public List<Status> story;

    private User user1 = new User("Allen", "Anderson",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    private StoryServiceInterfaceProxy storyServiceSpy;

    private StoryRequest validRequest;
    private StoryRequest invalidRequest;

    private StoryResponse successResponse;
    private StoryResponse failureResponse;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        storyServiceSpy = new StoryServiceInterfaceProxy();
        story = new ArrayList<>();
        story.add(new Status(user1, "Hi"));
        story.add(new Status(user1, "Hi 2"));
        story.add(new Status(user1, "Hi 3"));

        validRequest = new StoryRequest(10, new Status(user1, "Hi 2"));
        invalidRequest = new StoryRequest(1000, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StoryResponse(story, false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(validRequest, StoryServiceInterfaceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new StoryResponse("An exception occurred");
        Mockito.when(mockServerFacade.getStory(invalidRequest, StoryServiceInterfaceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        storyServiceSpy = Mockito.spy(new StoryServiceInterfaceProxy());
        Mockito.when(storyServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void getStoryTest() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);
        Assertions.assertEquals(story, response.getStory());
    }

    @Test
    public void getStoryTestInvalid() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceSpy.getStory(invalidRequest);
        Assertions.assertNull(response.getStory());
    }
}
