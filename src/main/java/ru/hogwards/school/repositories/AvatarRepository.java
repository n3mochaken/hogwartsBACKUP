package ru.hogwards.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwards.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository <Avatar, Long> {

    Optional<Avatar> findByStudentId(Long studentID);
}
