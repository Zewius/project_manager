package ru.zewius.web.project_manager.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.zewius.web.project_manager.dto.TaskDTO;
import ru.zewius.web.project_manager.entity.Task;
import ru.zewius.web.project_manager.entity.User;
import ru.zewius.web.project_manager.service.TaskService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/project/{projectId}")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "/tasks")
    public List<Task> getProjectTasks(@PathVariable Long projectId,
                                      @AuthenticationPrincipal User user) {
        return taskService.getTasks(projectId, user);
    }

    @GetMapping(path = "/task/{taskId}")
    public Task getProjectTaskById(@PathVariable Long projectId,
                                   @PathVariable Long taskId,
                                   @AuthenticationPrincipal User user) {
        return taskService.getTaskById(projectId, taskId, user);
    }

    @PostMapping(path = "/tasks")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Task createProjectTask(@PathVariable Long projectId,
                                  @Valid @RequestBody TaskDTO newTask,
                                  @AuthenticationPrincipal User user) {
        return taskService.createTask(projectId, newTask, user);
    }

    @PutMapping(path = "/task/{taskId}")
    public Task updateProjectTaskInfo(@PathVariable Long projectId,
                                      @PathVariable Long taskId,
                                      @Valid @RequestBody TaskDTO updateTask,
                                      @AuthenticationPrincipal User user) {
        return taskService.updateTaskInfo(projectId, taskId, updateTask, user);
    }

    @PutMapping(path = "/task/{taskId}/status")
    public Task updateProjectTaskStatus(@PathVariable Long projectId,
                                        @PathVariable Long taskId,
                                        @RequestBody Map<String, Task.TaskStatus> status,
                                        @AuthenticationPrincipal User user) {
        if (status.get("status") == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status field not found");
        }
        return taskService.updateTaskStatus(projectId, taskId, status.get("status"), user);
    }

    @DeleteMapping(path = "/task/{taskId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProjectTask(@PathVariable Long projectId,
                                  @PathVariable Long taskId,
                                  @AuthenticationPrincipal User user) {
        taskService.deleteTask(projectId, taskId, user);
    }
}
