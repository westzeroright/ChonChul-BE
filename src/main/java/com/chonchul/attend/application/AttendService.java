package com.chonchul.attend.application;

import com.chonchul.attend.persistence.AttendRepository;
import com.chonchul.attend.persistence.entity.Attend;
import com.chonchul.attend.persistence.entity.Status;
import com.chonchul.auth.application.token.TokenResolver;
import com.chonchul.lecture.application.exception.NotFoundSession;
import com.chonchul.lecture.persistence.entity.Session;
import com.chonchul.lecture.persistence.repository.SessionRepository;
import com.chonchul.user.application.exception.NotFoundUserException;
import com.chonchul.user.persistence.StudentRepository;
import com.chonchul.user.persistence.entity.Student;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendService {
    private final TokenResolver tokenResolver;
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;
    private final AttendRepository attendRepository;

    public void attend(Long studentId, String qrToken) {
        Long sessionId = tokenResolver.getLectureDataByQrToken(qrToken);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundUserException());
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundSession());
        Attend attend = new Attend(LocalDateTime.now(), Status.ATTEND, session, student);
        attendRepository.saveAndFlush(attend);
    }

    public void late(Long studentId, Long sessionId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundUserException());
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundSession());
        Attend attend = new Attend(LocalDateTime.now(), Status.LATE, session, student);
        attendRepository.saveAndFlush(attend);
    }
}
