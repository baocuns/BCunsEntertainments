package com.cuns.bce.repositories;

import com.cuns.bce.entities.Authority;
import com.cuns.bce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
//    Set<Authority> findAllByUser(UUID id);
    // get all authorities of uid user in table authority
    Set<Authority> findAllByUser(User user);
}