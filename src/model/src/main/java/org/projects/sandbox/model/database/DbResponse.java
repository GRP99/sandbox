package org.projects.sandbox.model.database;

public record DbResponse<V>(DbStatus status, V value) {
}

