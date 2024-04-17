package ru.hogwards.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwards.school.exeptions.FacultyNotFoundException;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class FacultyService {
    //    private final HashMap<Long, Faculty> facultyMap = new HashMap<>();
//    private long lastId = 0;
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Running edit faculty method");
        return facultyRepository.save(faculty);
    }


    public Faculty findFaculty(long id) {
        logger.debug("Running find faculty method");
        return facultyRepository.findById(id).get();
    }

    public void deleteFaculty(long id) {
        logger.debug("Running delete faculty method");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.debug("Running get all faculty method");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColor(String color) {
        logger.debug("Running get by color faculty method");
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }  public Collection<Faculty> findByName(String name) {
        logger.debug("Running find student of faculty by name method");
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Student> getAllStudentsOfFaculty(Long id) {
        logger.debug("Running get all students of faculty method");
        return facultyRepository.getReferenceById(id).getStudents();
    }

    public List<String> getLongestFacultyNames() {
        List<Faculty> faculties = facultyRepository.findAll();


        int maxLength = faculties.stream()
                .map(Faculty::getName)
                .mapToInt(String::length)
                .max()
                .orElse(0);


        return faculties.stream()
                .map(Faculty::getName)
                .filter(name -> name.length() == maxLength)
                .collect(Collectors.toList());
    }


//    public List<Faculty> getFacultyByColor(String color) {
//        List<Faculty> facultyByColor = new ArrayList<>();
//        for (Faculty faculty : facultyMap.values()) {
//            if (faculty.getColor() == color) {
//                facultyByColor.add(faculty);
//            }
//        }
//        return facultyByColor;
//    }
}
