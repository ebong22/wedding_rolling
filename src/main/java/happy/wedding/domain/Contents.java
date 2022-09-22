package happy.wedding.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Contents {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    private String name;

    private String contents;

    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}
