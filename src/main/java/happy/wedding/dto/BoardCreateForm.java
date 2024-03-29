package happy.wedding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class BoardCreateForm {

//    @NotEmpty
//    private String name;

    @NotEmpty
    private String bridge;

    @NotEmpty
    private String groom;

    @NotEmpty
    @Length(min = 6, max = 6)
    private String password;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate weddingDay;
}
