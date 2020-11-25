package edu.byu.cs.tweeter.model.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.Status;

public class FeedResponse extends PagedResponse {
    private List<Status> feed;

    public FeedResponse(String message) {
        super(false, message, false);
    }

    public FeedResponse(List<Status> feed, boolean hasMorePages) {
        super(true, hasMorePages);
        this.feed = feed;
    }

    public List<Status> getFeed() {
        return feed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedResponse that = (FeedResponse) o;
        return feed.equals(that.feed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feed);
    }

    public void setFeed(List<Status> feed) {
        this.feed = feed;
    }
}