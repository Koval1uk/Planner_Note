package ko.notePad.planner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import ko.notePad.planner.enumeration.Level;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Data
public class Note  implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull(message = "Title can not be null") @NotEmpty(message = "Title can not be empty")
    private String title;
    @NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty")
    private String description;
    private Level level;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "Europe/Bratislava")
    private LocalDateTime createAt;

}
