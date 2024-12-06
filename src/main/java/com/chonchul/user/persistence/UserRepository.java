package com.chonchul.user.persistence;

import com.chonchul.user.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}
