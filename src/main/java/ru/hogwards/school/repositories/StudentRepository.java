package ru.hogwards.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwards.school.model.AmountOfStudents;
import ru.hogwards.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT count(Id) FROM student",nativeQuery = true)
    Integer countStudents();
    @Query(value = "SELECT AVG(age) From student",nativeQuery = true)
    Float AvgAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();





}
