package org.launchcode.blogz.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		HttpSession session =request.getSession();
		
		boolean valid_username= User.isValidUsername(username);
		boolean valid_password = User.isValidPassword(password);
		boolean verification;
		verification = verify.equals(password);
		
		
		
		if (! valid_username){
			
			model.addAttribute("username_error", "Invalid username");
			return "/signup";			
		} else if( ! valid_password){
			model.addAttribute("password_error", " What's wrong with you? This is not a valid password");
			return "/signup";
		}else if (! verification){
			model.addAttribute("verify_error", "Don't be stupid!!! The passwords need to match!!");
			return "/signup";
		}else{
			User user = new User(username,password);
			userDao.save(user);
			
			setUserInSession(session, user);
			}
		return "redirect:blog/newpost";
				
		}
		
	
	private User User(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session =request.getSession();
		
		
		User user = userDao.findByUsername(username);
		user.isMatchingPassword(password);
		setUserInSession(session,user);
		getUserFromSession(session);
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
