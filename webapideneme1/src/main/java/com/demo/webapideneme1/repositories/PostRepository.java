package com.demo.webapideneme1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.webapideneme1.models.Comment;
import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.Media;
import com.demo.webapideneme1.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{


	List<Post> findByGroup(Group group);

	Post findByComment(Comment comment);

	Post findByMedia(Media media);

}
