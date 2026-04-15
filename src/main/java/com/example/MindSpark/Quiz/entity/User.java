package com.example.MindSpark.Quiz.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int  id;

	@Column(unique = true)
	private String gmail;
	String name;
	@Column(unique=true)
	String userName;
	
	String password;
	String coins="0";
	String image;
	String language;
    String score="0";
    String referelCode="";
    List<String> friends=new ArrayList<>();
    public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	int rating=0;
    public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReferelCode() {
		return referelCode;
	}
	public void setReferelCode(String referelCode) {
		this.referelCode = referelCode;
	}
	List<Integer> trueQuiz=new ArrayList<>();
    List<Integer> completedQuiz=new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AnalysisResult> analysisResult=new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getCompletedQuiz() {
		return completedQuiz;
	}
	public List<AnalysisResult> getAnalysisResult() {
		return analysisResult;
	}
	public void setAnalysisResult(List<AnalysisResult> analysisResult) {
		this.analysisResult = analysisResult;
	}
	public void setCompletedQuiz(List<Integer> completedQuiz) {
		this.completedQuiz = completedQuiz;
	}
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCoins() {
		return coins;
	}
	public void setCoins(String coins) {
		this.coins = coins;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public List<Integer> getTrueQuiz() {
		return trueQuiz;
	}
	public void setTrueQuiz(List<Integer> trueQuiz) {
		this.trueQuiz = trueQuiz;
	}
	public List<Integer> getFalseQuiz() {
		return falseQuiz;
	}
	public void setFalseQuiz(List<Integer> falseQuiz) {
		this.falseQuiz = falseQuiz;
	}
	public List<Integer> getCurrentQuiz() {
		return currentQuiz;
	}
	public void setCurrentQuiz(List<Integer> currentQuiz) {
		this.currentQuiz = currentQuiz;
	}
	public int getCompletedQuizCurrenTime() {
		return completedQuizCurrenTime;
	}
	public void setCompletedQuizCurrenTime(int completedQuizCurrenTime) {
		this.completedQuizCurrenTime = completedQuizCurrenTime;
	}
	List<Integer> falseQuiz=new ArrayList<>();
    List<Integer>currentQuiz =new ArrayList<>();
    int completedQuizCurrenTime=0;
    int selectedCurrentTimeQuizNo=0;
	public int getSelectedCurrentTimeQuizNo() {
		return selectedCurrentTimeQuizNo;
	}
	public void setSelectedCurrentTimeQuizNo(int selectedCurrentTimeQuizNo) {
		this.selectedCurrentTimeQuizNo = selectedCurrentTimeQuizNo;
	}
}
