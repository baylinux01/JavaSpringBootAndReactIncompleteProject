package com.demo.webapideneme1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{

}
