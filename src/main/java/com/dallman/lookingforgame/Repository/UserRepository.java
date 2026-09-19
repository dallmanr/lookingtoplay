package com.dallman.lookingforgame.Repository;

import com.dallman.lookingforgame.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
