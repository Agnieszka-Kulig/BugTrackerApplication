package bug.com.issues;//formatka z funkcjonalnością, lista sortująca statusy zadań

import bug.com.auth.Person;
import bug.com.comment.Comment;
import bug.com.enums.*;
import bug.com.project.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Issue {


    @Id
    @GeneratedValue
    Long id;

    @NotEmpty
    @Size(min = 5, max = 200)
    @Column(nullable = false)
    String title;

    @Size(min = 5, max = 255)
    @Column(columnDefinition = "TEXT")
    String content;

    @Column
    @Enumerated(EnumType.STRING)
    Status status;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "assignee_id")
    Person assignee;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    Project project;

    @Column
    @Enumerated(EnumType.STRING)
    Priority priority;

    @Column
    @Enumerated(EnumType.STRING)
    Type type;

    @ManyToOne()
    @JoinColumn(name = "creator_id")
    Person creator;

    @Column
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateCreated;

    @Column
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date lastUpdate;

    @Column
    @OneToMany(mappedBy = "issue")
    List<Comment> comments;

    @PrePersist
    public void setDateCreated(){

        this.dateCreated = new Date();
    }

    @PreUpdate
    public void setLastUpdate(){

        this.lastUpdate = new Date();
    }
}
