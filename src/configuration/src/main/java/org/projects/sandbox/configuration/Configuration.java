package org.projects.sandbox.configuration;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "config")
public interface Configuration {
    Db db();

    interface Db {
        String connectionString();

        Mongo mongo();

        /**
         * Mongo Configuration.
         */
        interface Mongo {
            @WithDefault("sandbox")
            String dbName();

            @WithDefault("user")
            String userCollection();
        }
    }

}
