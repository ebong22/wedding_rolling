package happy.wedding.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Contents {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;    // 게시판

    private String name;    // 게시물 이름

    @Lob
    private String contents;    // 게시물 내용

    @Enumerated(EnumType.STRING)
    private ListIcon listIcon;  // 리스트 아이콘(꽃, 하트)

    @Embedded
    private EntityInfo entityInfo;
}
