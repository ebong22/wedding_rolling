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
    private Board board;

    private String name;

    private String contents;

    @Enumerated(EnumType.STRING)
    private ListIcon listIcon;

    @Embedded
    private EntityInfo entityInfo;
}
