package org.projects.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = UserCreate.UserCreateBuilder.class)
public class UserCreate {

    @JsonProperty(required = true)
    private final String email;
    @JsonProperty(required = true)
    private final String password;
    @JsonProperty(required = true)
    private final String username;
    private final String name;
    private final String phone;
    private final String address;

    public UserCreate(UserCreateBuilder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.username = builder.username;
        this.name = builder.name;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCreate user = (UserCreate) o;
        return Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(username, user.username)
                && Objects.equals(name, user.name)
                && Objects.equals(phone, user.phone)
                && Objects.equals(address, user.address);

    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, username, name, phone, address);
    }

    @Override
    public String toString() {
        return "UserCreate{"
                + "email='" + email + '\''
                + ", password='" + password + '\''
                + ", username='" + username + '\''
                + ", name='" + name + '\''
                + ", phone='" + phone + '\''
                + ", address='" + address + '\''
                + '}';
    }

    // Builder Class
    @JsonPOJOBuilder
    public static class UserCreateBuilder {
        private final String email;
        private final String password;
        private final String username;
        private String name;
        private String phone;
        private String address;

        public UserCreateBuilder(String email,
                                 String password,
                                 String username) {
            // Required Parameters
            this.email = email;
            this.password = password;
            this.username = username;
        }

        public UserCreateBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserCreateBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserCreateBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public UserCreate build() {
            return new UserCreate(this);
        }
    }
}
