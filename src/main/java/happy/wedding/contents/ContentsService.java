package happy.wedding.contents;

import happy.wedding.domain.Contents;
import happy.wedding.domain.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ContentsService {
    private final ContentsRepository contentsRepository;

    /**
     * 게시글 조회
     * @param id
     * @return
     */
    public Contents getContents(Long id) {
        return contentsRepository.find(id);
    }

    /**
     * 게시글 저장
     * @param contents
     * @return
     */
    public Long save(Contents contents) {
        return contentsRepository.save(contents);
    }
}
