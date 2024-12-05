package com.chonchul.attend.persistence;

import com.chonchul.attend.persistence.entity.Attend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendRepository extends JpaRepository<Attend, Long> {
}
