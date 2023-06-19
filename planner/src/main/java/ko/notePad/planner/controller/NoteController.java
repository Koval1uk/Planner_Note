package ko.notePad.planner.controller;

import ko.notePad.planner.domain.HttpResponse;
import ko.notePad.planner.domain.Note;
import ko.notePad.planner.enumeration.Level;
import ko.notePad.planner.exception.NoteNotFoundException;
import ko.notePad.planner.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/note")
@RequiredArgsConstructor

public class NoteController {
    private final NoteService noteService;

    @GetMapping("/all")
    public ResponseEntity<HttpResponse<Note>> getNotes() {
        return ResponseEntity.ok().body(noteService.getNotes());
    }

    @GetMapping("/add")
    public ResponseEntity<HttpResponse<Note>> saveNote(@RequestBody @Valid Note note) {
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/note/all").toUriString())
        ).body(noteService.saveNote(note));
    }

    @GetMapping("/filter")
    public ResponseEntity<HttpResponse<Note>> filterNotes(@RequestParam(value = "level") Level level) {
        return ResponseEntity.ok().body(noteService.filterNotes(level));
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse<Note>> updateNote(@RequestBody @Valid Note note) throws NoteNotFoundException {
        return ResponseEntity.ok().body(noteService.updateNote(note));
    }

    @PutMapping("/delete/{noteId}")
    public ResponseEntity<HttpResponse<Note>> updateNote(@PathVariable(value = "noteId") Long id) throws NoteNotFoundException{
        return ResponseEntity.ok().body(noteService.deleteNote(id));
    }
}
