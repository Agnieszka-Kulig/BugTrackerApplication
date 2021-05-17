package bug.com.auth;

import bug.com.enums.AuthorityName;
import lombok.NoArgsConstructor;
import lombok.Getter;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Authority {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    @Enumerated
    public AuthorityName name;

    public Authority(AuthorityName name) {

        this.name = name;
    }
}