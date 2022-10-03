package happy.wedding.domain;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ContentsRepository {


    // findByBoardId - return TList<Contents>

    @PersistenceContext
    EntityManager em;

    /**
     * 게시글 저장
     * @param contents
     * @return
     */
    public Long save(Contents contents){
        em.persist(contents);
        return contents.getId();
    }

    /**
     * 게시글 id로 조회
     * @param id
     * @return
     */
    public Contents find (Long id){
        return em.find(Contents.class, id);
    }

    /**
     * 특정 게시판에 해당하는 게시물 모두 조회
     * @param boardId
     * @return 게시글 List
     */
    public List<Contents> findByBoardId(Long boardId){
        return em.createQuery("select c from Contents c where c.board.id=:boardId", Contents.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }
}
