package ru.zewius.web.project_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zewius.web.project_manager.entity.Role;
import ru.zewius.web.project_manager.entity.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
    List<Task> findAllByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.purpose = :role")
    List<Task> findAllByProjectIdAndRole(@Param("projectId") Long projectId,
                                         @Param("role") Role role);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.id = :taskId")
    Optional<Task> findById(@Param("projectId") Long projectId,
                            @Param("taskId") Long taskId);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.id = :taskId AND t.purpose = :role")
    Optional<Task> findByIdAndRole(@Param("projectId") Long projectId,
                                   @Param("taskId") Long taskId,
                                   @Param("role") Role role);
}
