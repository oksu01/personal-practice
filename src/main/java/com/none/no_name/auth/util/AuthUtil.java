package com.none.no_name.auth.util;

import static com.none.no_name.auth.util.AuthConstant.*;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.none.no_name.global.exception.business.BusinessException;

import jakarta.servlet.http.HttpServletResponse;

public class AuthUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void setResponse(HttpServletResponse response, BusinessException e) throws IOException {
		response.setStatus(e.getHttpStatus().value());
		response.setContentType("application/json");
		response.setHeader(LOCATION, "/auth/refresh");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
			mapper.writeValueAsString(
				e.getMessage()
			)
		);
	}

	public static void setResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setHeader(LOCATION, "/auth/refresh");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
