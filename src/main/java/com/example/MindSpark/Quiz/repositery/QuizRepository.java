package com.example.MindSpark.Quiz.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MindSpark.Quiz.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Integer>{

	List<Quiz> findAllByCategory(String category);

	List<Quiz> findAllBySubjectType(String subjectType);
	

}
