package io.pivotal.security.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.pivotal.security.credential.JsonCredentialValue;
import io.pivotal.security.entity.JsonCredentialVersionData;
import io.pivotal.security.exceptions.ParameterizedValidationException;
import io.pivotal.security.util.JsonObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonCredentialVersion extends CredentialVersion<JsonCredentialVersion> {

  private final JsonObjectMapper objectMapper;
  private final JsonCredentialVersionData delegate;

  public JsonCredentialVersion() {
    this(new JsonCredentialVersionData());
  }

  public JsonCredentialVersion(JsonCredentialVersionData delegate) {
    super(delegate);
    this.delegate = delegate;
    /*
    It is alright to use a "new" ObjectMapper here because the JSON data does
    not have properties that is maps to, thereby not facing a problem with the
    casing being defined. Using JsonObjectMapper for consistency across project.
     */
    this.objectMapper = new JsonObjectMapper();
  }

  public JsonCredentialVersion(String name) {
    this(new JsonCredentialVersionData(name));
  }

  public JsonCredentialVersion(JsonCredentialValue jsonValue, Encryptor encryptor) {
    this();
    this.setEncryptor(encryptor);
    this.setValue(jsonValue.getValue());
  }

  @Override
  public String getCredentialType() {
    return delegate.getCredentialType();
  }

  @Override
  public void rotate() {
    Map<String, Object> value = this.getValue();
    this.setValue(value);
  }

  public Map<String, Object> getValue() {
    String serializedValue = (String) super.getValue();
    try {
      return objectMapper.readValue(serializedValue, Map.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public JsonCredentialVersion setValue(Map<String, Object> value) {
    if (value == null) {
      throw new ParameterizedValidationException("error.missing_value");
    }

    try {
      String serializedString = objectMapper.writeValueAsString(value);

      return super.setValue(serializedString);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
