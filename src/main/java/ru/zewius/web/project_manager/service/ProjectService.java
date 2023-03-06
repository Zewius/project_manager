package ru.zewius.web.project_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.zewius.web.project_manager.dto.ProjectDTO;
import ru.zewius.web.project_manager.entity.Project;
import ru.zewius.web.project_manager.exception.ProjectNotFoundException;
import ru.zewius.web.project_manager.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN', 'ADMIN'})")
    public List<Project> getAllProjects(boolean onlyParents) {
        return findAll(onlyParents);
    }

    @PreAuthorize("hasAnyRole({'MANAGER', 'TECHNICIAN', 'ADMIN'})")
    public Project getProjectById(Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Project createProject(ProjectDTO newProject) {
        return create(newProject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Project createSubProject(Long parentProjectId, ProjectDTO newProject) {
        return create(parentProjectId, newProject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Project updateProjectInfo(Long id, ProjectDTO updateProject) {
        return updateInfo(id, updateProject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProject(Long id) {
        delete(id);
    }

    private List<Project> findAll(boolean onlyParents) {
        if (onlyParents) {
            return projectRepository.findAllByParentProjectIsNull();
        }
        return projectRepository.findAll();
    }

    private Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private Project create(ProjectDTO newProject) {
        return projectRepository.save(new Project(newProject.name(), newProject.description()));
    }

    private Project create(Long parentProjectId, ProjectDTO newProject) {
        return projectRepository.save(new Project(
                newProject.name(),
                newProject.description(),
                findById(parentProjectId)));
    }

    private Project updateInfo(Long id, ProjectDTO updateProject) {
        Project updatedProject = this.findById(id);
        updatedProject.setName(updateProject.name());
        updatedProject.setDescription(updateProject.description());
        return projectRepository.save(updatedProject);
    }

    private void delete(Long id) {
        projectRepository.delete(findById(id));
    }
}
