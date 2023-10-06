package algogather.api.domain.programminglanguage;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.studyroom.StudyRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class StudyRoomProgrammingLanguage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "study_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyRoom studyRoom;

    @JoinColumn(name = "programming_language_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProgrammingLanguage programmingLanguage;
}
