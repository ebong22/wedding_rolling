package happy.wedding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Validated
public class BoardCreateForm {

    @NotNull
    private String name;
    @NotNull
    private String bridge;
    @NotNull
    private String groom;
    @NotNull
    @NumberFormat(pattern = "######")
    private String password;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate weddingDay;
}
