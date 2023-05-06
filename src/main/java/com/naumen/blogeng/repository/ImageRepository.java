package com.naumen.blogeng.repository;

import com.naumen.blogeng.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    long count();
}
