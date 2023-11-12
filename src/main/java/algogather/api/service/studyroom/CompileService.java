package algogather.api.service.studyroom;

import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.compile.CompileSourceCodeRequestForm;
import algogather.api.dto.compile.CompilerRequestDto;
import algogather.api.exception.compile.CompilerException;
import algogather.api.service.programmingllanguage.ProgrammingLanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static algogather.api.config.init.initConst.COMPILER_SERVER_URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompileService {

    private final StudyRoomService studyRoomService;
    private final ProgrammingLanguageService programmingLanguageService;

//    public Object compileSourceCode(Long studyRoomId, CompileSourceCodeRequestForm compileSourceCodeRequestForm, UserAdapter userAdapter ) {
//        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 컴파일이 가능하다.
//
//        URI uri = UriComponentsBuilder.fromHttpUrl(COMPILER_SERVER_URL)
//                .path("/executer")
//                .encode()
//                .build()
//                .toUri();
//
//
//        ProgrammingLanguage programmingLanguage = programmingLanguageService.findById(compileSourceCodeRequestForm.getProgrammingLanguageId());
//
//        List<String> inputList = new ArrayList<>();
//        inputList.add(compileSourceCodeRequestForm.getInput());
//
//        CompilerRequestDto compilerRequestDto = CompilerRequestDto.builder()
//                .language(programmingLanguage.getNickname())
//                .code(compileSourceCodeRequestForm.getSourceCodeText())
//                .input(inputList)
//                .build();
//
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<ApiResponse> apiResponseResponseEntity = restTemplate.postForEntity(uri, compilerRequestDto, ApiResponse.class);
//
//            String message = apiResponseResponseEntity.getBody().getMessage();
//
//            //TODO 메시지 내용으로 구분해서 응답 만들기
//            if(message.equals("Compile fail")) {
//
//            }
//            else if(message.equals("Excute Fail")) { //TODO 채점서버에서 오타 메시지 줌.
//
//            }
//            else if(message.equals("Language did not exist.")) {
//
//            }
//            else { // 정상
//
//            }
//
//
//        } catch(HttpClientErrorException e) {
//            throw new CompilerException();
//        } catch(HttpServerErrorException e) {
//            throw new CompilerException();
//        } catch (UnknownHttpStatusCodeException) {
//            throw new CompilerException();
//        }
//    }
}
