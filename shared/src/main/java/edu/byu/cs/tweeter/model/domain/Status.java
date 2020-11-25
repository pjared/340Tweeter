package edu.byu.cs.tweeter.model.domain;

import java.util.Objects;

public class Status {

    private User poster;
    private String message;

    public Status() {
    }

    public Status(User poster, String message)  {
        this.poster  =  poster;
        this.message = message;
    }

    public User getPoster() {
        return poster;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return poster.equals(status.poster) &&
                message.equals(status.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poster, message);
    }

    @Override
    public String toString() {
        return "Status{" +
                "poster=" + poster +
                ", message='" + message + '\'' +
                '}';
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
