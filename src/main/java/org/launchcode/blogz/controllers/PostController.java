package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		String title = request.getParameter("title");
		String body = request.getParameter("body");		
		HttpSession session =request.getSession();
		User author =  getUserFromSession(session);
		
		if (title == "" && body == ""){
			model.addAttribute("error", "Add Title and Post");
			return "newpost";
		}else if (title == ""){
			model.addAttribute("error", "Add a Title");
			return "newpost";
		}else if (body == ""){
			model.addAttribute("error", "Write a Post");
			return "newpost";			
		}else{
			Post newPost = new Post(title,body,author);
			postDao.save(newPost);
			 
			int postId = newPost.getUid();
			User user = author;
			String username = user.getUsername();
			String usernameFormat = "redirect:/blog/%1s/%2s";
			String unformat =String.format(usernameFormat,username,postId);
								
		     return unformat; // TODO - this redirect should go to the new post's page  
		}
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		List<Post> post = postDao.findByUid(uid);
		model.addAttribute("posts", post);
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		User user = userDao.findByUsername(username);
		List<Post> userzpost = user.getPosts();
		model.addAttribute("posts", userzpost);
		return "blog";
	}
	
}
