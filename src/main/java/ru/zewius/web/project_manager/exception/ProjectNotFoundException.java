package ru.zewius.web.project_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProjectNotFoundException extends ResponseStatusException {
    public ProjectNotFoundException(Long projectId) {
        super(HttpStatus.NOT_FOUND, "Project with ID " + projectId + " not found");
    }
}
