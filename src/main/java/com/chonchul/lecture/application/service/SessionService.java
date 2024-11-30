package com.chonchul.lecture.application.service;

import com.chonchul.lecture.application.dto.SessionInfoDto;
import com.chonchul.lecture.application.exception.NotFoundSession;
import com.chonchul.lecture.persistence.entity.Session;
import com.chonchul.lecture.persistence.repository.SessionRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionInfoDto getLectureSession(LocalDate localDate, Long lectureId) {
        Session session = sessionRepository.findByLectureIdAndDate(lectureId,localDate)
                .orElseThrow(()->new NotFoundSession());

        return new SessionInfoDto(
                session.getWeek(),
                session.getDate().toString()
        );
    }
}
