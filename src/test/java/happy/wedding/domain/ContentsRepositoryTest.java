package happy.wedding.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentsRepositoryTest {

    @Autowired
    ContentsRepository contentsRepository;

    @Autowired
    BoardRepository boardRepository;


    /**
     * 한 게시판에 여러 게시글 작성 후
     * findByBoardId로 찾아온 리스트 와 작성한 글 리스트 동일한 지 비교
     */
    @Test
    @Transactional
    @Rollback(false)
    public void contentsSaveAndFindOneTest(){
        Board board = new Board();
//        board.setName("test");
        Long boardId = boardRepository.save(board);

        Contents contents = new Contents();
        contents.setContents("GOOOOOD!!!!");
        contents.setName("BY");
        contents.setBoard(board);
        Long contentId = contentsRepository.save(contents);

        Contents findContents = contentsRepository.find(contentId);

        assertThat(contents).isEqualTo(findContents);
        assertThat(contents.getId()).isEqualTo(findContents.getId());

    }

    @Test
    @Transactional
    @Rollback(false)
    public void manyContentsTest(){
        Board board = new Board();
//        board.setName("bong");
        Long boardId = boardRepository.save(board);

        Contents contents1 = new Contents();
        contents1.setBoard(board);
        contentsRepository.save(contents1);

        Contents contents2 = new Contents();
        contents2.setBoard(board);
        contentsRepository.save(contents2);

        List<Contents> contentsList = contentsRepository.findByBoardId(boardId);
        log.info("contentsList = {}", contentsList);
        assertThat(contentsList).contains(contents1);
        assertThat(contentsList).contains(contents2);
    }


}