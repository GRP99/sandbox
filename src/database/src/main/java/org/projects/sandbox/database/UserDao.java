package org.projects.sandbox.database;

import org.projects.sandbox.model.User;
import org.projects.sandbox.model.database.DbUserFilter;
import org.projects.sandbox.model.database.DbRequest;
import org.projects.sandbox.model.database.DbResponse;

import java.util.List;

public interface UserDao {
    DbResponse<User> createUser(DbRequest<?, User> request);

    DbResponse<List<User>> findUsersByEmailOrUsername(DbRequest<DbUserFilter, ?> request);

    DbResponse<List<User>> getUsers(DbRequest<DbUserFilter, ?> request);

    DbResponse<Long> countUsers(DbRequest<DbUserFilter, ?> request);

    DbResponse<User> updateUser(DbRequest<DbUserFilter, User> request);
    DbResponse<User> deleteUser(DbRequest<DbUserFilter, ?> request);
}
