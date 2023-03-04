package com.naumen.blogeng.repository;

import com.naumen.blogeng.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BlogUser, Long> {
}
