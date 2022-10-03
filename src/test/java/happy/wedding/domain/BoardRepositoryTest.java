package happy.wedding.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardrepository;

    @Test
    @Transactional
    @Rollback(false)
    void boardTest(){
        Board board = new Board();
        board.setName("test");
        Long saveId = boardrepository.save(board);

        Board findBoard = boardrepository.find(saveId);
        log.info("findBoard ={}", findBoard.getId());

        assertThat(findBoard.getId()).isEqualTo(board.getId());
        assertThat(findBoard).isEqualTo(board);
    }

}