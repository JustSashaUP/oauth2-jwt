package com.sample.auth.module;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public record User(
        @Id
        @UuidGenerator
        UUID id,
        String name,
        String email
) {
}
