package algogather.api.config;

import algogather.api.config.studyroomauth.StudyRoomAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StudyRoomAuthInterceptor studyRoomAuthInterceptor;

    public WebConfig(StudyRoomAuthInterceptor studyRoomAuthInterceptor) {
        this.studyRoomAuthInterceptor = studyRoomAuthInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(studyRoomAuthInterceptor)
                .addPathPatterns("/studyrooms/**");
    }
}
