package com.chonchul.lecture.persistence.repository;

import com.chonchul.lecture.persistence.entity.Session;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
        Optional<Session> findByLectureIdAndDate(Long lectureId, LocalDate date);
}
