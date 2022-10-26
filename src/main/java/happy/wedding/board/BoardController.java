package happy.wedding.board;

import happy.wedding.domain.Board;
import happy.wedding.domain.BoardRepository;
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

import javax.swing.text.html.Option;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;
    private final Encrypt encrypt;

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
    public String createBoard(@Validated @ModelAttribute BoardCreateForm form, BindingResult bindingResult) throws NoSuchAlgorithmException {
        if(bindingResult.hasErrors()){
            log.info("erros={}", bindingResult);
            return "board/form";
        }

        Board board = makeBoard(form);
        Long boardId = boardService.createBoard(board);
        return "redirect:/view/" + boardId; // /view/{id}로 Redirect
    }

//    @PostMapping
//    public String createBoard2(@Validated @ModelAttribute BoardCreateForm board, BindingResult bindingResult){
//
//        if(bindingResult.hasErrors()){
//            log.info("erros={}", bindingResult);
//            return "board/form";
//        }
//
//        Board realBoard = makeBoard(board);
//        Long boardId = boardService.createBoard(realBoard);
//        return "redirect:/view/" + boardId; // /view/{id}로 Redirect
//    }

    /**
     * boardForm으로 board 생성
     * @param form
     * @return
     */
    private Board makeBoard(BoardCreateForm form) throws NoSuchAlgorithmException {
        Board board = new Board();
        board.setName(form.getName());
        board.setBridge(form.getBridge());
        board.setGroom(form.getGroom());
        board.setWeddingDay(form.getWeddingDay());
        // @todo 암호화 추가
        board.setPassword(form.getPassword());
        board.setEntityInfo(new EntityInfo());

        // 비밀번호 암호화
        String salt = encrypt.makeSalt();
        String encodingPw = encrypt.encodingPassword(form.getPassword() + salt);
        log.info("salt={}",salt);
        log.info("form.getPassword()={}",form.getPassword());
        log.info("encodingPw={}",encodingPw);
        board.setPassword(encodingPw);
        board.setSalt(salt);

        return board;
    }

    // GET downloadImage/{boardId}
    @CrossOrigin("http://localhost:8080")
    @GetMapping("download/{id}")
    public String drawPdf(@PathVariable Long id, Model model){
        model.addAttribute("contents", boardService.getContentsByBoardId(id));
        return "contents/view-pdf";
    }

    @CrossOrigin("http://localhost:8080")
    @GetMapping("downloadTest/{id}")
    public String drawPdfTest(@PathVariable Long id, Model model){
        List<Contents> contentsList = boardService.getContentsByBoardId(id);

        List<List<Contents>> contents = new ArrayList<>();
        List<Contents> contentsTemp = new ArrayList<>();

        for (int i = 0; i < contentsList.size(); i++) {
            if( i !=0 && i%16 == 0){
                contents.add(copyContents(contentsTemp));
                contentsTemp.clear();
            }
            contentsTemp.add(contentsList.get(i));
            // 마지막 게시물 일 때
            if( i%16 != 0 && i == contentsList.size()-1 ){
                contents.add(copyContents(contentsTemp));
            }
        }
        model.addAttribute("contents", contents);
        return "contents/view-pdf";
    }

    public List<Contents> copyContents(List<Contents> contents) {
        List<Contents> result = new ArrayList<>();
        for (Contents content : contents) {
            result.add(content);
        }
        return result;
    }

    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    @CrossOrigin("http://localhost:8080")
    @PostMapping("check/{id}")
    @ResponseBody
    public Boolean checkPassword(@PathVariable Long id, @RequestBody String password) throws NoSuchAlgorithmException {
        Board board = boardService.findById(id);
        //비밀번호 작동 확인
        log.info("pw={}", password);
        log.info("dbpw={}", board.getPassword());
        log.info("dbsalt={}", board.getSalt());
        log.info("pw+salt={}", password + board.getSalt());
        log.info("encoding={}", encrypt.encodingPassword(password + board.getSalt()));
        return board.getPassword().equals( encrypt.encodingPassword(password + board.getSalt()) );
    }

    // GET downloadFile/{boardId}ㄴ

    // 인스타, 카카오 공유는 알아보기
    // GET shareToInstagram/{boardId} - 인스타그램에 이미지 게시

    // GET shareToKakao/{boardId} - 카카오톡으로 url 공유 : 지인들에게 메세지 받기 위한 url 공유
}
