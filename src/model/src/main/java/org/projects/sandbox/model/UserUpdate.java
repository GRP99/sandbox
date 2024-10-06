package org.projects.sandbox.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = UserUpdate.UserUpdateBuilder.class)
public class UserUpdate {
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String address;

    public UserUpdate(UserUpdateBuilder builder) {
        this.email = builder.email;
        this.password = builder.password;
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
        UserUpdate user = (UserUpdate) o;
        return Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(name, user.name)
                && Objects.equals(phone, user.phone)
                && Objects.equals(address, user.address);

    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, phone, address);
    }

    @Override
    public String toString() {
        return "UserUpdate{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    // Builder Class
    @JsonPOJOBuilder
    public static class UserUpdateBuilder {
        private String email;
        private String password;
        private String name;
        private String phone;
        private String address;

        public UserUpdateBuilder() {
            // Required Parameters
        }

        public UserUpdateBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserUpdateBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserUpdateBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserUpdateBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserUpdateBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public UserUpdate build() {
            return new UserUpdate(this);
        }
    }
}
