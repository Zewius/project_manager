package ru.zewius.web.project_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zewius.web.project_manager.domain.NewProject;
import ru.zewius.web.project_manager.domain.UpdateProject;
import ru.zewius.web.project_manager.entity.Project;
import ru.zewius.web.project_manager.exception.ProjectNotFoundException;
import ru.zewius.web.project_manager.repository.ProjectRepository;

import java.util.List;

// TODO: Необходим ли здесь @Transactional?
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getParentProjects() {
        return projectRepository.findAllByParentProjectIsNull();
    }

    public Project getProjectById(Long id) {
        return this.findTaskById(id);
    }

    public Project createProject(NewProject newProject) {
        return this.create(newProject);
    }

    public Project createSubProject(Long parentProjectId, NewProject newProject) {
        return this.create(parentProjectId, newProject);
    }

    public Project updateProjectInfo(Long id, UpdateProject updateProject) {
        return this.updateInfo(id, updateProject);
    }

    public void deleteProject(Long id) {
        projectRepository.delete(this.findTaskById(id));
    }

    private Project findTaskById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private Project create(NewProject newProject) {
        return projectRepository.save(new Project(newProject.name(), newProject.description()));
    }

    private Project create(Long parentProjectId, NewProject newProject) {
        return projectRepository.save(new Project(
                newProject.name(),
                newProject.description(),
                findTaskById(parentProjectId)));
    }

    private Project updateInfo(Long id, UpdateProject updateProject) {
        Project updatedProject = this.findTaskById(id);
        updatedProject.setName(updateProject.name());
        updatedProject.setDescription(updateProject.description());
        return projectRepository.save(updatedProject);
    }
}
