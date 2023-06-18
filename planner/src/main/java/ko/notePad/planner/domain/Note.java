package ko.notePad.planner.domain;

import ko.notePad.planner.enumeration.Level;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Note  implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Level level;
    private LocalDateTime createAt;

}
