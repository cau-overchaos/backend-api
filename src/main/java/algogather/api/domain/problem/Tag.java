package algogather.api.domain.problem;

import algogather.api.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Tag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_id_by_provider")
    private Long idByProvider;

    @Column(name = "tag_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag_provider")
    private ProblemProvider provider;

    @Builder
    public Tag(Long id, Long idByProvider, String name, ProblemProvider provider) {
        this.id = id;
        this.idByProvider = idByProvider;
        this.name = name;
        this.provider = provider;
    }
}
