package vttp2022.miniproject.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class VerificationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    HttpServletRequest httpReq = (HttpServletRequest)request;
    HttpServletResponse httpResp = (HttpServletResponse)response;


    HttpSession session = httpReq.getSession();
    String username = (String)session.getAttribute("username");

    System.out.printf(">>>>>> url: %s\n", httpReq.getRequestURI().toString());
    System.out.printf(">>>>>> name: %s\n", username);

    if ((null == username) || (username.trim().length() <= 0)) {
        httpResp.sendRedirect("/index.html");
        return;
    }

    chain.doFilter(request, response);
    }
    
}
