package happy.wedding.contents;

import happy.wedding.board.BoardService;
import happy.wedding.domain.Contents;
import happy.wedding.domain.EntityInfo;
import happy.wedding.domain.ListIcon;
import happy.wedding.dto.ContentsForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("contents")
public class ContentsController {

    private final ContentsService contentsService;

    private final BoardService boardService;

    /**
     * 게시글 상세보기
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model){
        model.addAttribute("contents", contentsService.getContents(id));
        return "contents/view";
    }

    @GetMapping("/view/pdf/{boardId}")
    public String viewPdf(@PathVariable Long boardId, Model model){
        model.addAttribute("contents", boardService.getContentsByBoardId(boardId));
        return "contents/view-pdf";
    }

    /**
     * 게시글 작성 form
     * @return
     */
    @GetMapping("{boardId}")
    public String contentsForm(Model model){
        model.addAttribute("contentsForm", new ContentsForm());
        model.addAttribute("listIcon", ListIcon.values());
        return "contents/form";
    }

    /**
     * 게시글 저장
     * @param form
     * @return
     */
    @PostMapping("{boardId}")
    public String saveContents(@Validated @ModelAttribute ContentsForm form, BindingResult bindingResult, @PathVariable Long boardId, Model model){
        if(bindingResult.hasErrors()){
            log.info("erros={}", bindingResult);
            model.addAttribute("listIcon", ListIcon.values());
            model.addAttribute("selectedIcon", form.getListIcon());
            return "contents/form";
        }
        Contents contents = makeContents(form, boardId);
        Long contentsId = contentsService.save(contents);
        return "redirect:/contents/view/" + contentsId; //view/{id}로 Redirect or contents/view로
    }

    /**
     * form to Contents
     * @param form
     * @param boardId
     * @return
     */
    private Contents makeContents(ContentsForm form, Long boardId) {
        Contents contents = new Contents();
        contents.setName(form.getName());
        contents.setContents(form.getContents());
        contents.setListIcon(form.getListIcon());
        contents.setBoard(boardService.findById(boardId));
        contents.setEntityInfo(new EntityInfo());
        return contents;
    }


}
