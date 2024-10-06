package org.projects.sandbox.model.database;

import java.util.Objects;

public class DbUserQuery {
    private final String email;
    private final String username;

    private DbUserQuery(DbUserQueryBuilder builder) {
        this.email = builder.email;
        this.username = builder.username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbUserQuery dbQuery = (DbUserQuery) o;
        return Objects.equals(email, dbQuery.email) && Objects.equals(username, dbQuery.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username);
    }

    @Override
    public String toString() {
        return "DbUserQuery{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public static class DbUserQueryBuilder {
        private String email;
        private String username;

        public DbUserQueryBuilder() {
            // Non Required Parameters
        }

        public DbUserQueryBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public DbUserQueryBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public DbUserQuery build() {
            return new DbUserQuery(this);
        }
    }
}
