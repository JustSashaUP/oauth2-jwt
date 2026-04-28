package com.sample.auth.module;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
        @Id
        @UuidGenerator
        private UUID id;
        private String email;
        private String fullName;
        private String profilePictureUrl;

        private Provider provider;
        private String providerId;

        @Enumerated(EnumType.STRING)
        private Role role;

        public User() {}

        public User(String email) {
                this.email = email;
        }

        public User(String email, String fullName, String profilePictureUrl,
                    Provider provider, String providerId, Role role) {
                this.email = email;
                this.fullName = fullName;
                this.profilePictureUrl = profilePictureUrl;
                this.provider = provider;
                this.providerId = providerId;
                this.role = role;
        }

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getEmail() {
                return email;
        }

        public String getProfilePictureUrl() {
                return profilePictureUrl;
        }

        public void setProfilePictureUrl(String profilePictureUrl) {
                this.profilePictureUrl = profilePictureUrl;
        }

        public Role getRole() {
                return role;
        }

        @Override
        public String toString() {
                return "User{" +
                        "email='" + email + '\'' +
                        ", name='" + fullName + '\'' +
                        ", pictureUrl='" + profilePictureUrl + '\'' +
                        ", provider=" + provider +
                        ", providerId='" + providerId + '\'' +
                        '}';
        }
}
