package ru.zewius.web.project_manager.domain;

import jakarta.validation.constraints.NotBlank;

public record NewProject(@NotBlank String name,
                         String description) {
}
