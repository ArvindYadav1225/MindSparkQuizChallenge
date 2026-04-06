package com.example.MindSpark.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.MindSpark.Quiz.entity.Quiz;
import com.example.MindSpark.Quiz.repositery.QuizRepository;



@SpringBootApplication
public class MindSparkQuizApplication implements CommandLineRunner {
@Autowired
    QuizRepository qr;

    public static void main(String[] args) {
        SpringApplication.run(MindSparkQuizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    	
    	

    
    }
}