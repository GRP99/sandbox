package org.projects.sandbox.core;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.projects.sandbox.database.UserDao;
import org.projects.sandbox.model.User;
import org.projects.sandbox.model.database.DbUserFilter;
import org.projects.sandbox.model.database.DbRequest;
import org.projects.sandbox.model.database.DbResponse;
import org.projects.sandbox.model.database.DbStatus;
import org.projects.sandbox.model.database.DbUserQuery;
import org.projects.sandbox.model.exceptions.UserCreationException;

import java.util.List;

@ApplicationScoped
public class UserProcessorImpl implements UserProcessor {

    private final UserDao userDao;

    @Inject
    public UserProcessorImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        DbResponse<User> existingUserResponse = getUserByEmailAndUsername(user.email(), user.username());

        if (existingUserResponse.status() != DbStatus.NOT_FOUND) {
            throw new UserCreationException(duplicateUserMessage(user.email(), user.username()));
        }

        DbRequest<?, User> request = new DbRequest<>(null, user);
        DbResponse<User> creationResponse = userDao.createUser(request);

        if (creationResponse.status() != DbStatus.CREATED) {
            throw new UserCreationException(failedToCreateUserMessage(user.email(), user.username()));
        }

        return creationResponse.value();
    }

    @Override
    public DbResponse<User> getUserByEmailAndUsername(String email,
                                                      String username) {
        DbUserQuery query = new DbUserQuery.DbUserQueryBuilder().withEmail(email).withUsername(username).build();

        DbUserFilter filter = new DbUserFilter.DbFilterBuilder().withDbQuery(query).build();

        DbRequest<DbUserFilter, ?> request = new DbRequest<>(filter, null);

        DbResponse<List<User>> response = userDao.findUsersByEmailOrUsername(request);

        return response.value().isEmpty()
                ? new DbResponse<>(DbStatus.NOT_FOUND, null)
                : new DbResponse<>(response.status(), response.value().getFirst());
    }

    @Override
    public DbResponse<User> getUserByUsername(String username) {
        DbUserQuery query = new DbUserQuery.DbUserQueryBuilder()
                .withUsername(username)
                .build();

        DbUserFilter filter = new DbUserFilter.DbFilterBuilder()
                .withDbQuery(query)
                .build();

        DbRequest<DbUserFilter, ?> request = new DbRequest<>(filter, null);

        DbResponse<List<User>> response = userDao.getUsers(request);

        return response.value().isEmpty()
                ? new DbResponse<>(DbStatus.NOT_FOUND, null)
                : new DbResponse<>(response.status(), response.value().getFirst());
    }

    @Override
    public DbResponse<List<User>> getUsers(Integer offset,
                                           Integer limit) {
        DbUserFilter filter = new DbUserFilter.DbFilterBuilder()
                .withLimit(limit)
                .withOffset(offset)
                .build();

        DbRequest<DbUserFilter, ?> request = new DbRequest<>(filter, null);

        return userDao.getUsers(request);
    }

    @Override
    public DbResponse<Long> countAllUsers() {
        DbUserFilter filter = new DbUserFilter.DbFilterBuilder().build();

        DbRequest<DbUserFilter, ?> request = new DbRequest<>(filter, null);

        return userDao.countUsers(request);
    }

    @Override
    public DbResponse<User> updateUser(String username,
                                       User user) {
        DbUserQuery query = new DbUserQuery.DbUserQueryBuilder()
                .withUsername(username)
                .build();

        DbUserFilter filter = new DbUserFilter.DbFilterBuilder()
                .withDbQuery(query)
                .build();

        DbRequest<DbUserFilter, User> request = new DbRequest<>(filter, user);

        return userDao.updateUser(request);
    }

    @Override
    public DbResponse<User> deleteUser(String username) {
        DbUserQuery query = new DbUserQuery.DbUserQueryBuilder()
                .withUsername(username)
                .build();

        DbUserFilter filter = new DbUserFilter.DbFilterBuilder()
                .withDbQuery(query)
                .build();

        DbRequest<DbUserFilter, ?> request = new DbRequest<>(filter, null);

        DbResponse<User> response = userDao.deleteUser(request);

        return response.value() == null
                ? new DbResponse<>(DbStatus.NOT_FOUND, null)
                : new DbResponse<>(response.status(), response.value());
    }

    private String duplicateUserMessage(String email,
                                        String username) {
        return String.format("Cannot create a user. A user with email '%s' or username '%s' already exists.", email, username);
    }

    private String failedToCreateUserMessage(String email,
                                             String username) {
        return String.format("Failed to create user with email '%s' and username '%s' in the database.", email, username);
    }
}
