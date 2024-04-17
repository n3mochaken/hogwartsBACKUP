package ru.hogwards.school.controllers;


import org.hibernate.type.IntegerType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwards.school.model.AmountOfStudents;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.services.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/students")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudent(@RequestParam(required = false) Integer min,
                                                           @RequestParam(required = false) Integer max) {

        if (min > 0 && max > 0 && max >= min) {
            return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
        }


        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundedStudent = studentService.editStudent(student);
        if (foundedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/facultyOfStudent/{id}")
    public Faculty findFaculty(@PathVariable long id) {
        return studentService.findFacultyOfStudent(id);
    }

    @GetMapping("/student/getAmount")
    public Integer getAmount() {
        return studentService.countStudents();
    }

    @GetMapping("/student/getAvgAge")
    public Float avgAge() {
        return studentService.avgAgeOfStudents();
    }
    @GetMapping("/student/getLastFiveStudents")
    public List<Student> lastFiveStudents() {
        return studentService.lastFiveStudents();
    }


    @GetMapping("/students/starting-with-a")
    public List<String> getStudentNamesStartingWithA() {
        return studentService.getStudentNamesStartingWithA();
    }

    @GetMapping("/students/average-age")
    public double getAverageStudentAge() {
        return studentService.getAverageStudentAge();
    }


    @GetMapping("/calculateSum")
    public int calculateValue() {
        int sum = IntStream.rangeClosed(1, 1000000)
                .parallel()
                .sum();
        return sum;
    }

//    @GetMapping("/ageFilter/{age}")
//    public ResponseEntity<Collection<Student>> getStudentByAge(@PathVariable int age) {
//        return ResponseEntity.ok(studentService.getStudentsByAge(age));
//    }

}
