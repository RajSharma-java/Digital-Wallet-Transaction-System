package com.digital.repo;

import com.digital.model.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserProfile, Long> {


    @Query("SELECT u FROM UserProfile u WHERE u.email = :email AND u.phone = :phone")
    Optional<UserProfile> findByEmailAndPhone(@Param("email") String email, @Param("phone") Long phone);
    //    Optional<User>findByEmailAndPhone(String email, Long phone);


    @Query("SELECT u FROM UserProfile u WHERE u.isDeleted = false")
    Page<UserProfile> getActiveUsers(Pageable pageable);
//    Page<User> findByIsDeletedFalse(Pageable pageable);

    Optional<UserProfile> findByPhone(Long phone);
    Optional<UserProfile> findByEmail(String email);
}

