package ko.notePad.planner.service;

import ko.notePad.planner.domain.HttpResponse;
import ko.notePad.planner.domain.Note;
import ko.notePad.planner.enumeration.Level;
import ko.notePad.planner.exception.NoteNotFoundException;
import ko.notePad.planner.repo.NoteRepo;
import ko.notePad.planner.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singleton;
import static java.util.Optional.ofNullable;
import static ko.notePad.planner.util.DateUtil.dateTimeFormatter;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NoteService {
    private final NoteRepo noteRepo;

    /* method to get all notes */
    public HttpResponse<Note> getNotes() {
        log.info("Getting all notes from the database");
        return HttpResponse.<Note>builder()
                .notes(noteRepo.findAll())
                .message(noteRepo.count() > 0 ? noteRepo.count() + " notes retrieved" : "There is no notes to display")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    /* method to filter notes */
    public HttpResponse<Note> filterNotes(Level level) {
        List<Note> notes = noteRepo.findByLevel(level);
        log.info("Getting all the notes by level {}", level);
        return HttpResponse.<Note>builder()
                .notes(notes)
                .message(notes.size() + " notes are in " + level + " level of importance")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    /* method to save a new note */
    public HttpResponse<Note> saveNote(Note note) {
        log.info("Saving new note to the database");
        note.setCreateAt(LocalDateTime.now());
        return HttpResponse.<Note>builder()
                .notes(singleton(noteRepo.save(note)))
                .message("Note created successfully!")
                .status(CREATED)
                .statusCode(CREATED.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    /* method to update a note */
    public HttpResponse<Note> updateNote(Note note) throws NoteNotFoundException{
        log.info("Updating note to the database");
        Optional<Note> optionalNote = ofNullable(noteRepo.findById(note.getId())
                .orElseThrow(() -> new NoteNotFoundException("The note was not found on the server :(")));
        Note updateNote = optionalNote.get();
        updateNote.setId(note.getId());
        updateNote.setTitle(note.getTitle());
        updateNote.setDescription(note.getDescription());
        updateNote.setLevel(note.getLevel());
        noteRepo.save(updateNote);
        return HttpResponse.<Note>builder()
                .notes(singleton(updateNote))
                .message("Note updated successfully!")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    /* method to delete a note */
    public HttpResponse<Note> deleteNote(Long id) throws NoteNotFoundException{
        log.info("Deleting note from the database by id {}", id);
        Optional<Note> optionalNote = ofNullable(noteRepo.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("The note was not found on the server :(")));
        optionalNote.ifPresent(noteRepo::delete);
        return HttpResponse.<Note>builder()
                .notes(singleton(optionalNote.get()))
                .message("Note deleted successfully!")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }
}
