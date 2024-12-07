package com.chonchul.lecture.persistence.repository;

import com.chonchul.lecture.persistence.entity.StudentLecture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentLectureRepository extends JpaRepository<StudentLecture, Long> {
    @Query("SELECT sl.lecture.id FROM StudentLecture sl WHERE sl.student.id = :studentId")
    List<Long> findLectureIdByStudentId(@Param("studentId") Long studentId);
}
