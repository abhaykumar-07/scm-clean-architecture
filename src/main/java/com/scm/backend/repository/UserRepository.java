package com.scm.backend.repository;

import com.scm.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@RepositoryRestResource(exported = false)          // from this we can disable 'users' api creating by default by 'Spring Data Rest'
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);  //  Required for login by email
}
