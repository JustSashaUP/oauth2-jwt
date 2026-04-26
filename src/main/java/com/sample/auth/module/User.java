package com.sample.auth.module;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
        @Id
        @UuidGenerator
        private UUID id;
        private String email;
        private String name;
        private String profilePicture;

        private Provider provider;
        private String providerId;

        public User() {}

        public User(String email, String name, String profilePicture, Provider provider, String providerId) {
                this.email = email;
                this.name = name;
                this.profilePicture = profilePicture;
                this.provider = provider;
                this.providerId = providerId;
        }

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setProfilePicture(String profilePicture) {
                this.profilePicture = profilePicture;
        }

        @Override
        public String toString() {
                return "User{" +
                        "email='" + email + '\'' +
                        ", name='" + name + '\'' +
                        ", profilePicture='" + profilePicture + '\'' +
                        ", provider=" + provider +
                        ", providerId='" + providerId + '\'' +
                        '}';
        }
}
