package com.liftoff.libraryapp.repositories;

import com.liftoff.libraryapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

// **** Description *** //
// This class accesses the User based on email address //
// **** ********** *** //

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
}
