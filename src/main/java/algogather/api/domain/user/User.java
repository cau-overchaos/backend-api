package algogather.api.domain.user;

import algogather.api.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_profile_image")
    private String profile_image;

    @Column(name = "user_judge_account", nullable = false)
    private String judge_account;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;
}
