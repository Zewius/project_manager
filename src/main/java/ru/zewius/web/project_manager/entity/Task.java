package ru.zewius.web.project_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    public enum TaskPurpose {
        MANAGER, TECHNICIAN
    }

    public enum TaskStatus {
        NEW, PROGRESS, DONE
    }

    public Task(String name, TaskPurpose purpose, Project project, String additionalInfo) {
        this.name = name;
        this.purpose = purpose;
        this.project = project;
        this.additionalInfo = additionalInfo;

        this.status = TaskStatus.NEW;
        this.createDate = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false)
    private TaskPurpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "status_change_date")
    private Date statusChangeDate;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // TODO: Скорее всего, в данное поле будут записывать текст больше 255 символов, поэтому тип данных в БД должен быть text
    @Column(name = "additional_info")
    private String additionalInfo;

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.statusChangeDate = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
