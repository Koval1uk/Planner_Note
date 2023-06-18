package ko.notePad.planner.repo;

import ko.notePad.planner.domain.Note;
import ko.notePad.planner.enumeration.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    List<Note> findByLevel(Level level);
    void deleteNoteById(Long id);

}
