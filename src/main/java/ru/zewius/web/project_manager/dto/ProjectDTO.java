package ru.zewius.web.project_manager.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectDTO(@NotBlank String name,
                         String description) {
}
