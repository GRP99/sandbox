package org.projects.sandbox.core;

import org.projects.sandbox.model.User;
import org.projects.sandbox.model.database.DbResponse;

import java.util.List;

public interface UserProcessor {

    User createUser(User user);

    DbResponse<User> getUserByEmailAndUsername(String email,
                                               String username);

    DbResponse<User> getUserByUsername(String username);

    DbResponse<List<User>> getUsers(Integer offset,
                                    Integer limit);

    DbResponse<Long> countAllUsers();

    DbResponse<User> updateUser(String username,
                                User user);

    DbResponse<User> deleteUser(String username);
}
