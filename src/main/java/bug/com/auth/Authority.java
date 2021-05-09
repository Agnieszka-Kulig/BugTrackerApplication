package bug.com.auth;

import lombok.NoArgsConstructor;
import lombok.Getter;
import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Authority {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    AuthorityName name;

    public Authority(AuthorityName name) {
        this.name = name;
    }
}