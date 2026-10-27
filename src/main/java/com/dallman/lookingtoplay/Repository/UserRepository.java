package com.dallman.lookingtoplay.repository;

import com.dallman.lookingtoplay.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/*
 * findBy... Spring will automatically determine what to find by so long as the method signature is findBy... This is a derived query
 * Appending additional information to the query can be achieved with the use of And...
 * */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String userName);
}
