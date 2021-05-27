package bug.com.issues;//formatka z funkcjonalnością, lista sortująca statusy zadań



import bug.com.auth.Person;
import bug.com.comment.Comment;
import bug.com.enums.*;
import bug.com.project.Project;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collection;
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

    @Column(nullable = false)
    String tittle;

    @Column(columnDefinition = "TEXT")
    String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    State state = State.TODO;

    @ManyToOne()
    @JoinColumn(name = "assignee_id")
    Person assignee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    Project project;

    @Column
    @CreationTimestamp
    Date dateCreated;

    @Column
    @UpdateTimestamp
    Date lastUpdate;

    @Column
    @Enumerated(EnumType.STRING)
    Status status;

    @Column
    @Enumerated(EnumType.STRING)
    Priority priority;


   @Column
   @Enumerated(EnumType.STRING)
   Type type;

   @Column
   @Enumerated(EnumType.STRING)
   @ElementCollection
   List<Tag> tag;

   @Column
   String code;

   @ManyToOne()
   @JoinColumn(name = "creator_id")
   Person creator;


   @Column
   @OneToMany(mappedBy = "issue")
   List<Comment> comments;

   @PrePersist
   public void setDateCreated(){this.dateCreated = new Date();}


    @PreUpdate
    public void setLastUpdate(){this.lastUpdate = new Date();}



}
