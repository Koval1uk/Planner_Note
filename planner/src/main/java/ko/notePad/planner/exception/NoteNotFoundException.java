package ko.notePad.planner.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message){
        super (message);
    }
}
