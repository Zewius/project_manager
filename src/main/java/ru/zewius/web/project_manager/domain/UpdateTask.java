package ru.zewius.web.project_manager.domain;

import jakarta.validation.constraints.NotBlank;

public record UpdateTask(@NotBlank String name,
                         String additionalInfo) {
}
