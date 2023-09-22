package algogather.api.domain.chat;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.post.Board;
import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_message_content")
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "study_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyRoom studyRoom;

}
