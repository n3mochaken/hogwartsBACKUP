package ru.hogwards.school.services;

import liquibase.sdk.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwards.school.exeptions.StudentNotFoundException;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);


//    private final HashMap<Long, Student> studentMap = new HashMap<>();
//    private long lastId = 0;

    public Student createStudent(Student student) {
        logger.debug("Running create student method");
        return studentRepository.save(student);
    }

    public Student editStudent(Student student) {
        logger.debug("Running edit student method");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("Running find student method");
        return studentRepository.findById(id).get();
    }

    public void deleteStudent(long id) {
        logger.debug("Running delete student method");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.debug("Running find all student method");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Running find by min max age student method");
        return studentRepository.findByAgeBetween(min, max);

    }

    public Faculty findFacultyOfStudent(long id) {
        logger.debug("Running find faculty of student method");
        return findStudent(id).getFaculty();
    }

    public Integer countStudents() {
        logger.debug("Running count student method");
        return studentRepository.countStudents();
    }

    public Float avgAgeOfStudents() {
        logger.debug("Running avg age student method");
        return studentRepository.AvgAgeOfStudents();
    }

    public List<Student> lastFiveStudents() {
        logger.debug("Running get last 5 student method");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getStudentNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageStudentAge() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new StudentNotFoundException("no students");
        }
        int totalAge = students.stream().mapToInt(Student::getAge).sum();
        return (double) totalAge / students.size();
    }

    public void parallelPrint() {
        printStudent(1);
        printStudent(2);
        new Thread(() -> {
            printStudent(19);
            printStudent(20);
        }).start();

        new Thread(() -> {
            printStudent(21);
            printStudent(22);
        }).start();
    }






    public void parallelPrintSynchronizedFlag() {
        printStudentFlag(1);
        printStudentFlag(2);
        new Thread(() -> {
            printStudentFlag(19);
            printStudentFlag(20);
        }).start();
        new Thread(() -> {
            printStudentFlag(21);
            printStudentFlag(22);
        }).start();
    }

    private void printStudent(int id) {
        System.out.println(findStudent(id));
    }
    public Object flag = new Object();
    private void printStudentFlag(int id) {
        synchronized (flag){
            System.out.println(findStudent(id));
        }

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
