package bug.com.project;
import bug.com.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Status status = Status.NORMAL;


    @NotEmpty
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    Boolean enabled = true;
}