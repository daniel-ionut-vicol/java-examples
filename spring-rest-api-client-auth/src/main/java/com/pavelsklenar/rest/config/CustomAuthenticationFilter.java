package com.pavelsklenar.rest.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class CustomAuthenticationFilter extends GenericFilterBean {

	private List<String> allowedBussinesCodes = Arrays.asList(new String [] {"GCB"});
	
	private List<String> allowedCountryCode= Arrays.asList(new String [] {"TW"});;
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        
    	 HttpServletRequest httpRequest = (HttpServletRequest) request;
    	 String businessCode = httpRequest.getHeader("businessCode");
    	 String countryCode = httpRequest.getHeader("countryCode");
    	 if(allowedBussinesCodes.contains(businessCode) && allowedCountryCode.contains(countryCode)) {
    		 filterChain.doFilter(request, response);
    	 }else {
    		 throw new SecurityException();
    	 }
    }
}
