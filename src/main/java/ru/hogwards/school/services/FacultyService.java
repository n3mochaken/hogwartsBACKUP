package ru.hogwards.school.services;

import org.springframework.stereotype.Service;
import ru.hogwards.school.exeptions.FacultyNotFoundException;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }  public Collection<Faculty> findByName(String name) {
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Student> getAllStudentsOfFaculty(Long id) {
        return facultyRepository.getReferenceById(id).getStudents();
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
