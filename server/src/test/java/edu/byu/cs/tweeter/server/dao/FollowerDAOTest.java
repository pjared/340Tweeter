package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class FollowerDAOTest {
    private FollowerDAO followerDAOSpy;

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);

    @BeforeEach
    public void setup() {
        followerDAOSpy = Mockito.spy(new FollowerDAO());
    }

    List<User> getDummyFollowing() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9);
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        FollowerRequest request = new FollowerRequest(user1, 10, null);
        FollowerResponse response = new FollowerResponse(getDummyFollowing(), false);
        Mockito.when(followerDAOSpy.getFollowers(request)).thenReturn(response);

        Assertions.assertEquals(response, followerDAOSpy.getFollowers(request));
    }

    @Test
    public void testGetFollowees() throws IOException {
        FollowerDAO newDAO = new FollowerDAO();
        FollowerRequest request = new FollowerRequest(user1, 10, null);
        FollowerResponse response = newDAO.getFollowers(request);
        Assertions.assertEquals(response, followerDAOSpy.getFollowers(request));
    }

    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException {
        FollowerRequest request = new FollowerRequest(user1, 10, null);
        FollowerResponse preResponse = new FollowerResponse(getDummyFollowing(), false);
        Mockito.when(followerDAOSpy.getFollowers(request)).thenReturn(preResponse);
        FollowerResponse response = followerDAOSpy.getFollowers(request);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageUrl());
        }
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException {
        List<User> followees = Collections.emptyList();
        FollowerRequest request = new FollowerRequest(null, 10, null);
        FollowerResponse preResponse = new FollowerResponse(followees, false);
        Mockito.when(followerDAOSpy.getFollowers(request)).thenReturn(preResponse);

        FollowerResponse response = followerDAOSpy.getFollowers(request);
        Assertions.assertEquals(0, response.getFollowers().size());
        Assertions.assertFalse(response.getHasMorePages());
    }
}
