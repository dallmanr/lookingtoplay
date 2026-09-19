package com.dallman.lookingtoplay.Repository;

import com.dallman.lookingtoplay.User.User;
import org.springframework.data.jpa.repository.JpaRepository;


/*
 * findBy... Spring will automatically determine what to find by so long as the method signature is findBy... This is a derived query
 * Appending additional information to the query can be achieved with the use of And...
 * */
public interface UserRepository extends JpaRepository<User, Integer> {
}
