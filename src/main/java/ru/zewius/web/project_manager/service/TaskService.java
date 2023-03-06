package ru.zewius.web.project_manager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.zewius.web.project_manager.dto.TaskDTO;
import ru.zewius.web.project_manager.entity.Project;
import ru.zewius.web.project_manager.entity.Role;
import ru.zewius.web.project_manager.entity.Task;
import ru.zewius.web.project_manager.entity.User;
import ru.zewius.web.project_manager.exception.ProjectNotFoundException;
import ru.zewius.web.project_manager.exception.TaskNotFoundException;
import ru.zewius.web.project_manager.repository.ProjectRepository;
import ru.zewius.web.project_manager.repository.TaskRepository;

import java.util.List;

@Slf4j
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN', 'ADMIN'})")
    public List<Task> getTasks(Long projectId, User user) {
        return findAllTasks(projectId, user);
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN', 'ADMIN'})")
    public Task getTaskById(Long projectId, Long taskId, User user) {
        return findTaskById(projectId, taskId, user);
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN'})")
    public Task createTask(Long projectId, TaskDTO newTask, User user) {
        return create(projectId, newTask, user);
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN', 'ADMIN'})")
    public Task updateTaskInfo(Long projectId, Long taskId, TaskDTO updateTask, User user) {
        return updateInfo(projectId, taskId, updateTask, user);
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN', 'ADMIN'})")
    public Task updateTaskStatus(Long projectId, Long taskId, Task.TaskStatus status, User user) {
        return updateStatus(projectId, taskId, status, user);
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN', 'ADMIN'})")
    public void deleteTask(Long projectId, Long taskId, User user) {
        delete(projectId, taskId, user);
    }

    private List<Task> findAllTasks(Long projectId, User user) {
        if (projectRepository.existsById(projectId)) {
            if (user.getRole().equals(Role.ADMIN)) {
                return taskRepository.findAllByProjectId(projectId);
            }
            return taskRepository.findAllByProjectIdAndRole(projectId, user.getRole());
        } else {
            throw new ProjectNotFoundException(projectId);
        }
    }

    private Task findTaskById(Long projectId, Long taskId, User user) {
        if (projectRepository.existsById(projectId)) {
            if (user.getRole().equals(Role.ADMIN)) {
                return taskRepository.findById(projectId, taskId).orElseThrow(() ->
                        new TaskNotFoundException(projectId, taskId));
            }
            return taskRepository.findByIdAndRole(projectId, taskId, user.getRole()).orElseThrow(() ->
                    new TaskNotFoundException(projectId, taskId));
        } else {
            throw new ProjectNotFoundException(projectId);
        }
    }

    private Task create(Long projectId, TaskDTO newTask, User user) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        if (user.getRole().equals(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User with ADMIN role cannot create tasks");
        }
        return taskRepository.save(new Task(
                newTask.name(),
                user,
                user.getRole(),
                project,
                newTask.additionalInfo()));
    }

    private Task updateInfo(Long projectId, Long taskId, TaskDTO updateTask, User user) {
        Task updatedTask = this.findTaskById(projectId, taskId, user);
        log.info("Authorized user: " + user.toString());
        log.info("User who created the task: " + updatedTask.getCreatedBy());
        log.info("Equals: " + user.equals(updatedTask.getCreatedBy()));

        if (!user.equals(updatedTask.getCreatedBy()) && !user.getRole().equals(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot edit tasks created by other users");
        }
        updatedTask.setName(updateTask.name());
        updatedTask.setAdditionalInfo(updateTask.additionalInfo());
        return taskRepository.save(updatedTask);
    }

    private Task updateStatus(Long projectId, Long taskId, Task.TaskStatus status, User user) {
        Task updatedTask = this.findTaskById(projectId, taskId, user);
        updatedTask.setStatus(status);
        return taskRepository.save(updatedTask);
    }

    private void delete(Long projectId, Long taskId, User user) {
        Task deletedTask = this.findTaskById(projectId, taskId, user);
        if (!user.equals(deletedTask.getCreatedBy()) && !user.getRole().equals(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete tasks created by other users");
        }
        taskRepository.delete(deletedTask);
    }
}
