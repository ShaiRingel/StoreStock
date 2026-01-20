package project.StoreStock.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.StoreStock.entity.User;
import project.StoreStock.service.SecurityService;

@Controller
public class SecurityController {

	private final SecurityService security;

	public SecurityController(SecurityService security){
		this.security = security;
	}

	private final static String REDIRECT_LOGIN = "redirect:/login";

	@RequestMapping("/login")
	public String showLoginForm(Model model, HttpServletRequest request) {
		User user = new User();
		model.addAttribute("user", user);

		String param = request.getParameter("incorrect");

		if(param != null)
			model.addAttribute("message", "Error: Invalid username or password");

		return "login-page";
	}

	@RequestMapping("/processLogin")
	public String processLogin(@ModelAttribute("user") User user, HttpServletRequest request, Model model) {
		String username = user.getUsername();

		if (security.isBlocked(username)) {
			return showBlockPage(username, model);
		}

		if(security.login(user)){
			HttpSession oldSession = request.getSession(false);

			if (oldSession != null)
				oldSession.invalidate();

			HttpSession newSession = request.getSession(true);
			newSession.setAttribute("user", user);

			security.resetAttempts(username);

			return "redirect:/mainScreen";
		}

		security.registerFailedLogin(username);

		if (security.isBlocked(username)) {
			return showBlockPage(username, model);
		}

		return REDIRECT_LOGIN + "?incorrect=true";
	}

	private String showBlockPage(String username, Model model) {
		model.addAttribute("username", username);
		model.addAttribute("count", security.getAttempts(username));
		return "access-denied";
	}

	@RequestMapping("/mainScreen")
	public String showMainScreen(HttpServletRequest request) {
		return "redirect:/mainpage";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return REDIRECT_LOGIN;
	}
}
