package com.demo.webapideneme1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.UserGroupPermission;

@Repository
public interface UserGroupPermissionRepository extends JpaRepository<UserGroupPermission, Long>{

	

	List<UserGroupPermission> findByGroup(Group group);

	List<UserGroupPermission> findByUserAndGroup(User owner, Group group);

}
