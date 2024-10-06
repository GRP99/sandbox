package org.projects.sandbox.database;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.projects.sandbox.commons.serializer.Serializer;
import org.projects.sandbox.model.User;
import org.projects.sandbox.model.database.DbUserFilter;
import org.projects.sandbox.model.database.DbRequest;
import org.projects.sandbox.model.database.DbResponse;
import org.projects.sandbox.model.database.DbStatus;
import org.projects.sandbox.model.database.DbUserQuery;
import org.projects.sandbox.model.exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final MongoCollection<Document> collection;

    private final Serializer serializer;

    public UserDaoImpl(MongoCollection<Document> collection,
                       Serializer serializer) {
        this.collection = collection;
        this.serializer = serializer;
    }

    @Override
    public DbResponse<User> createUser(DbRequest<?, User> request) {
        try {
            Document document = Document.parse(serializer.toJson(request.newValue()));

            collection.insertOne(document);

            return new DbResponse<>(DbStatus.CREATED, request.newValue());
        } catch (MongoException mongoException) {
            if (ErrorCategory.fromErrorCode(mongoException.getCode()) == ErrorCategory.DUPLICATE_KEY) {
                return new DbResponse<>(DbStatus.ALREADY_EXIST, null);
            }
            throw new DatabaseException("Error while creating a user in database.", mongoException);
        } catch (Exception exception) {
            throw new DatabaseException("Error while creating a user.", exception);
        }
    }

    @Override
    public DbResponse<List<User>> findUsersByEmailOrUsername(DbRequest<DbUserFilter, ?> request) {
        Bson queryFilter = Filters.empty();

        Optional<DbUserQuery> optionalDbUserQuery = request.filter().getDbUserQuery();
        if (optionalDbUserQuery.isPresent()) {
            DbUserQuery dbUserQuery = optionalDbUserQuery.get();
            queryFilter = Filters.or(
                    Filters.eq("email", dbUserQuery.getEmail()),
                    Filters.eq("username", dbUserQuery.getUsername())
            );
        }

        try (MongoCursor<Document> cursor = collection.find(queryFilter).iterator()) {
            List<User> users = new ArrayList<>();

            while (cursor.hasNext()) {
                Document document = cursor.next();
                User user = serializer.fromJson(document.toJson(), User.class);
                users.add(user);
            }

            return new DbResponse<>(DbStatus.SUCCESS_NO_UPDATE, users);
        } catch (Exception e) {
            throw new DatabaseException("Error while retrieving users.", e);
        }
    }

    @Override
    public DbResponse<List<User>> getUsers(DbRequest<DbUserFilter, ?> request) {
        List<User> users = new ArrayList<>();

        FindIterable<Document> findIterable = collection.find();
        applyUserFilter(findIterable, request.filter());

        try (MongoCursor<Document> cursor = findIterable.iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                User user = serializer.fromJson(document.toJson(), User.class);
                users.add(user);
            }
            return new DbResponse<>(DbStatus.SUCCESS_NO_UPDATE, users);
        } catch (Exception e) {
            throw new DatabaseException("Error while retrieving users.", e);
        }
    }

    @Override
    public DbResponse<Long> countUsers(DbRequest<DbUserFilter, ?> request) {
        try {
            Optional<DbUserQuery> dbUserQuery = request.filter().getDbUserQuery();
            Bson filterDocument = dbUserQuery.map(this::createFilterDocument).orElseGet(Filters::empty);

            long numberOfDocuments = collection.countDocuments(filterDocument);

            return new DbResponse<>(DbStatus.SUCCESS_NO_UPDATE, numberOfDocuments);
        } catch (Exception e) {
            throw new DatabaseException("Error while counting the number of users.", e);
        }
    }

    @Override
    public DbResponse<User> updateUser(DbRequest<DbUserFilter, User> request) {
        try {
            Optional<DbUserQuery> dbUserQuery = request.filter().getDbUserQuery();
            Bson filterDocument = dbUserQuery.map(this::createFilterDocument).orElseGet(Filters::empty);

            Document replacementDocument = Document.parse(serializer.toJson(request.newValue()));

            UpdateResult updateResult = collection.replaceOne(filterDocument, replacementDocument);

            return updateResult.getModifiedCount() > 0
                    ? new DbResponse<>(DbStatus.UPDATED, null)
                    : new DbResponse<>(DbStatus.NOT_FOUND, null);
        } catch (Exception e) {
            throw new DatabaseException("Error while deleting the user.", e);
        }
    }

    @Override
    public DbResponse<User> deleteUser(DbRequest<DbUserFilter, ?> request) {
        try {
            Optional<DbUserQuery> dbUserQuery = request.filter().getDbUserQuery();
            Bson filterDocument = dbUserQuery.map(this::createFilterDocument).orElseGet(Filters::empty);

            Document deletedDocument = collection.findOneAndDelete(filterDocument);

            return deletedDocument != null
                    ? new DbResponse<>(DbStatus.SUCCESS_NO_UPDATE, serializer.fromJson(deletedDocument.toJson(), User.class))
                    : new DbResponse<>(DbStatus.NOT_FOUND, null);
        } catch (Exception e) {
            throw new DatabaseException("Error while deleting the user.", e);
        }

    }

    private void applyUserFilter(FindIterable<Document> findIterable,
                                 DbUserFilter dbUserFilter) {
        dbUserFilter.getDbUserQuery().ifPresent(dbUserQuery -> defineFilter(findIterable, dbUserQuery));
        dbUserFilter.getOffset().ifPresent(offset -> defineSkip(findIterable, offset));
        dbUserFilter.getLimit().ifPresent(limit -> defineLimit(findIterable, limit));
    }

    private void defineFilter(FindIterable<Document> findIterable,
                              DbUserQuery dbUserQuery) {
        findIterable.filter(createFilterDocument(dbUserQuery));
    }

    private void defineSkip(FindIterable<Document> findIterable,
                            int offset) {
        findIterable.skip(offset);
    }

    private void defineLimit(FindIterable<Document> findIterable,
                             int limit) {
        findIterable.limit(limit);
    }

    private Bson createFilterDocument(DbUserQuery dbUserQuery) {
        return Filters.eq("username", dbUserQuery.getUsername());
    }
}
