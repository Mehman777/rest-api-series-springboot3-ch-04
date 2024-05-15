package com.tiagoamp.booksapi.repository;

import com.tiagoamp.booksapi.model.AppUser;
import com.tiagoamp.booksapi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<Token, Long> {

    Token findByUser(AppUser user);

    Token findByToken(String token);

}