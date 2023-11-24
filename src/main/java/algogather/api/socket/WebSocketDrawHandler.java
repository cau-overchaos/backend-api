package algogather.api.socket;


import algogather.api.domain.sharedsourcecode.SharedSourceCode;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.draw.MessageRequestDto;
import algogather.api.service.sharedsourcecode.SharedSourceCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketDrawHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Long, Set<WebSocketSession>> sharedSourceCodeSessionMap = new HashMap<>();

    private final SharedSourceCodeService sharedSourceCodeService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("{} 연결됨", session.getId());

        SecurityContextImpl o = (SecurityContextImpl) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
        UserAdapter principal = (UserAdapter) o.getAuthentication().getPrincipal();
        log.debug("유저 아이디: " + principal.getUsername());

        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.debug("payload {}", payload);

        MessageRequestDto messageRequestDto = mapper.readValue(payload, MessageRequestDto.class);
        log.debug("session {}", messageRequestDto.toString());

        SecurityContextImpl o = (SecurityContextImpl) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
        UserAdapter principal = (UserAdapter) o.getAuthentication().getPrincipal();
        log.debug("유저 아이디: " + principal.getUsername());

        SharedSourceCode sharedSourceCode = sharedSourceCodeService.findById(messageRequestDto.getSharedSourceCodeId()); // 공유소스코드로 그림판을 구분한다
        if(!sharedSourceCodeSessionMap.containsKey(sharedSourceCode.getId())) {
            sharedSourceCodeSessionMap.put(sharedSourceCode.getId(), new HashSet<>());
        }

        Set<WebSocketSession> sharedSourceCodeSession = sharedSourceCodeSessionMap.get(sharedSourceCode.getId());

        if(messageRequestDto.getMessageType().equals(MessageRequestDto.MessageType.ENTER)) { // messageType이 ENTER이면 해당 공유소스코드 그림판에 session 추가
            sharedSourceCodeSession.add(session);
            messageRequestDto.setMessageText(principal.getUsername() + " 님이 들어오셨습니다.");
        }
        else if (messageRequestDto.getMessageType().equals(MessageRequestDto.MessageType.QUIT)) {
            sharedSourceCodeSession.remove(session);
            messageRequestDto.setMessageText(principal.getUsername() + " 님이 나가셨습니다.");
        }
        else {
            messageRequestDto.setMessageText(principal.getUsername() + " 좌표를 전송하였습니다");
        }

        if(sharedSourceCodeSession.size()>=3) {
            removeClosedSession(sharedSourceCodeSession);
        }

        sendMessageToChatRoom(messageRequestDto, sharedSourceCodeSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("{} 연결 끊김", session.getId());

        SecurityContextImpl o = (SecurityContextImpl) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
        UserAdapter principal = (UserAdapter) o.getAuthentication().getPrincipal();
        log.debug("유저 아이디: " + principal.getUsername());

        sessions.remove(session);
    }

    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(MessageRequestDto chatMessageRequestDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageRequestDto));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
