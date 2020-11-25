package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FeedServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FeedServiceInterface;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedPresenter {
    private final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public FeedPresenter(View view) {
        this.view = view;
    }

    public FeedResponse getFeed(FeedRequest request) throws IOException, TweeterRemoteException {
        FeedServiceInterface followerService = getFeedService();
        return followerService.getFeed(request);
    }

    FeedServiceInterface getFeedService() {
        return new FeedServiceInterfaceProxy();
    }

    //Should I make a presenter class for these to implement?
}
