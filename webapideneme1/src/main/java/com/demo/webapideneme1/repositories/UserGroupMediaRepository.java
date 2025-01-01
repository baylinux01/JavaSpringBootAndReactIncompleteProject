package com.demo.webapideneme1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.UserGroupMedia;

@Repository
public interface UserGroupMediaRepository extends JpaRepository<UserGroupMedia, Long>{


	List<UserGroupMedia> findByGroup(Group group);

}
