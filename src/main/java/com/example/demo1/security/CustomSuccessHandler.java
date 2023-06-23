package com.example.demo1.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo1.dto.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication, request);
		if (response.isCommitted()) {
			return;
		}
/*		HttpSession session = request.getSession(false);
		if(session!=null){
			Cookie cookie = new Cookie("username","anony");
			cookie.setPath("/");
			AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			cookie.setValue(appUser.getUsername());
			response.addCookie(cookie);
		}else{
			Cookie cookie = new Cookie("username","anony");
			cookie.setPath("/");
			response.addCookie(cookie);
		}	*/
			Cookie cookie = new Cookie("username","anony");
			cookie.setPath("/");
//			AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			AppUser appUser = (AppUser) authentication.getPrincipal();
			cookie.setValue(appUser.getUsername());
			response.addCookie(cookie);

			Cookie cookieUserid = new Cookie("userid","0");
			cookieUserid.setPath("/");
			cookieUserid.setValue(String.valueOf(appUser.getId()));
			response.addCookie(cookieUserid);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	
	private String determineTargetUrl(Authentication authentication, HttpServletRequest request) {
		String url = "";
		List<String> authoritiesAndRoles = new ArrayList<>();
		//List<String> roles = com.example.demo1.utils.SecurityUtils.getAuthorities();
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			authoritiesAndRoles.add(authority.getAuthority());
		}
		if (authoritiesAndRoles.contains("ACCESS_HOMEADMIN") || authoritiesAndRoles.contains("ROLE_ADMIN")) {
			url = "/quantri";
			//url=request.getHeader("Referer");
		}
		else
		{
			//url = "/";
			url=request.getHeader("Referer");
		}
		return url;
	}
	
/*	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ADMIN")) {
			return true;
		}
		return false;
	}
	
	private boolean isUser(List<String> roles) {
		if (roles.contains("USER")) {
			return true;
		}
		return false;
	}
	
	private boolean isGuest(List<String> roles) {
		if (roles.contains("GUEST")) {
			return true;
		}
		return false;
	}*/
}
