package org.projects.sandbox.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import io.quarkus.runtime.Quarkus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.bson.Document;
import org.projects.sandbox.commons.serializer.Serializer;
import org.projects.sandbox.configuration.Configuration;
import org.projects.sandbox.model.exceptions.DatabaseException;


public class DbProducer {

    @Produces
    @ApplicationScoped
    public MongoClient getMongoClient(Configuration configuration) {
        String connectionString = configuration.db().connectionString();

        try {
            return MongoClients.create(connectionString);
        } catch (Exception exception) {
            Quarkus.asyncExit(1);
            throw new DatabaseException("Unable to connect to mongo.", exception);
        }
    }

    @Produces
    @ApplicationScoped
    public UserDao getUserDao(MongoClient mongoClient,
                           Configuration configuration,
                           Serializer serializer) {
        String databaseName = configuration.db().mongo().dbName();
        String collectionName = configuration.db().mongo().userCollection();

        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        return new UserDaoImpl(collection, serializer);
    }

}
