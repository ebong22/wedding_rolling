package happy.wedding.board;

import happy.wedding.domain.*;
import happy.wedding.dto.BoardCreateForm;
import happy.wedding.util.Encrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final ContentsRepository contentsRepository;
    private final BoardRepository boardRepository;
    private final Encrypt encrypt;


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


    /**
     * boardForm으로 board 생성
     * @param form
     * @return
     */
    public Board makeBoard(BoardCreateForm form) throws NoSuchAlgorithmException {
        Board board = new Board();

//        board.setName(form.getName());
        board.setBridge(form.getBridge());
        board.setGroom(form.getGroom());
        board.setWeddingDay(form.getWeddingDay());
        board.setEntityInfo(new EntityInfo());

        passwordEncrypt(form, board);

        return board;
    }


    /**
     * 비밀번호 암호화 후 board에 세팅
     * @param form
     * @param board
     * @throws NoSuchAlgorithmException
     */
    public void passwordEncrypt(BoardCreateForm form, Board board) throws NoSuchAlgorithmException {
        String salt = encrypt.makeSalt();
        String encodingPw = encrypt.encodingPassword(form.getPassword() + salt);

        board.setPassword(encodingPw);
        board.setSalt(salt);
    }


    /**
     * pdf downloadView를 위한 게시글 데이터 생성
     * @param id
     * @return
     */
    public List<List<Contents>> makePdfList(Long id) {
        List<Contents> contentsList = getContentsByBoardId(id);

        List<List<Contents>> contents = new ArrayList<>();
        List<Contents> contentsTemp = new ArrayList<>();

        for(int i = 0; i < contentsList.size(); i++ ) {
            if( i != 0 && i % 16 == 0 ){
                contents.add(copyContents(contentsTemp));
                contentsTemp.clear();
            }
            contentsTemp.add(contentsList.get(i));

            // 마지막 게시물 일 때
            if( i % 16 != 0 && i == contentsList.size() - 1 ){
                contents.add(copyContents(contentsTemp));
            }
        }
        return contents;
    }


    /**
     * 배열 복사
     * @param contents
     * @return
     */
    public List<Contents> copyContents(List<Contents> contents) {
        List<Contents> result = new ArrayList<>();

        for (Contents content : contents) {
            result.add(content);
        }
        return result;
    }


    /**
     * 비밀번호 확인
     * @param id
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public boolean passwordCheck(Long id, String password) throws NoSuchAlgorithmException {
        Board board = findById(id);

//        log.info("pw={}", password);
//        log.info("dbpw={}", board.getPassword());
//        log.info("dbsalt={}", board.getSalt());
//        log.info("pw+salt={}", password + board.getSalt());
//        log.info("encoding={}", encrypt.encodingPassword(password + board.getSalt()));

        return board.getPassword().equals(encrypt.encodingPassword(password + board.getSalt()));
    }





}
