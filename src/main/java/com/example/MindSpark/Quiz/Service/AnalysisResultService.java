package com.example.MindSpark.Quiz.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MindSpark.Quiz.entity.User;
import com.example.MindSpark.Quiz.repositery.AnalysisResultRepository;

import jakarta.transaction.Transactional;

@Service
public class AnalysisResultService {



	    @Autowired
	    private AnalysisResultRepository repo;

	    @Transactional
	    public void deleteByUser(User user) {
	        repo.deleteByUser(user);
	    }
	
}
