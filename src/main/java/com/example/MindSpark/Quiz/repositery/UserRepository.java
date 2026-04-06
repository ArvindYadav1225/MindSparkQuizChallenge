package com.example.MindSpark.Quiz.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MindSpark.Quiz.entity.User;

public interface UserRepository extends JpaRepository<User,String>{
	public User getByGmail(String gmail);
	public User getByUserName(String userName);
	public List<User> findAllByName(String userNameOrName);
	

}
