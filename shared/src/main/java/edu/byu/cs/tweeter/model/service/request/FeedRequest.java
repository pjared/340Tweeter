package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;

public class  FeedRequest {
    private int limit;
    private Status lastStatus;

    //THIS IS WHO THE USER IS FOLLOWING


    public FeedRequest() {
    }

    public FeedRequest(int limit, Status lastStatus) {
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }
}
