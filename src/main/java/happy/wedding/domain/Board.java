package happy.wedding.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String password; // 비밀번호 - 다운로드 기능 사용 시 인증용

    private String bridge;  // 신부

    private String groom;   // 신랑

    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}
