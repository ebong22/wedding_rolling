package happy.wedding.board;

import happy.wedding.domain.Board;
import happy.wedding.domain.BoardRepository;
import happy.wedding.domain.Contents;
import happy.wedding.domain.EntityInfo;
import happy.wedding.dto.BoardCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
//@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시판 화면 (축하 메세지 리스트)
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/view/{id}")
    public String boardView(@PathVariable Long id, Model model){
        Optional<Board> board = Optional.ofNullable(boardService.findById(id));
        if( board.isPresent() ) {
            model.addAttribute("contents", boardService.getContentsByBoardId(id));
            model.addAttribute("board", board.get());
            return "board/list";
        }
        else{
            // 해당 게시판 없을 시 메인(게시판 생성 폼)으로 이동
            return "redirect:/";
        }

    }

    /**
     * 게시판 만들기 form
     * @return
     */
    @GetMapping
    public String createBoardForm(Model model){
        model.addAttribute("board", new BoardCreateForm());
        return "board/form"; // 첫화면return
    }

    /**
     * 게시판 생성
     * @param form
     * @return
     */
    @PostMapping
    public String createBoard(@ModelAttribute BoardCreateForm form){
        Board board = makeBoard(form);
        Long boardId = boardService.createBoard(board);
        return "redirect:/view/" + boardId; // /view/{id}로 Redirect
    }

    /**
     * boardForm으로 board 생성
     * @param form
     * @return
     */
    private static Board makeBoard(BoardCreateForm form) {
        Board board = new Board();
        board.setName(form.getName());
        board.setBridge(form.getBridge());
        board.setGroom(form.getGroom());
        board.setWeddingDay(form.getWeddingDay());
        // @todo 암호화 추가
        board.setPassword(form.getPassword());
        board.setEntityInfo(new EntityInfo());
        return board;
    }

    // GET downloadImage/{boardId}
    @GetMapping("download/{id}")
    public void downloadImage(@PathVariable Long id){
        // 찾아보기
    }

    // GET downloadFile/{boardId}

    // 인스타, 카카오 공유는 알아보기
    // GET shareToInstagram/{boardId} - 인스타그램에 이미지 게시

    // GET shareToKakao/{boardId} - 카카오톡으로 url 공유 : 지인들에게 메세지 받기 위한 url 공유
}
