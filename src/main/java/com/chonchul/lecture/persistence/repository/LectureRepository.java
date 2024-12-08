package com.chonchul.lecture.persistence.repository;

import com.chonchul.lecture.persistence.entity.Lecture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByIdIn(List<Long> ids);
    List<Lecture> findByTeacherId(Long userId);
}
