package happy.wedding.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue
    private Long id;

//    private String name;

    private String password; // 비밀번호 - 다운로드 기능 사용 시 인증용

    private String salt;    // 암호화 솔트

    private String bridge;  // 신부

    private String groom;   // 신랑

    private LocalDate weddingDay;

    @Embedded
    private EntityInfo entityInfo;
}
