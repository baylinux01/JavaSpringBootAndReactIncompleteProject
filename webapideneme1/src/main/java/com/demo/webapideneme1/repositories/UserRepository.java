package com.demo.webapideneme1.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByUsername(String username);

//	@Query(value=
//	"SELECT * FROM user u WHERE u.name LIKE %:string% OR u.surname LIKE %:string%"
//	,nativeQuery=true)
	@Query(value=
	"SELECT * FROM user u WHERE lower(u.name) "
	+ "LIKE lower(concat(\"%\",:string,\"%\")) "
	+ "OR lower(u.surname) "
	+ "LIKE lower(concat(\"%\",:string,\"%\"))"
			,nativeQuery=true)
	List<User> getSearchedUsers(String string);

	


}
