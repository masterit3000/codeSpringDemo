package com.elearningbackend.repository;

import com.elearningbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    Page<User> findByEmailContaining(String email, Pageable pageable);

    @Query("select a from User a where a.displayName like %?1%")
    Page<User> fetchUserByDisplayName(String displayName, Pageable pageable);

    Page<User> findByDisplayNameContaining (String displayName, Pageable pageable);

    User findByEmail (String email);

    User findByPhone (String phone);
}
