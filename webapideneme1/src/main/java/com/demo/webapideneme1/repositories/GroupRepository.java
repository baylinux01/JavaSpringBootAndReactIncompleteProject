package com.demo.webapideneme1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

	List<Group> findByOwnerId(long userId);
	
	List<Group> findByMembersContaining(User member);

	

	

	

}
