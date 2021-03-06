package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.presenter.UserPresenter;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class UnFollowTask extends AsyncTask<FollowRequest, Void, FollowResponse> {

    private final UserPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void unfollowSuccessful(FollowResponse feedResponse);
        void unfollowUnSuccessful(FollowResponse feedResponse);
        void handleException(Exception exception);
    }

    public UnFollowTask(UserPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowResponse doInBackground(FollowRequest... followRequests) {

        FollowResponse response = null;
        try {
            response = presenter.unFollow(followRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }
        return response;
    }

    @Override
    protected void onPostExecute(FollowResponse followResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else if(followResponse.isSuccess()) {
            observer.unfollowSuccessful(followResponse);
        } else {
            observer.unfollowUnSuccessful(followResponse);
        }
    }
}