package happy.wedding.domain;

import lombok.Getter;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Embeddable
@Getter
public class EntityInfo {

    private LocalDateTime createDate;

    public EntityInfo(){
        this.createDate = LocalDateTime.now();
    }

}
