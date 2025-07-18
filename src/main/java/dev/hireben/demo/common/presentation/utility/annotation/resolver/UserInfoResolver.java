package dev.hireben.demo.common.presentation.utility.annotation.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import dev.hireben.demo.common.presentation.dto.UserInfoDTO;
import dev.hireben.demo.common.presentation.utility.JwtUtil;
import dev.hireben.demo.common.presentation.utility.annotation.UserInfo;
import dev.hireben.demo.common.presentation.utility.context.HttpHeaderKey;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInfoResolver implements HandlerMethodArgumentResolver {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JwtUtil jwtUtil;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public boolean supportsParameter(@NonNull MethodParameter parameter) {
    return parameter.getParameterType().equals(UserInfoDTO.class) &&
        parameter.hasParameterAnnotation(UserInfo.class);
  }

  // ---------------------------------------------------------------------------//

  @Override
  @Nullable
  public Object resolveArgument(
      @NonNull MethodParameter parameter,
      @Nullable ModelAndViewContainer mavContainer,
      @NonNull NativeWebRequest webRequest,
      @Nullable WebDataBinderFactory binderFactory) throws Exception {

    String token = webRequest.getHeader(HttpHeaderKey.AUTH_TOKEN);
    if (token == null || token.isBlank()) {
      throw new MissingRequestHeaderException(HttpHeaderKey.AUTH_TOKEN, parameter);
    }

    Claims claims = jwtUtil.parseToken(token.substring("Bearer ".length()));

    return UserInfoDTO.builder()
        .id(claims.getSubject())
        .build();
  }

}
