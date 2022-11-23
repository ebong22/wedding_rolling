package happy.wedding.board;

import happy.wedding.domain.Board;
import happy.wedding.domain.Contents;
import happy.wedding.domain.EntityInfo;
import happy.wedding.dto.BoardCreateForm;
import happy.wedding.util.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor

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
        model.addAttribute("boardCreateForm", new BoardCreateForm());
        return "board/form";
    }


    /**
     * 게시판 생성
     * @param form
     * @return
     */
    @PostMapping
    public String createBoard(@Validated @ModelAttribute BoardCreateForm form, BindingResult bindingResult, Model model) throws NoSuchAlgorithmException {
        if(bindingResult.hasErrors()){
            log.info("erros={}", bindingResult);
            return "board/form";
        }

        Board board = boardService.makeBoard(form);
        Long boardId = boardService.createBoard(board);
        return "redirect:/view/" + boardId; // /view/{id}로 Redirect
    }


    /**
     * pdf Downlad view 생성
     * @param id
     * @param model
     * @return
     */
    //    @CrossOrigin("*")
    @GetMapping("download/{id}")
    public String drawPdf(@PathVariable Long id, Model model){
        model.addAttribute("contents", boardService.makePdfList(id));
        return "contents/view-pdf";
    }


    /**
     * 비밀번호 확인
     * @param id
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("check/{id}")
    @ResponseBody
    public Boolean checkPassword(@PathVariable Long id, @RequestBody String password) throws NoSuchAlgorithmException {
        return boardService.passwordCheck(id, password);
    }

}
