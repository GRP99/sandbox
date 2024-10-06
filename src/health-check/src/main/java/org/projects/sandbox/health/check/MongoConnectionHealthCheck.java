package org.projects.sandbox.health.check;

import com.mongodb.client.MongoClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.projects.sandbox.configuration.Configuration;

@Readiness
@ApplicationScoped
public class MongoConnectionHealthCheck implements HealthCheck {

    private final Configuration configuration;
    private final MongoClient mongoClient;

    @Inject
    public MongoConnectionHealthCheck(Configuration configuration,
                                      MongoClient mongoClient) {
        this.configuration = configuration;
        this.mongoClient = mongoClient;
    }

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Mongo connection health check");

        try {
            String databaseName = configuration.db().mongo().dbName();
            Document command = new Document("ping", 1);
            mongoClient.getDatabase(databaseName).runCommand(command);

            responseBuilder.up();
        } catch (Exception e) {
            responseBuilder.down().withData("error", e.getMessage());
        }

        return responseBuilder.build();
    }
}