package bug.com.auth;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
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
    @Enumerated(EnumType.STRING)
    AuthorityName name;

    public Authority(AuthorityName name) {
        this.name = name;
    }
}