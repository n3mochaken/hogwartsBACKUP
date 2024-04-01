package ru.hogwards.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwards.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    Collection<Faculty> findFacultyByNameIgnoreCase(String name);
    Collection<Faculty> findFacultyByColorIgnoreCase(String color);


}
