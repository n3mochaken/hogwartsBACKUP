package ru.hogwards.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwards.school.model.Faculty;

import java.util.Collection;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    Collection<Faculty> findFacultyByNameIgnoreCase(String name);
    Collection<Faculty> findFacultyByColorIgnoreCase(String color);


}
