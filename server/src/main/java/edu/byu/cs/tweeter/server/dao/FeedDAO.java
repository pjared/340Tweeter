package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedDAO {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);

    List<Status> getDummyStatus() {
        //User should be following all these, and also see these statuses
        return Arrays.asList(new Status(user1, "Hi"),new Status(user4, "Hi 2")
                ,new Status(user5, "Hi 3"),new Status(user5, "Hi 3"),
                new Status(user5, "Hi 3"),new Status(user5, "Hi 3"),
                new Status(user5, "Hi 3"),new Status(user5, "Hi 3"),
                new Status(user1, "Hi"),new Status(user4, "Hi 2")
                ,new Status(user5, "Hi 3"), new Status(user1, "Hi"),new Status(user4, "Hi 2")
                ,new Status(user5, "Hi 3"),new Status(user1, "Hi"),new Status(user4, "Hi 2")
                ,new Status(user5, "Hi 3"));
    }

    public FeedResponse getFeed(FeedRequest request) {
        //would get all statuses from following
        List<Status> allStatuses = getDummyStatus();
        List<Status> responseStatus = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFeedStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; followersIndex < allStatuses.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseStatus.add(allStatuses.get(followersIndex));
            }
            hasMorePages = followersIndex < allStatuses.size();
        }

        //return new StatusResponse(responseFollowers, hasMorePages);
        return new FeedResponse(responseStatus, hasMorePages);
    }

    private int getFeedStartingIndex(Status lastStatus, List<Status> feed) {

        int feedIndex = 0;

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
}
