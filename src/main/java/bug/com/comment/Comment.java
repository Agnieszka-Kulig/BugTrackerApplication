//dodatkowe

package bug.com.comment;

import bug.com.issues.Issue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    Long id;

    @Column
    @CreationTimestamp
    Date dateCreated;

    @ManyToOne
    @JoinColumn(name ="issue_id")
    Issue issue;

    @Column(columnDefinition = "TEXT")
    String content;

    @PrePersist
    public void setDateCreated(){
    this.dateCreated = new Date();}

}
