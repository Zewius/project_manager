package ru.zewius.web.project_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zewius.web.project_manager.domain.NewTask;
import ru.zewius.web.project_manager.domain.UpdateTask;
import ru.zewius.web.project_manager.entity.Project;
import ru.zewius.web.project_manager.entity.Task;
import ru.zewius.web.project_manager.exception.ProjectNotFoundException;
import ru.zewius.web.project_manager.exception.TaskNotFoundException;
import ru.zewius.web.project_manager.repository.ProjectRepository;
import ru.zewius.web.project_manager.repository.TaskRepository;

import java.util.List;

// TODO: Необходим ли здесь @Transactional?
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public List<Task> getTasks(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    public Task getTaskById(Long projectId, Long taskId) {
        return this.findTaskById(projectId, taskId);
    }

    public Task createTask(Long projectId, NewTask newTask) {
        return this.create(projectId, newTask);
    }

    public Task updateTaskInfo(Long projectId, Long taskId, UpdateTask updateTask) {
        return this.updateInfo(projectId, taskId, updateTask);
    }

    public Task updateTaskStatus(Long projectId, Long taskId, Task.TaskStatus status) {
        return this.updateStatus(projectId, taskId, status);
    }

    public void deleteTask(Long projectId, Long taskId) {
        taskRepository.delete(this.findTaskById(projectId, taskId));
    }

    private Task findTaskById(Long projectId, Long taskId) {
        if (projectRepository.existsById(projectId)) {
            return taskRepository.findById(projectId, taskId).orElseThrow(() ->
                    new TaskNotFoundException(projectId, taskId));
        } else {
            throw new ProjectNotFoundException(projectId);
        }
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
    }
    
    private Task create(Long projectId, NewTask newTask) {
        Project project = this.findProjectById(projectId);
        return taskRepository.save(new Task(
                newTask.name(),
                newTask.purpose(),
                project,
                newTask.additionalInfo()));
    }
    
    private Task updateInfo(Long projectId, Long taskId, UpdateTask updateTask) {
        Task updatedTask = this.findTaskById(projectId, taskId);
        updatedTask.setName(updateTask.name());
        updatedTask.setAdditionalInfo(updateTask.additionalInfo());
        return taskRepository.save(updatedTask);
    }

    private Task updateStatus(Long projectId, Long taskId, Task.TaskStatus status) {
        Task updatedTask = this.findTaskById(projectId, taskId);
        updatedTask.setStatus(status);
        return taskRepository.save(updatedTask);
    }
}
