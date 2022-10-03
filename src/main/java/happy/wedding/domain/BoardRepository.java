package happy.wedding.domain;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BoardRepository {

    @PersistenceContext
    EntityManager em;

    /**
     * 게시판 저장(생성)
     * @param board
     * @return
     */
    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }


    /**
     * 게시판 id로 조회
     * @param id
     * @return
     */
    public Board find (Long id){
        return em.find(Board.class, id);
    }
}
