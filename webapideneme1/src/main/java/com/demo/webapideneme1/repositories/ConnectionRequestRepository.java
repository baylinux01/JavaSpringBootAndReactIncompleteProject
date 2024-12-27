package com.demo.webapideneme1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.ConnectionRequest;
import com.demo.webapideneme1.models.User;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest,Long>{

	ConnectionRequest findByConnectionRequestSenderAndConnectionRequestReceiver(User user, User user2);

}
