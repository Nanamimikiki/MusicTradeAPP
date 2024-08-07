package org.mude.repository;

import org.mude.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    List<User> findByRegistrationDateBetween(Date start, Date end);
//    List<User> findByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);
}
