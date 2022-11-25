package com.expense.tracker.helpers;

import com.expense.tracker.helpers.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthHelper extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filter)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) req;
    HttpServletResponse httpResponse = (HttpServletResponse) res;

    String authHeader = httpRequest.getHeader("Authorization");

    if (authHeader != null) {
      String[] authHeaderArray = authHeader.split("Bearer ");

      if (authHeaderArray.length > 1 && authHeaderArray[1] != null) {
        String token = authHeaderArray[1];

        try {
          Claims claims = Jwts
              .parser()
              .setSigningKey(Constants.API_SECRET_KEY)
              .parseClaimsJws(token).getBody();

          httpRequest.setAttribute("user_id", Integer.parseInt(claims.get("user_id").toString()));
        } catch (Exception e) {
          httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid/Expired Token");
          return;
        }
      } else {
        httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token should be Bearer [token]");
        return;
      }
    } else {
      httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
      return;
    }

    filter.doFilter(req, res);
  }
}