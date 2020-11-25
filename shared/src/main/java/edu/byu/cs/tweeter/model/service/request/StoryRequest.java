package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;

public class StoryRequest {
    private int limit;
    private Status lastStatus;

    public StoryRequest() {
    }

    //THIS IS WHO THE USER IS FOLLOWING

    public StoryRequest(int limit, Status lastStatus) {
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
