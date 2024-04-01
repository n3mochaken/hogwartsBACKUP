package ru.hogwards.school.exeptions;

public class FacultyNotFoundException extends RuntimeException{
    public FacultyNotFoundException(String message) {
        super(message);
    }
}
