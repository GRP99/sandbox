package org.projects.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Error {
    private final String reason;
    private final String message;


    public Error(@JsonProperty(value = "reason", required = true) String reason,
                 @JsonProperty(value = "message") String message) {
        this.reason = reason;
        this.message = message;
    }

    @JsonProperty(value = "reason")
    public String reason() {
        return reason;
    }

    @JsonProperty(value = "message")
    public String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Error) obj;
        return Objects.equals(this.reason, that.reason) && Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, message);
    }

    @Override
    public String toString() {
        return "Error{" +
                "reason='" + reason + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
