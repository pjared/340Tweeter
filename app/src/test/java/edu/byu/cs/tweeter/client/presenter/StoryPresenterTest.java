package edu.byu.cs.tweeter.client.presenter;

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
import edu.byu.cs.tweeter.model.service.FollowingServiceInterface;
import edu.byu.cs.tweeter.model.service.StoryServiceInterface;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryPresenterTest {
    public List<Status> story;

    private User user1 = new User("Allen", "Anderson",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    private StoryRequest request;
    private StoryResponse response;
    private StoryServiceInterface mockStoryService;
    private StoryPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        story = new ArrayList<>();
        story.add(new Status(user1, "Hi"));
        story.add(new Status(user1, "Hi 2"));
        story.add(new Status(user1, "Hi 3"));

        request = new StoryRequest(10, new Status(user1, "Hi 2"));
        response = new StoryResponse(story, false);

        mockStoryService = Mockito.mock(StoryServiceInterface.class);
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.getStory(request));
    }

    @Test
    public void testGetFollowing_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockStoryService.getStory(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getStory(request);
        });
    }
}
