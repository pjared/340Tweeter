package edu.byu.cs.tweeter.model.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.Status;

public class StoryResponse extends PagedResponse {
    private List<Status> story;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(List<Status> feed, boolean hasMorePages) {
        super(true, hasMorePages);
        this.story = feed;
    }

    public List<Status> getStory() {
        return story;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryResponse that = (StoryResponse) o;
        return story.equals(that.story);
    }

    @Override
    public int hashCode() {
        return Objects.hash(story);
    }
}
