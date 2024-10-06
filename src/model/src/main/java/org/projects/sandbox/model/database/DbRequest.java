package org.projects.sandbox.model.database;

public record DbRequest<F, V>(F filter, V newValue) {
}

