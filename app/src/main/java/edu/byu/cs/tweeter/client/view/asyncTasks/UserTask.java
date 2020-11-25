package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.presenter.UserPresenter;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

public class UserTask extends AsyncTask<UserRequest, Void, UserResponse> {
    private final UserPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void handleException(Exception exception);
    }

    public UserTask(UserPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected UserResponse doInBackground(UserRequest... userRequests) {
        UserResponse response = null;
        try {
            response = presenter.updateUser(userRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }
        return response;
    }

    @Override
    protected void onPostExecute(UserResponse response) {
        if (exception != null) {
            observer.handleException(exception);
        }
    }
}
