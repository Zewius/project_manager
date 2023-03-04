package ru.zewius.web.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zewius.web.project_manager.domain.NewProject;
import ru.zewius.web.project_manager.domain.UpdateProject;
import ru.zewius.web.project_manager.entity.Project;
import ru.zewius.web.project_manager.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //TODO: Завести глобальный обработчик исключений для данного случая
    @GetMapping(path = "/projects")
    public List<Project> getProjects(@RequestParam(defaultValue = "false") boolean onlyParents) {
        if (onlyParents) {
            return projectService.getParentProjects();
        }
        return projectService.getAllProjects();
    }

    @GetMapping(path = "/project/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping(path = "/projects")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Project createProject(@RequestBody NewProject newProject) {
        return projectService.createProject(newProject);
    }

    @PostMapping(path = "/project/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Project createSubProject(@PathVariable Long id, @RequestBody NewProject newProject) {
        return projectService.createSubProject(id, newProject);
    }

    @PutMapping(path = "/project/{id}")
    public Project updateProjectInfo(@PathVariable Long id, @RequestBody UpdateProject updateProject) {
        return projectService.updateProjectInfo(id, updateProject);
    }

    @DeleteMapping(path = "/project/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
