package org.projects.sandbox.model.database;

import java.util.Objects;
import java.util.Optional;

public class DbUserFilter {

    private final Optional<DbUserQuery> dbUserQuery;
    private final Optional<Integer> offset;
    private final Optional<Integer> limit;


    private DbUserFilter(DbFilterBuilder builder) {
        this.dbUserQuery = Optional.ofNullable(builder.dbUserQuery);
        this.offset = Optional.ofNullable(builder.offset);
        this.limit = Optional.ofNullable(builder.limit);
    }


    public Optional<DbUserQuery> getDbUserQuery() {
        return dbUserQuery;
    }

    public Optional<Integer> getOffset() {
        return offset;
    }

    public Optional<Integer> getLimit() {
        return limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DbUserFilter that = (DbUserFilter) o;
        return Objects.equals(dbUserQuery, that.dbUserQuery)
                && Objects.equals(offset, that.offset)
                && Objects.equals(limit, that.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbUserQuery, offset, limit);
    }

    @Override
    public String toString() {
        return "DbFilter{"
                + ", dbUserQuery=" + dbUserQuery
                + ", offset=" + offset
                + ", limit=" + limit
                + '}';
    }

    public static class DbFilterBuilder {
        private DbUserQuery dbUserQuery;
        private Integer offset;
        private Integer limit;

        public DbFilterBuilder() {
            // Non Required Parameters
        }

        public DbFilterBuilder withDbQuery(DbUserQuery dbUserQuery) {
            this.dbUserQuery = dbUserQuery;
            return this;
        }

        public DbFilterBuilder withOffset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public DbFilterBuilder withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public DbUserFilter build() {
            return new DbUserFilter(this);
        }
    }
}
