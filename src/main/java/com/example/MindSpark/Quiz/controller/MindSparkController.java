package com.example.MindSpark.Quiz.controller;
import com.example.MindSpark.Quiz.entity.Quiz;
import com.example.MindSpark.Quiz.entity.User;
import com.example.MindSpark.Quiz.helper.AnalysisResult;
import com.example.MindSpark.Quiz.repositery.QuizRepository;
import com.example.MindSpark.Quiz.repositery.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MindSparkController {
	 List<String> userSuffix = Arrays.asList(
	            "royal", "king", "boss", "legend",
	            "master", "pro", "elite", "prime", "lord",
	            "savage", "killer", "warrior", "ninja", "ghost",
	            "shadow", "venom", "blaze", "storm", "thunder",
	            "gamer", "sniper", "hunter", "striker", "destroyer",
	            "assassin", "reaper", "titan", "gladiator", "viper",
	            "luxe", "op", "max", "ultra",
	            "fx", "zx", "dx", "yt", "live",
	            "squad", "gang", "crew", "vibes", "zone",
	            "hub", "world", "nation", "empire", "club",
	            "vibe", "aura", "spark", "pixel", "neo",
	            "glitch", "flux", "wave", "nova", "orbit"
	        );

	@Autowired
	UserRepository ur;
	@Autowired
	QuizRepository qr;
//@GetMapping("/home")
//public String home() {
//	
//	return"home";
//}
@GetMapping("/signup")
public String signup(Model m) {
  m.addAttribute("user",new User());
	return"signup";
}
@PostMapping("submitSignup")
public String submitSignup(@Valid @ModelAttribute("user") User user,
                           BindingResult result,@RequestParam("reCode") String reCode,
                           @RequestParam String cpassword,
                           Model model,
                           HttpSession session) {

    

    if(user.getGmail() == null || user.getGmail().isEmpty())
        result.rejectValue("gmail", "error.user", "Please enter your gmail.");
    if(ur.getByGmail(user.getGmail())!=null) {
    	 result.rejectValue("gmail", "error.user", "this gmail is already registered.");
     }
    if(user.getName() == null || user.getName().isEmpty())
        result.rejectValue("name", "error.user", "Please enter your name.");
    else {
        if(user.getName().length() < 3)
            result.rejectValue("name", "error.user", "Name must be at least 3 characters.");
        else if(user.getName().length() > 15)
            result.rejectValue("name", "error.user", "Name must be maximum 15 characters.");
    }

    if(user.getPassword() == null || user.getPassword().isEmpty())
        result.rejectValue("password", "error.user", "Enter a strong password.");
    else {
        if(user.getPassword().length() < 8)
            result.rejectValue("password", "error.user", "Password must be at least 8 characters.");
        else if(user.getPassword().length() > 15)
            result.rejectValue("password", "error.user", "Password must be maximum 15 characters.");
        else
        	if(!user.getPassword().equals(cpassword))
                result.reject("cpassworderror",  "Password and confirm password cannot be match match.");

    }
  
  
    
    if(result.hasErrors()) {
    	System.out.println("result has an error.");
        return "signup";
    }
    Random random = new Random();
     String username=user.getName()+"_"+userSuffix.get(random.nextInt(57))+(100 + random.nextInt(900));
     user.setUserName(username);
     int imgNo = random.nextInt(70); // 0–69
     String  referelCode=generateReferelCode(user.getUserName());
     
      System.out.println(referelCode);
	 model.addAttribute("referelCode",referelCode);
	 user.setReferelCode(referelCode);
	 
     String img = "https://i.pravatar.cc/150?img=" + imgNo;
     user.setImage(img);
    ur.save(user);
    session.setAttribute("gmail", user.getGmail());
    model.addAttribute("user", user);
    return "signupSuccessful";
}
@GetMapping("userhomepage")
public String userhomepage(HttpSession session,Model model) {
	User u=ur.getByGmail((String)session.getAttribute("gmail"));
	 model.addAttribute("user", u);
	return "userhomepage";
}
@GetMapping("/selectSubject")
public String selectSubject( HttpSession session ,Model model) {
	
	if(ur.getByGmail((String)session.getAttribute("gmail"))==null)return "signup";
	else
		model.addAttribute("user",ur.getByGmail((String)session.getAttribute("gmail")));
	return"selectSubject";
}
@GetMapping("/profile")
public String profile(HttpSession session,Model m) {
	String gmail=(String)session.getAttribute("gmail");
	if(gmail==null) {
		return"signup";
		
	}
	m.addAttribute("user", ur.getByGmail(gmail));
	return"profile";
}
@GetMapping("/updateprofile")
public String updateprofile() {
	
	return"updateprofile";
}
//List<Map<String,String>> result=new ArrayList<>();
@GetMapping("/nextQuestion/{quizNo}")
public String nextQuestion(@PathVariable("quizNo") String quizNo,Model m,HttpSession session) {
	User u=ur.getByGmail((String)session.getAttribute("gmail"));
	List<AnalysisResult> analysisResult=u.getAnalysisResult();
	u.setCompletedQuizCurrenTime(Integer.parseInt(quizNo)+1);
	 if(Integer.parseInt(quizNo)==9) {
		 m.addAttribute("analysisResult",analysisResult);
		 u.setAnalysisResult(new ArrayList<>());
		 u.setCurrentQuiz(new ArrayList<>());
		 System.out.println(analysisResult);
		 u.setCompletedQuizCurrenTime(0);
		 ur.save(u);
		  return"analysisPage";
		  
	  }
	
	 
//	 String ansOption="";
//	 Quiz q=quizes.get(Integer.parseInt(quizNo)+1);
//	   if(q.getA().equals(q.getAnswer())) ansOption="A";
//	   if(q.getB().equals(q.getAnswer())) ansOption="B";
//	   if(q.getC().equals(q.getAnswer())) ansOption="C";
//	   if(q.getD().equals(q.getAnswer())) ansOption="D";
//	 m.addAttribute("ansOption",ansOption);
//	m.addAttribute("ans","false");
//	 m.addAttribute("showans","false");
	 m.addAttribute("user", u);
	 m.addAttribute("quizNo",Integer.parseInt(quizNo)+1);
	 m.addAttribute("quizes",quizes);
	 ur.save(u);
	return "questionpage";

}


@GetMapping("/checkresult2/{qId}/{ao}/{uo}/{res}")
@ResponseBody
public void checkresult2(@PathVariable("qId")  String qId,@PathVariable("ao")  String ao,@PathVariable("uo") String uo,@PathVariable("res") String res,HttpSession session) {
	User u=ur.getByGmail((String)session.getAttribute("gmail"));
	List<AnalysisResult> analysisResult=u.getAnalysisResult();
	  String score=u.getScore() ;
	  List<Integer> cl=u.getCompletedQuiz();
	  if(!cl.contains(Integer.parseInt(qId))) {
		  cl.add(Integer.parseInt(qId));
		  u.setCompletedQuiz(cl);
	  }
	  
	  System.out.println("score"+score);
	  String coins=u.getCoins();
	       System.out.println("coins "+coins);
	       score=String.valueOf(Integer.parseInt(score)+1);
	      
	         AnalysisResult result=new AnalysisResult();
			  String str="Question "+(analysisResult.size()+1);
			  
			  result.setQuestionno(str);
			  result.setUserOption(uo);
			  result.setCorrectOption(ao);
			  result.setResult(res);
			 
			  analysisResult.add(result);
			  u.setAnalysisResult(analysisResult);
			  
	if(res.equals("true")) {
		u.setRating(u.getRating()+6);
		System.out.println("true");
		coins=String.valueOf(Integer.parseInt(coins)+5);
		  List<Integer> tl=u.getTrueQuiz();
		  
		  if(!tl.contains(Integer.parseInt(qId))) {
			  tl.add(Integer.parseInt(qId));
			  u.setTrueQuiz(tl);
		  }
		
		 
		  u.setCoins(coins);
		  u.setScore(score);
		  ur.save(u);
		  return;
		  
	  }
	System.out.println("false");
	coins=String.valueOf(Integer.parseInt(coins)+5);
	 u.setCoins(coins);
	  u.setScore(score);
	  u.setRating(u.getRating()-2);
	coins=String.valueOf(Integer.parseInt(coins)+5);
	  List<Integer> fl=u.getFalseQuiz();
	  if(!fl.contains(Integer.parseInt(qId))) {
		  fl.add(Integer.parseInt(qId));
		  u.setFalseQuiz(fl);
	  }
	  
	  ur.save(u);
    
}
//@GetMapping("/checkResult/{uans}/{ans}/{id}/{qNo}/{uo}/{ao}")
//@ResponseBody
//public boolean checkResult( @PathVariable("uans")  String uans,@PathVariable("ans") String ans,@PathVariable("id") String id,@PathVariable("qNo") String qNo,@PathVariable("uo") String uo,@PathVariable("ao") String ao,HttpSession session) {
//	
//	System.out.println("result is checking............");
//	System.out.println(uans+ans);
//	User u=ur.getByGmail((String)session.getAttribute("gmail"));
//	System.out.println(u);
//	if(uans.equals(ans)) {
//		List<Integer>l=u.getTrueQuiz();
//		List<Integer>l2=u.getCompletedQuiz();
//		l.add(Integer.parseInt(id));
//		  u.setTrueQuiz(l);
//		  l2.add(Integer.parseInt(id));
//		  u.setCompletedQuiz(l2);
//		  System.out.println(true);
//		  resultAnalysis.put(quizes.get(Integer.parseInt(qNo)),Arrays.asList("","",""));
//		return true;
//	}
//	List<Integer>l=u.getFalseQuiz();
//	l.add(Integer.parseInt(id));
//	u.setFalseQuiz(l);
//	List<Integer>l2=u.getCompletedQuiz();
//	l2.add(Integer.parseInt(id));
//	  u.setCompletedQuiz(l2);
//	  resultAnalysis.put(quizes.get(Integer.parseInt(qNo)),Arrays.asList("","",""));
//	  System.out.println(false);
//	return false;
//}
List<Quiz> quizes;

@GetMapping("/questionPage/{questionNum}/{questionLevel}")
public String questionPage( @PathVariable("questionNum") String questionNum,@PathVariable("questionLevel")  String questionlevel,Model m,HttpSession session) {
	//System.out.println(session);
	//System.out.println(session.getAttribute("subject"));
	 User u=ur.getByGmail((String)session.getAttribute("gmail"));
	     quizes=qr.findAllBySubjectType((String)session.getAttribute("subject"));
	    quizes= quizes.stream().filter((q)->
	    	!u.getCompletedQuiz().contains(q.getId())
	     ).toList();
	    System.out.println(quizes.size());
	   quizes= quizes.stream().limit(Integer.parseInt(questionNum)).toList();
	    System.out.println(quizes.size());
	   
	    List<Integer> quizesId=quizes.stream().map((q)->q.getId()).toList();
	    //System.out.println(quizesId);
	    u.setCurrentQuiz(quizesId);
	    System.out.println(u.getCurrentQuiz());
	    String ansOption="";
		 Quiz q=quizes.get(0);
		   if(q.getA().equals(q.getAnswer())) ansOption="A";
		   if(q.getB().equals(q.getAnswer())) ansOption="B";
		   if(q.getC().equals(q.getAnswer())) ansOption="C";
		   if(q.getD().equals(q.getAnswer())) ansOption="D";
		 m.addAttribute("ansOption",ansOption);
	
	 m.addAttribute("user", u);
	 m.addAttribute("quizNo", 0);
	 m.addAttribute("quizes",quizes);
	 session.setAttribute("quizes",quizes);
	 ur.save(u);
	return"questionPage";
}
@GetMapping("/login")
public String login() {
	return"login";
}
@PostMapping("/submitLogin")
public String submitLogin(@RequestParam("userName") String userNameOrGmail,String password,Model m,HttpSession session) {
	  User user=ur.getByUserName(userNameOrGmail);
	  if(user==null) {
		    user=ur.getByGmail(userNameOrGmail);
		    if(user==null)
		  return"login";
	  }
	  else {
		  if(!user.getPassword().equals(password))return "login";
	  }
	session.setAttribute("gmail", user.getGmail());
	m.addAttribute("user", user);
	return"userhomepage";
}
@PostMapping("/submitProfileUpdate")
public String submitProfileUpdate(@RequestParam String name,@RequestParam String userName,@RequestParam String gmail,@RequestParam String password,HttpSession session) {
	 User u=ur.getByGmail((String)session.getAttribute("gmail"));
	 if(u.getPassword().equals(password)) {
		 
	 }
	return"";
}
@GetMapping("/noQuestion/{subject}")
public String noQuestion(@PathVariable("subject") String subject,HttpSession session) {
	
	session.setAttribute("subject", subject);

	
	return "NoQuestion";
}

//@GetMapping("/resultAnalysis")
//	public String resultAnalysis() {
//		
//		return"";
//	}
//@GetMapping("analysisPage")
//public String analysisPage(Model m) {
//	
//	return "analysisPage";
//}
@GetMapping("/referApp")
public String referApp() {
	
	return"referelPage";
}
@GetMapping("/previousChallenge")
public String previousChallenge(HttpSession session,Model m) {
	User u=ur.getByGmail((String)session.getAttribute("gmail"));
	
	System.out.println(u.getCompletedQuizCurrenTime()+" &&"+u.getCurrentQuiz().size());
	  if(u.getCompletedQuizCurrenTime()==u.getCurrentQuiz().size()) {
		  m.addAttribute("user", u);
		  return "userhomepage";
	  }
	  else
	  {
		  int completedQuizNo=u.getCompletedQuizCurrenTime();
		  //int noOfSelectedQuiz=u.getSelectedCurrentTimeQuizNo();
		  List<Integer> currentQuiz=u.getCurrentQuiz();
		  quizes=currentQuiz.stream().map((q)->qr.getById(q)).toList();
		  System.out.println(quizes);
		
		 
		  
	  }
	return nextQuestion( String.valueOf(4), m,session);
	 // return "redirect:/userhomepage";

}
@GetMapping("/findFriend")
public String findFriend() {
	return"findFriendPage";
}
@PostMapping("/searchFriends")

public String searchFriend(@RequestParam("userName") String userNameOrName,Model m,HttpSession session) {
	User user=ur.getByUserName(userNameOrName);
	List<User>users=new ArrayList<>();
	users.add(user);
	if(user==null) {
		users=ur.findAllByName(userNameOrName);
		
	}
	System.out.println(users);
	
	  m.addAttribute("users",users);
	 session.setAttribute("findFriendsSearches", users);
	return "findFriendPage";
}
public String generateReferelCode(String userName) {
	   String referelCode="ARYA07"+userName.substring(0,4);
	   Random random = new Random();
	     referelCode+=(100 + random.nextInt(900));
	    if(!checkValidReferelCode(referelCode))return referelCode;
	    else
	    	return generateReferelCode(userName);
	   
	    
	     
	    
	  
	
	
	
}
@GetMapping("/referer")
public String refere( HttpSession session,Model m) {
	User u=ur.getByGmail((String)session.getAttribute("gmail"));
	
	return"referelPage";
}
@GetMapping("/leaderboard")
public String leaderboard(HttpSession session,Model m) {
	User u=ur.getByGmail((String)session.getAttribute("gmail"));  
	List<User> users=ur.findAll();
	users=users.stream().sorted((a,b)->b.getRating()-a.getRating()).toList();
	m.addAttribute("users",users);
	
	return "leaderboard";
}
@GetMapping("/logout")
public String logout( HttpSession session) {
	session.removeAttribute("gmail");
	return "redirect:/signup";
}
@GetMapping("/addFriend/{userName}")

public String  addFriend(@PathVariable("userName") String userName,HttpSession session,Model m) {
	System.out.println(userName);
	User u=ur.getByGmail((String)session.getAttribute("gmail"));  
	List<String > friends=u.getFriends();
	friends.add(userName);
	u.setFriends(friends);
	ur.save(u);
	List<User> users=(List<User>) session.getAttribute("findFriendsSearches");
	m.addAttribute("users",users);
	 return "findFriendPage";
}
@GetMapping("/myFriends")
public String myFriends( HttpSession session ,Model m) {
	User u=ur.getByGmail((String)session.getAttribute("gmail"));  
	List<String > friends=u.getFriends();
	List<User>users=new ArrayList<>();
	friends.forEach((a)->{
		users.add(ur.getByUserName(a));
	});
	m.addAttribute("friends",users);
	//.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"+users);
	return "myFriends";
}
@GetMapping("/checkValidReferelCode/{referelCode}")
@ResponseBody
public boolean checkValidReferelCode(@PathVariable("referelCode") String referelCode) {
	System.out.println(referelCode+"hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhheeeeeeeeeeeeeeeeeeeeeeeeeedferrrrrrrrrrrrrrrrrrrrrrrrr");
	 List<User> users=ur.findAll();
	      for(User u:users) {
	    	  System.out.println(u.getReferelCode()+referelCode);
	    	  if(u.getReferelCode().equals(referelCode)) {
	    		 System.out.println(true);
	    		  return true;
	    		  
	    	  
	    	  }
	      }
	return false;
}
String OTP;
String userGmail;
@GetMapping("/generateOtp")
@ResponseBody
public void generateOtp() {
	Random random2=new Random();
	OTP=String.valueOf(random2.nextInt(1000000));
	
}
@GetMapping("/sendOtpPage")
public String sendOtpPage() {
	
	return "";
}
@PostMapping("/sendOtp")
public String sendOtp(@RequestParam("gmail") String gmail,Model m) {
	List<User> users=ur.findAll();
	boolean f=false;
	 for(User u:users) {
		 if(u.getGmail().equals(gmail)) {
			 f=true;
			 userGmail=gmail;
			 break;
		 }
		 }
	 if(f==true) {
		 generateOtp();
		 
		 
	 }
	 m.addAttribute("invalidGmail","yes");
	 
	return "";
	
}

@GetMapping("/otpVerification")
@ResponseBody
public boolean otpVerification(@RequestParam("otp") String otp) {
	if(OTP.equals(otp)) {
		return true;
	}
	
	
	return false;
}
@GetMapping("/loginByGmail")
public String loginByGmail(HttpSession session) {
	session.setAttribute("gmail",userGmail);
	
	return "loginSuccessful";
}
@GetMapping("/updatePasswordPage")
public String updatePasswordPage() {
	
	return "";
}
 @PostMapping("/updatePassword")
 public String updatePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,@RequestParam("cNewPassword") String cNewPassword,HttpSession session,Model m) {
	 User u=ur.getByGmail((String)session.getAttribute("gmail")); 
	 if(u.getPassword().equals(oldPassword)) {
		 u.setPassword(newPassword);
		 ur.save(u);
		 return "";
		 
	 }
	 m.addAttribute("invalidOldPassword","yes");
	 return "";
 }
}
