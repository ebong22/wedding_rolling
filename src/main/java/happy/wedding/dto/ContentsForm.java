package happy.wedding.dto;

import happy.wedding.domain.ListIcon;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ContentsForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String contents;

    @NotNull
    private ListIcon listIcon;
}
