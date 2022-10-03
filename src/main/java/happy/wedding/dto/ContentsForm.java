package happy.wedding.dto;

import happy.wedding.domain.ListIcon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
public class ContentsForm {

    @NotNull
    private String name;

    @NotNull
    private String contents;

    @NotNull
    private ListIcon listIcon;
}
