package com.naumen.blogeng.repository;

import com.naumen.blogeng.dto.DtoUser;
import com.naumen.blogeng.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
