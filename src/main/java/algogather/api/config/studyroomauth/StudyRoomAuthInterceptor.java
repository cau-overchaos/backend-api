package algogather.api.config.studyroomauth;

import algogather.api.domain.user.UserAdapter;
import algogather.api.service.studyroom.StudyRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class StudyRoomAuthInterceptor implements HandlerInterceptor {
    private final StudyRoomService studyRoomService;

    public StudyRoomAuthInterceptor(StudyRoomService studyRoomService) {
        this.studyRoomService = studyRoomService;
    }


    /**
     * 스터디룸 관련 컨트롤러에 진입하기 전에
     * 스터디방의 멤버인지 확인한다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserAdapter userAdapter = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        studyRoomService.checkStudyRoomMember(userAdapter);

       return true;
    }
}
