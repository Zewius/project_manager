package ru.zewius.web.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zewius.web.project_manager.domain.NewTask;
import ru.zewius.web.project_manager.domain.UpdateTask;
import ru.zewius.web.project_manager.entity.Task;
import ru.zewius.web.project_manager.service.TaskService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/project/{projectId}")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "/tasks")
    public List<Task> getProjectTasks(@PathVariable Long projectId) {
        return taskService.getTasks(projectId);
    }

    @GetMapping(path = "/task/{taskId}")
    public Task getProjectTaskById(@PathVariable Long projectId,
                                   @PathVariable Long taskId) {
        return taskService.getTaskById(projectId, taskId);
    }

    @PostMapping(path = "/tasks")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Task createProjectTask(@PathVariable Long projectId,
                                  @RequestBody NewTask newTask) {
        return taskService.createTask(projectId, newTask);
    }

    @PutMapping(path = "/task/{taskId}")
    public Task updateProjectTaskInfo(@PathVariable Long projectId,
                                      @PathVariable Long taskId,
                                      @RequestBody UpdateTask updateTask) {
        return taskService.updateTaskInfo(projectId, taskId, updateTask);
    }

    //TODO: Завести глобальный обработчик исключений для данного случая
    @PutMapping(path = "/task/{taskId}/status")
    public Task updateProjectTaskStatus(@PathVariable Long projectId,
                                        @PathVariable Long taskId,
                                        @RequestBody Map<String, Task.TaskStatus> status) {
        return taskService.updateTaskStatus(projectId, taskId, status.get("status"));
    }

    @DeleteMapping(path = "/task/{taskId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProjectTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteTask(projectId, taskId);
    }
}
