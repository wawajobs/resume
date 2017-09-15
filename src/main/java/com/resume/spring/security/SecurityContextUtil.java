package com.resume.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityContextUtil {
	
	 public static SecurityContext getSecurityContext()
	  {
	    return SecurityContextHolder.getContext();
	  }
	  
	  public static Authentication getAuthentication()
	  {
	    return getSecurityContext().getAuthentication();
	  }
	  
	  public static UserDetails getUserDetails()
	  {
	    if (getAuthentication() != null)
	    {
	      Object principal = getAuthentication().getPrincipal();
	      if ((principal instanceof UserDetails)) {
	        return (UserDetails)principal;
	      }
	    }
	    return null;
	  }

}
