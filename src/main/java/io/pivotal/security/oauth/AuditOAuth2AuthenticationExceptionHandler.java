package io.pivotal.security.oauth;

import io.pivotal.security.entity.AuthFailureAuditRecord;
import io.pivotal.security.repository.AuthFailureAuditRecordRepository;
import io.pivotal.security.service.AuditRecordParameters;
import io.pivotal.security.util.InstantFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.EXP;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuditOAuth2AuthenticationExceptionHandler extends OAuth2AuthenticationEntryPoint {

  @Autowired
  InstantFactoryBean instantFactoryBean;

  @Autowired
  AuthFailureAuditRecordRepository auditRecordRepository;

  @Autowired
  JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  TokenStore jwtTokenStore;

  @Autowired
  ResourceServerTokenServices tokenServices;

  private JsonParser objectMapper = JsonParserFactory.create();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {

    final Map<String, Object> tokenInformation = extractTokenInformation(request);

    try {
      doHandle(request, response, authException);
    } finally {
      logAuthFailureToDb(tokenInformation, authException, new AuditRecordParameters(request, null), request.getMethod(), response.getStatus());
    }
  }

  private Map<String, Object> extractTokenInformation(HttpServletRequest request) {
    String token = (String) request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
    try {
      final Jwt jwt = JwtHelper.decode(token);

      final Map<String, Object> map = objectMapper.parseMap(jwt.getClaims());
      if (map.containsKey(EXP) && map.get(EXP) instanceof Integer) {
        Integer intValue = (Integer) map.get(EXP);
        map.put(EXP, new Long(intValue));
      }

      return map;
    } catch (RuntimeException mie) {
      return null;
    }
  }

  private void logAuthFailureToDb(Map<String, Object> tokenInformation, AuthenticationException authException, AuditRecordParameters parameters, String requestMethod, int statusCode) {
    RequestToOperationTranslator requestToOperationTranslator = new RequestToOperationTranslator(parameters.getPath()).setMethod(requestMethod);

    final Instant now;
    try {
      now = instantFactoryBean.getObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    String userId = null;
    String userName = null;
    String iss = null;
    long issued = -1;
    long expires = -1;
    String clientId = null;
    String scope = null;
    String grantType = null;

    if (tokenInformation != null) {
      List<String> scopeArray = (List<String>) tokenInformation.get("scope");
      userId = (String) tokenInformation.get("user_id");
      userName = (String) tokenInformation.get("user_name");
      iss = (String) tokenInformation.get("iss");
      issued = ((Number) tokenInformation.get("iat")).longValue();
      expires = ((Number) tokenInformation.get("exp")).longValue();
      clientId = (String) tokenInformation.get("client_id");
      scope = scopeArray == null ? null : String.join(",", scopeArray);
      grantType = (String) tokenInformation.get("grant_type");
    }

    AuthFailureAuditRecord authFailureAuditRecord = new AuthFailureAuditRecord()
        .setNow(now)
        .setOperation(requestToOperationTranslator.translate())
        .setFailureDescription(authException.getMessage())
        .setUserId(userId)
        .setUserName(userName)
        .setUaaUrl(iss)
        .setTokenIssued(issued)
        .setTokenExpires(expires)
        .setHostName(parameters.getHostName())
        .setPath(parameters.getPath())
        .setRequesterIp(parameters.getRequesterIp())
        .setXForwardedFor(parameters.getXForwardedFor())
        .setClientId(clientId)
        .setScope(scope)
        .setGrantType(grantType)
        .setMethod(requestMethod)
        .setStatusCode(statusCode);
    auditRecordRepository.save(authFailureAuditRecord);
  }
}

