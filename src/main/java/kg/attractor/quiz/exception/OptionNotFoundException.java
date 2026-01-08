package kg.attractor.quiz.exception;

import java.util.NoSuchElementException;

public class OptionNotFoundException extends NoSuchElementException {
    public OptionNotFoundException() {
        super("Option not found!");
    }
}
