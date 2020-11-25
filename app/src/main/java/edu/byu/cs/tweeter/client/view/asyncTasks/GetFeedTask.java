package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {

    private final FeedPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void feedRetrieved(FeedResponse feedResponse);
        void handleException(Exception exception);
    }

    public GetFeedTask(FeedPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {
        FeedResponse response = null;

        try {
            response = presenter.getFeed(feedRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(FeedResponse feedResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else if(feedResponse.isSuccess()) {
            observer.feedRetrieved(feedResponse);
        }
    }
}