package com.tiagoamp.booksapi.repository;

import com.tiagoamp.booksapi.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends
        JpaRepository<AppUser, Long>,
        JpaSpecificationExecutor<AppUser> {

    AppUser findByUsernameIgnoreCase(String username);
    Optional<AppUser> findByUsername(String username);
    boolean existsByEmail(String email);

}