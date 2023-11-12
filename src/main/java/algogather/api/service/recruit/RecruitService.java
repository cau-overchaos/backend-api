package algogather.api.service.recruit;

import algogather.api.domain.recruit.RecruitCommentRepository;
import algogather.api.domain.recruit.RecruitPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitService {
    private final RecruitPostRepository recruitPostRepository;
    private final RecruitCommentRepository recruitCommentRepository;

//    public Object saveRecruitPost(){
//
//    }
}
