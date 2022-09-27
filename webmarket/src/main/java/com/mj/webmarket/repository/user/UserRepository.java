package com.mj.webmarket.repository.user;

import com.mj.webmarket.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String emial);
    public boolean existsByEmail(String email);
}
