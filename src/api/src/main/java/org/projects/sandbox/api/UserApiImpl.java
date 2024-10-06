package org.projects.sandbox.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.projects.sandbox.commons.serializer.Serializer;
import org.projects.sandbox.core.UserProcessor;
import org.projects.sandbox.model.User;
import org.projects.sandbox.model.UserCreate;
import org.projects.sandbox.model.UserUpdate;
import org.projects.sandbox.model.database.DbResponse;
import org.projects.sandbox.model.database.DbStatus;
import org.projects.sandbox.model.exceptions.InvalidInputException;
import org.projects.sandbox.model.exceptions.NotFoundException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserApiImpl implements UserApi {

    private final Serializer serializer;
    private final UserProcessor userProcessor;

    @Inject
    public UserApiImpl(Serializer serializer,
                       UserProcessor userProcessor) {
        this.serializer = serializer;
        this.userProcessor = userProcessor;
    }

    @Override
    public Response getUser(String username) {
        DbResponse<User> response = userProcessor.getUserByUsername(username);

        if (response.status() == DbStatus.SUCCESS_NO_UPDATE) {
            return Response.ok(serializer.toJson(response.value())).build();
        } else {
            throw createNotFoundException(username);
        }
    }

    @Override
    public Response updateUser(String username,
                               UserUpdate userUpdate) {
        if (userUpdate == null) {
            throw new InvalidInputException("User cannot be null.");
        }

        DbResponse<User> response = userProcessor.getUserByUsername(username);

        if (response.status() == DbStatus.SUCCESS_NO_UPDATE) {
            User user = response.value();

            User updatedUser = new User(
                    patchStringField(user.email(), userUpdate.getEmail()),
                    patchStringField(user.password(), userUpdate.getPassword()),
                    user.username(),
                    patchStringField(user.name(), userUpdate.getName()),
                    patchStringField(user.phone(), userUpdate.getPhone()),
                    patchStringField(user.address(), userUpdate.getAddress()),
                    user.garage()
            );

            DbResponse<User> dbResponse = userProcessor.updateUser(username, updatedUser);

            if (dbResponse.status() == DbStatus.UPDATED) {
                String locationUrl = "/user/" + username;
                return Response.ok().header(HttpHeaders.LOCATION, locationUrl).build();
            } else {
                throw createNotFoundException(username);
            }
        } else {
            throw createNotFoundException(username);
        }
    }

    @Override
    public Response deleteUser(String username) {
        DbResponse<User> response = userProcessor.deleteUser(username);

        if (response.status() == DbStatus.SUCCESS_NO_UPDATE) {
            return Response.noContent().build();
        } else {
            throw createNotFoundException(username);
        }
    }

    @Override
    public Response getAllUsers(int offset,
                                int limit) {
        DbResponse<List<User>> response = userProcessor.getUsers(offset, limit);

        DbResponse<Long> totalCountResponse = userProcessor.countAllUsers();

        List<User> users = response.value();

        int resultCount = users.size();
        Long totalCount = totalCountResponse.value();

        String usersJson = serializer.toJson(users);

        return Response.ok(usersJson)
                .header("Result-Count", resultCount)
                .header("Total-Count", totalCount)
                .build();
    }

    @Override
    public Response createUser(UserCreate userCreate) {
        if (userCreate == null) {
            throw new InvalidInputException("User cannot be null.");
        }

        User user = new User(
                userCreate.getEmail(),
                userCreate.getPassword(),
                userCreate.getUsername(),
                userCreate.getName(),
                userCreate.getPhone(),
                userCreate.getAddress(),
                new ArrayList<>()
        );

        User result = userProcessor.createUser(user);

        String locationUrl = "/user/" + result.username();

        return Response.created(URI.create(locationUrl)).build();
    }

    private static String patchStringField(String oldValue,
                                           String newValue) {
        return Objects.isNull(newValue) || newValue.isBlank()
                ? oldValue
                : newValue;
    }

    private static NotFoundException createNotFoundException(String username) {
        return new NotFoundException("User with username " + username + " not found.");
    }
}