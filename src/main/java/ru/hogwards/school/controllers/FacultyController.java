package ru.hogwards.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.services.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping
    public ResponseEntity<Collection<Faculty>> findByParamOfFaculty(@RequestParam(required = false) String color,
                                                                    @RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.findByName(name));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("/{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }


    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return  facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundedFaculty = facultyService.editFaculty(faculty);
        if (foundedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getStudentsByFaculty")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@RequestParam Long id) {
        Collection<Student> studentsOnFaculty = facultyService.getAllStudentsOfFaculty(id);
        if (studentsOnFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsOnFaculty);
    }


//    @GetMapping("/facultyFilter/{color}")
//    public ResponseEntity<Collection<Faculty>> getStudentByAge(@PathVariable String color) {
//        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
//    }


}
