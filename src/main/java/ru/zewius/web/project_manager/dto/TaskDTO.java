package ru.zewius.web.project_manager.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(@NotBlank String name,
                      String additionalInfo) {
}
