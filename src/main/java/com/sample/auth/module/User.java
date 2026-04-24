package com.sample.auth.module;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "users")
public record User(
        @Id
        @UuidGenerator
        UUID id,
        String name,
        String email
) {
}
