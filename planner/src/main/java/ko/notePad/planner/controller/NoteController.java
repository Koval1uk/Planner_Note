package ko.notePad.planner.controller;

import ko.notePad.planner.domain.HttpResponse;
import ko.notePad.planner.domain.Note;
import ko.notePad.planner.enumeration.Level;
import ko.notePad.planner.exception.NoteNotFoundException;
import ko.notePad.planner.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

import static ko.notePad.planner.util.DateUtil.dateTimeFormatter;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/note")
@RequiredArgsConstructor

public class NoteController {
    private final NoteService noteService;

    @GetMapping("/all")
    public ResponseEntity<HttpResponse<Note>> getNotes() {
        return ResponseEntity.ok().body(noteService.getNotes());
    }

    @PostMapping("/add")
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

    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<HttpResponse<Note>> updateNote(@PathVariable(value = "noteId") Long id) throws NoteNotFoundException{
        return ResponseEntity.ok().body(noteService.deleteNote(id));
    }

    @PostMapping("/error")
    public ResponseEntity<HttpResponse<?>> handleError(HttpServletRequest request) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                .reason("There is no mapping fro a " + request.getMethod() + " request for this path on the server")
                .developerMessage("There is no mapping fro a " + request.getMethod() + " request for this path on the server")
                .status(NOT_FOUND)
                .statusCode(NOT_FOUND.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build(), NOT_FOUND
        );
    }
}
