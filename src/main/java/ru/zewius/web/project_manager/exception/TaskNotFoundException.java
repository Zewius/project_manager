package ru.zewius.web.project_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaskNotFoundException extends ResponseStatusException {
    public TaskNotFoundException(Long projectId, Long taskId) {
        super(HttpStatus.NOT_FOUND, "Task with ID " + taskId + " in project with ID " + projectId + " not found");
    }
}
