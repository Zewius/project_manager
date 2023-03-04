package ru.zewius.web.project_manager.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.zewius.web.project_manager.entity.Task;

public record NewTask(@NotBlank String name,
                      @NotNull Task.TaskPurpose purpose,
                      String additionalInfo) {
}
