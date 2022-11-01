package happy.wedding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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


    /**
     * @// TODO: 2022/10/30 validation 진행중 
     */

//    @NotEmpty
//    private String name;

    @NotEmpty
    private String bridge;

    @NotEmpty
    private String groom;

    @NotEmpty
    @NumberFormat(pattern = "######")
    private String password;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate weddingDay;
}
