package com.chonchul.user.persistence;

import com.chonchul.user.persistence.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}
