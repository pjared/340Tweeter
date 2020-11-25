package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusTask extends AsyncTask<StatusRequest, Void, StatusResponse> {

    private final MainPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void statusSuccessful(StatusResponse loginResponse);
        void statusUnsuccessful(StatusResponse loginResponse);
        void handleException(Exception ex);
    }

    public StatusTask(MainPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }
    
    @Override
    protected StatusResponse doInBackground(StatusRequest... StatusRequests) {
        StatusResponse statResponse = null;

        try {
            statResponse = presenter.postStatus(StatusRequests[0]);
        } catch (Exception e) {
            exception = e;
        }
        return statResponse;
    }

    @Override
    protected void onPostExecute(StatusResponse statResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(statResponse.isSuccess()) {
            observer.statusSuccessful(statResponse);
        } else {
            observer.statusUnsuccessful(statResponse);
        }
    }
}
