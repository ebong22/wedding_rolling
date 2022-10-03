package happy.wedding.board;

import happy.wedding.domain.Board;
import happy.wedding.domain.BoardRepository;
import happy.wedding.domain.Contents;
import happy.wedding.domain.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final ContentsRepository contentsRepository;
    private final BoardRepository boardRepository;

    /**
     * 특정 게시판에 해당하는 게시물 모두 조회
     * @param boardId   게시판 id
     * @return
     */
    public List<Contents> getContentsByBoardId(Long boardId) {
        return contentsRepository.findByBoardId(boardId);
    }

    /**
     * 게시판 생성
     * @param board
     * @return
     */
    public Long createBoard(Board board){
        return boardRepository.save(board);
    }

    /**
     * 게시판 조회
     * @param id
     * @return
     */
    public Board findById(Long id){
        return boardRepository.find(id);
    }
}
