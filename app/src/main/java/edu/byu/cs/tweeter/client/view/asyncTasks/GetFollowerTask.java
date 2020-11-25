package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;

public class GetFollowerTask extends AsyncTask<FollowerRequest, Void, FollowerResponse> {

    private final FollowerPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followeesRetrieved(FollowerResponse followingResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer  the observer who wants to be notified when this task completes.
     */
    public GetFollowerTask(FollowerPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowerResponse doInBackground(FollowerRequest... followingRequests) {
        FollowerResponse response = null;

        try {
            response = presenter.getFollower(followingRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(FollowerResponse followerResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else if(followerResponse.isSuccess()) {
            observer.followeesRetrieved(followerResponse);
        }
    }
}