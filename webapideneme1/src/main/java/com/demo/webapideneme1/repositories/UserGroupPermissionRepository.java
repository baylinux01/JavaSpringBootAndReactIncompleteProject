package com.demo.webapideneme1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.UserGroupPermission;

@Repository
public interface UserGroupPermissionRepository extends JpaRepository<UserGroupPermission, Long>{

	UserGroupPermission findByUserAndGroup(User user, Group group);

}
