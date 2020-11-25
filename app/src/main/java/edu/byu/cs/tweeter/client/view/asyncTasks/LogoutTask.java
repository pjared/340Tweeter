package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {

    private final MainPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void logoutSuccessful(LogoutResponse loginResponse);
        void logoutUnsuccessful(LogoutResponse loginResponse);
        void handleException(Exception ex);
    }

    public LogoutTask(MainPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LogoutResponse doInBackground(LogoutRequest... LogoutRequests) {
        LogoutResponse logoutResponse = null;

        try {
            logoutResponse = presenter.logout(LogoutRequests[0]);
        } catch (Exception e) {
            exception = e;
        }
        return logoutResponse;
    }

    @Override
    protected void onPostExecute(LogoutResponse logoutResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else if (logoutResponse.isSuccess()) {
            observer.logoutSuccessful(logoutResponse);
        } else {
            observer.logoutUnsuccessful(logoutResponse);
        }
    }
}