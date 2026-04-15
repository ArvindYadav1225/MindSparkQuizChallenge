package com.example.MindSpark.Quiz.repositery;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MindSpark.Quiz.entity.AnalysisResult;
import com.example.MindSpark.Quiz.entity.User;
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult,Integer> {

	void deleteByUser(User u);

}
