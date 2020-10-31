package epsilon.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import epsilon.core.Constant;
import epsilon.core.Utility;
import epsilon.security.Principal;
import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.domain.JWT;
import orion.controller.Notification;

public class SessionFilter implements Filter {

	public static final String sessionKey = Constant.sessionKey;

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

		boolean valid = true;

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String path = Utility.getPath(request);
		if (path.startsWith("/system")) {
			if ( //
			path.startsWith("/system/authentication") //
					|| path.startsWith("/system/monitor") //
			) {

			} else {
				// check jwt

				valid = false;

				String encodedJwt = null;
				if (request.getCookies() != null) {
					for (Cookie cookie : request.getCookies()) {
						if (Constant.jwtCookieName.equals(cookie.getName())) {
							encodedJwt = cookie.getValue();
							break;
						}
					}
				}

				if (encodedJwt == null) {
					String token = request.getHeader("Authorization");
					if (token != null && token.startsWith("Bearer ")) {
						encodedJwt = token.substring("Bearer ".length());
					}
				}

				if (encodedJwt != null) {
					try {
						JWT jwt = JWT.getDecoder().decode(encodedJwt, Constant.jwtVerifier);
						Principal principal = Utility.gson.fromJson(jwt.getAllClaims().get("principal").toString(), Principal.class);
						request.setAttribute(Principal.class.getCanonicalName(), principal);
						valid = true;
					} catch (JWTExpiredException e) {
						// throw new RuntimeException("Your session has expired, please sign out and sign in again");
					}
				}
			}
		}

		if (!valid) {
			Notification notification = new Notification();
			notification.addNotice("Your session has expired, please sign out and sign in again");

			Map<String, Object> map = new HashMap<>();
			map.put("type", "NOTIFICATION");
			map.put("noticeList", notification.getNoticeList());
			map.put("errorList", notification.getErrorList());
			map.put("fieldErrorList", notification.getFieldErrorList());

			HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			PrintWriter pw = response.getWriter();
			pw.println(Utility.gson.toJson(map));
			pw.flush();
		} else {
			chain.doFilter(servletRequest, servletResponse);
		}
	}

}
