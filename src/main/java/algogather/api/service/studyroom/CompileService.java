package algogather.api.service.studyroom;

import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.compile.CompileResultResponseDto;
import algogather.api.dto.compile.CompileSourceCodeRequestForm;
import algogather.api.dto.compile.CompilerRequestDto;
import algogather.api.exception.compile.CompilerException;
import algogather.api.service.programmingllanguage.ProgrammingLanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static algogather.api.config.init.InitConst.COMPILER_SERVER_URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompileService {

    private final StudyRoomService studyRoomService;
    private final ProgrammingLanguageService programmingLanguageService;

    public CompileResultResponseDto compileSourceCode(Long studyRoomId, CompileSourceCodeRequestForm compileSourceCodeRequestForm, UserAdapter userAdapter ) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 컴파일이 가능하다.

        URI uri = UriComponentsBuilder.fromHttpUrl(COMPILER_SERVER_URL)
                .path("/executor")
                .encode()
                .build()
                .toUri();

        ProgrammingLanguage programmingLanguage = programmingLanguageService.findById(compileSourceCodeRequestForm.getProgrammingLanguageId());

        CompilerRequestDto compilerRequestDto = CompilerRequestDto.builder()
                .language(programmingLanguage.getNickname())
                .code(compileSourceCodeRequestForm.getSourceCodeText())
                .input(compileSourceCodeRequestForm.getInput())
                .build();

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CompilerResultApiResponse> apiResponseResponseEntity = restTemplate.postForEntity(uri, compilerRequestDto, CompilerResultApiResponse.class);

            if(apiResponseResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return apiResponseResponseEntity.getBody().getData();
            }
            else {
                throw new CompilerException(apiResponseResponseEntity.getBody().getMessage());
            }

        } catch(HttpClientErrorException | HttpServerErrorException |
                UnknownHttpStatusCodeException e) {
            throw new CompilerException("컴파일러 서버와 통신과정에서 오류가 발생하였습니다.");
        }
    }

    private static class CompilerResultApiResponse extends ApiResponse<CompileResultResponseDto> {}
}
