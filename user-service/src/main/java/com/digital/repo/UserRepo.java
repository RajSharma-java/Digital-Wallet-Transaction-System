package com.digital.repo;

import com.digital.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.email = :email AND u.phone = :phone")
    Optional<User> findByEmailAndPhone(@Param("email") String email, @Param("phone") Long phone);
    //    Optional<User>findByEmailAndPhone(String email, Long phone);


    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    Page<User> getActiveUsers(Pageable pageable);
//    Page<User> findByIsDeletedFalse(Pageable pageable);

    Optional<User> findByPhone(Long phone);
    Optional<User> findByEmail(String email);
}

