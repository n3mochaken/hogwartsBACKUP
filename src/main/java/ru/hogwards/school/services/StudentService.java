package ru.hogwards.school.services;

import org.springframework.stereotype.Service;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    private final HashMap<Long, Student> studentMap = new HashMap<>();
//    private long lastId = 0;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);

    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween (int min, int max) {
        return studentRepository.findByAgeBetween(min, max);

    }

    public Faculty findFacultyOfStudent(long id) {
        return findStudent(id).getFaculty();
    }


//    public List<Student> getStudentsByAge(int age) {
//        List<Student> studentsByAge = new ArrayList<>();
//        for (Student student : studentMap.values()) {
//            if (student.getAge() == age) {
//                studentsByAge.add(student);
//            }
//        }
//        return studentsByAge;
//    }


}
