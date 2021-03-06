package bug.com.project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    Long id;

    @NotEmpty
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    String name;

    @Column
    String code;

    @NotEmpty
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    Boolean enabled = true;

    @Column
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateCreated;
}