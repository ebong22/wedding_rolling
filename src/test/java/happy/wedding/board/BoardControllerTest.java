package happy.wedding.board;

import happy.wedding.domain.Board;
import happy.wedding.domain.BoardRepository;
import happy.wedding.dto.BoardCreateForm;
import happy.wedding.util.Encrypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {

    @Autowired
    Encrypt encrypt;
    @Autowired
    BoardService boardService;


    @Test
    void passwordCheckTest() throws NoSuchAlgorithmException {
        BoardCreateForm form = new BoardCreateForm();
        form.setBridge("test");
        form.setGroom("test");
        form.setWeddingDay(LocalDate.now());
        form.setPassword("123456");

        Board board = boardService.makeBoard(form);
        Long saveId = boardService.createBoard(board);

        Board findBoard = boardService.findById(saveId);

        assertThat(findBoard.getSalt()).isEqualTo(board.getSalt());
        assertThat(findBoard.getPassword()).isEqualTo(encrypt.encodingPassword("123456" + board.getSalt()));
    }

}