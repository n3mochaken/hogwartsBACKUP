package ru.hogwards.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwards.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Collection<Student> findByAgeBetween(int min, int max);




}
