package org.projects.sandbox.api.exception.handler;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.projects.sandbox.commons.serializer.Serializer;
import org.projects.sandbox.model.Error;
import org.projects.sandbox.model.exceptions.DatabaseException;
import org.projects.sandbox.model.exceptions.InvalidInputException;
import org.projects.sandbox.model.exceptions.NotFoundException;
import org.projects.sandbox.model.exceptions.UserCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ThrowableHandler implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThrowableHandler.class);

    private final Serializer serializer;

    @Inject
    public ThrowableHandler(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Response toResponse(Throwable throwable) {
        LOGGER.error("An error occurred:", throwable);

        return switch (throwable) {
            case UserCreationException userCreationException -> createDefaultResponse(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    userCreationException,
                    "Error contacting database."
            );
            case DatabaseException databaseException -> createDefaultResponse(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    databaseException,
                    "Error contacting database"
            );
            case InvalidInputException invalidInputException -> createDefaultResponse(
                    Response.Status.BAD_REQUEST,
                    invalidInputException,
                    "Invalid request"
            );
            case NotFoundException notFoundException -> createDefaultResponse(
                    Response.Status.NOT_FOUND,
                    notFoundException,
                    "Resource does not exist"
            );
            default -> createDefaultResponse(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    throwable,
                    "Internal Server Error"
            );
        };
    }


    private Response createDefaultResponse(Response.Status statusCode,
                                           Throwable throwable,
                                           String message) {
        return Response
                .status(statusCode)
                .entity(serializer.toJson(new Error(throwable.getMessage(), message)))
                .build();
    }
}
