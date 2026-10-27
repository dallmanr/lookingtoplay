package com.dallman.lookingtoplay.repository;

import com.dallman.lookingtoplay.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByAuthority(String authority);
}
