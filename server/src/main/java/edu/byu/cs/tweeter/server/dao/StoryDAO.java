package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryDAO {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);

    public StoryResponse getStory(StoryRequest request) {
        //would get all statuses from following
        List<Status> allStory = getDummyStory();
        List<Status> responseStatus = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFeedStartingIndex(request.getLastStatus(), allStory);

            for(int limitCounter = 0; followersIndex < allStory.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseStatus.add(allStory.get(followersIndex));
            }
            hasMorePages = followersIndex < allStory.size();
        }

        //return new StatusResponse(responseFollowers, hasMorePages);
        return new StoryResponse(responseStatus, hasMorePages);
    }

    private int getFeedStartingIndex(Status lastStatus, List<Status> feed) {

        int  feedIndex= 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < feed.size(); i++) {
                if(lastStatus.equals(feed.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    feedIndex = i + 1;
                    break;
                }
            }
        }

        return feedIndex;
    }

    List<Status> getDummyStory() {
        //User should be following all these, and also see these statuses
        return Arrays.asList(new Status(user1, "Hi"),new Status(user1, "Hi 2")
                ,new Status(user1, "Hi 3"), new Status(user1, "Hi 3")
                , new Status(user1, "Hi 4"), new Status(user1, "Hi 5"),
                new Status(user1, "Hi 3"), new Status(user1, "Hi 3"),
                new Status(user1, "Hi 4"), new Status(user1, "Hi 5"),
                new Status(user1, "Hi 3"), new Status(user1, "Hi 3"),
                new Status(user1, "Hi 4"), new Status(user1, "Hi 5"),
                new Status(user1, "Hi 4"), new Status(user1, "Hi 5"),
                new Status(user1, "Hi 4"), new Status(user1, "Hi 5"));
    }
}
