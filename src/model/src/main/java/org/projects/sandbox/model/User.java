package org.projects.sandbox.model;

import java.util.List;

public record User(
        String email,
        String password,
        String username,
        String name,
        String phone,
        String address,
        List<String> garage
) {
}
