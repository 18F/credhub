package org.cloudfoundry.credhub.integration;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.cloudfoundry.credhub.CredentialManagerApp;
import org.cloudfoundry.credhub.util.CertificateReader;
import org.cloudfoundry.credhub.util.CertificateStringConstants;
import org.cloudfoundry.credhub.util.DatabaseProfileResolver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.cloudfoundry.credhub.util.AuthConstants.ALL_PERMISSIONS_ACTOR_ID;
import static org.cloudfoundry.credhub.util.AuthConstants.ALL_PERMISSIONS_TOKEN;
import static org.cloudfoundry.credhub.util.AuthConstants.USER_A_ACTOR_ID;
import static org.cloudfoundry.credhub.util.AuthConstants.USER_A_PATH;
import static org.cloudfoundry.credhub.util.AuthConstants.USER_A_TOKEN;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CredentialManagerApp.class)
@ActiveProfiles(value = {"unit-test", "unit-test-permissions"}, resolver = DatabaseProfileResolver.class)
@Transactional
@Ignore
public class CredentialAclEnforcementTest {

  private static final String CREDENTIAL_NAME = "/TEST/CREDENTIAL";
  private static final String SECOND_CREDENTIAL_NAME = "/TEST/CREDENTIAL2";

  @Autowired
  WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;
  private String uuid;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .build();
    seedCredentials();
  }

  @Test
  public void GET_byCredentialName_whenTheUserHasPermissionToReadCredential_returnsTheCredential()
      throws Exception {
    final MockHttpServletRequestBuilder get = get("/api/v1/data?name=" + CREDENTIAL_NAME)
        .header("Authorization", "Bearer " + USER_A_TOKEN);
    mockMvc.perform(get)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].type", equalTo("password")))
        .andExpect(jsonPath("$.data[0].name", equalTo(CREDENTIAL_NAME)));
  }

  @Test
  public void GET_byCredentialName_whenTheUserDoesntHavePermissionToReadCredential_returns404()
      throws Exception {
    final MockHttpServletRequestBuilder get = get("/api/v1/data?name=" + CREDENTIAL_NAME)
        .with(SecurityMockMvcRequestPostProcessors
            .x509(CertificateReader.getCertificate(CertificateStringConstants.SELF_SIGNED_CERT_WITH_CLIENT_AUTH_EXT)));
    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";
    mockMvc.perform(get)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void GET_byId_whenTheUserHasPermissionToReadCredential_returnsTheCredential()
      throws Exception {
    final MockHttpServletRequestBuilder get = get("/api/v1/data/" + uuid)
        .header("Authorization", "Bearer " + USER_A_TOKEN);
    mockMvc.perform(get)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.type", equalTo("password")))
        .andExpect(jsonPath("$.name", equalTo(CREDENTIAL_NAME)));
  }

  @Test
  public void GET_byId_whenTheUserDoesntHavePermissionToReadCredential_returns404()
      throws Exception {
    final MockHttpServletRequestBuilder get = get("/api/v1/data/" + uuid)
        .with(SecurityMockMvcRequestPostProcessors
            .x509(CertificateReader.getCertificate(CertificateStringConstants.SELF_SIGNED_CERT_WITH_CLIENT_AUTH_EXT)));
    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";
    mockMvc.perform(get)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void GET_byVersions_whenTheUserHasPermissionToReadCredential_returnsCredentialVersions()
      throws Exception {
    final MockHttpServletRequestBuilder get = get("/api/v1/data?name=" + CREDENTIAL_NAME + "&versions=2")
        .header("Authorization", "Bearer " + USER_A_TOKEN);
    mockMvc.perform(get)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].type", equalTo("password")))
        .andExpect(jsonPath("$.data[1].type", equalTo("password")))
        .andExpect(jsonPath("$.data[0].name", equalTo(CREDENTIAL_NAME)))
        .andExpect(jsonPath("$.data[1].name", equalTo(CREDENTIAL_NAME)));
  }

  @Test
  public void GET_byVersions_whenTheUserDoesntHavePermissionToReadCredential_returns404()
      throws Exception {
    final MockHttpServletRequestBuilder get = get("/api/v1/data?name=" + CREDENTIAL_NAME + "&versions=2")
        .with(SecurityMockMvcRequestPostProcessors
            .x509(CertificateReader.getCertificate(CertificateStringConstants.SELF_SIGNED_CERT_WITH_CLIENT_AUTH_EXT)));
    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";
    mockMvc.perform(get)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void PUT_whenTheUserLacksPermissionToReadTheAcl_returnsAccessDenied() throws Exception {
    final MockHttpServletRequestBuilder edit = put("/api/v1/data")
        .header("Authorization", "Bearer " + USER_A_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        // language=JSON
        .content("{\n"
            + "  \"name\" : \"" + CREDENTIAL_NAME + "\",\n"
            + "  \"value\" : \"Resistance is futile\",\n"
            + "  \"type\" : \"password\"\n"
            + "}")
        .accept(APPLICATION_JSON);

    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";

    this.mockMvc.perform(edit)
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void PUT_whenTheUserHasPermissionToWriteAnAcl_succeeds() throws Exception {
    final MockHttpServletRequestBuilder edit = put("/api/v1/data")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        // language=JSON
        .content("{\n"
            + "  \"name\" : \"" + CREDENTIAL_NAME + "\",\n"
            + "  \"value\" : \"Resistance is futile\",\n"
            + "  \"type\" : \"password\",\n"
            + "  \"additional_permissions\": [\n"
            + "     { \n"
            + "       \"actor\": \"bob\",\n"
            + "       \"operations\": [\"read\", \"read_acl\", \"write\"]\n"
            + "     }]"
            + "}")
        .accept(APPLICATION_JSON);

    this.mockMvc.perform(edit)
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void PUT_whenTheUserLacksPermissionToWriteAnAcl_returnsAccessDenied() throws Exception {
    final MockHttpServletRequestBuilder edit = put("/api/v1/data")
        .header("Authorization", "Bearer " + USER_A_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        // language=JSON
        .content("{\n"
            + "  \"name\" : \"" + SECOND_CREDENTIAL_NAME + "\",\n"
            + "  \"value\" : \"Resistance is futile\",\n"
            + "  \"type\" : \"password\",\n"
            + "  \"additional_permissions\": [\n"
            + "     { \n"
            + "       \"actor\": \"bob\",\n"
            + "       \"operations\": [\"read\", \"read_acl\", \"write\"]\n"
            + "     }]"
            + "}")
        .accept(APPLICATION_JSON);

    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";

    this.mockMvc.perform(edit)
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void PUT_whenTheUserUpdatesOwnPermission_returnsBadRequest() throws Exception {
    final MockHttpServletRequestBuilder edit = put("/api/v1/data")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        // language=JSON
        .content("{\n"
            + "  \"name\" : \"" + CREDENTIAL_NAME + "\",\n"
            + "  \"value\" : \"Resistance is futile\",\n"
            + "  \"type\" : \"password\",\n"
            + "  \"additional_permissions\": [\n"
            + "     { \n"
            + "       \"actor\": \"" + ALL_PERMISSIONS_ACTOR_ID + "\",\n"
            + "       \"operations\": [\"read\", \"read_acl\", \"write\"]\n"
            + "     }]"
            + "}")
        .accept(APPLICATION_JSON);

    String expectedError = "Modification of access control for the authenticated user is not allowed. Please contact an administrator.";

    this.mockMvc.perform(edit)
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void POST_whenTheUserUpdatesOwnPermission_returnsBadRequest() throws Exception {
    final MockHttpServletRequestBuilder post = post("/api/v1/data")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        .content("{"
            + "  \"name\": \"" + "test-credential" + "\",\n"
            + "  \"type\": \"password\","
            + "  \"additional_permissions\": [\n"
            + "     { \n"
            + "       \"actor\": \"" + ALL_PERMISSIONS_ACTOR_ID + "\",\n"
            + "       \"operations\": [\"read\"]\n"
            + "     }]"
            + "}");

    String expectedError = "Modification of access control for the authenticated user is not allowed. Please contact an administrator.";

    this.mockMvc.perform(post)
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void POST_whenTheUserLacksPermissionToReadTheAcl_returnsAccessDenied() throws Exception {
    final MockHttpServletRequestBuilder edit = post("/api/v1/data")
        .header("Authorization", "Bearer " + USER_A_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        // language=JSON
        .content("{\n"
            + "  \"name\" : \"" + CREDENTIAL_NAME + "\",\n"
            + "  \"type\" : \"password\"\n"
            + "}")
        .accept(APPLICATION_JSON);

    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";

    this.mockMvc.perform(edit)
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void POST_whenTheUserHasPermissionToWriteAnAcl_succeeds() throws Exception {
    final MockHttpServletRequestBuilder edit = post("/api/v1/data")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        // language=JSON
        .content("{\n"
            + "  \"name\" : \"" + CREDENTIAL_NAME + "\",\n"
            + "  \"type\" : \"password\"\n"
            + "}")
        .accept(APPLICATION_JSON);

    this.mockMvc.perform(edit)
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void POST_whenTheUserLacksPermissionToWriteAnAcl_returnsAccessDenied() throws Exception {
    final MockHttpServletRequestBuilder edit = post("/api/v1/data")
        .header("Authorization", "Bearer " + USER_A_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        // language=JSON
        .content("{\n"
            + "  \"name\" : \"" + SECOND_CREDENTIAL_NAME + "\",\n"
            + "  \"type\" : \"password\",\n"
            + "  \"additional_permissions\": [\n"
            + "     { \n"
            + "       \"actor\": \"bob\",\n"
            + "       \"operations\": [\"read\", \"read_acl\", \"write\"]\n"
            + "     }]"
            + "}")
        .accept(APPLICATION_JSON);

    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";

    this.mockMvc.perform(edit)
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  @Test
  public void DELETE_whenTheUserHasPermissionToDeleteTheCredential_succeeds() throws Exception {
    final MockHttpServletRequestBuilder deleteRequest = delete(
        "/api/v1/data?name=" + CREDENTIAL_NAME)
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN);
    mockMvc.perform(deleteRequest)
        .andExpect(status().isNoContent());

    final MockHttpServletRequestBuilder getRequest = get("/api/v1/data?name=" + CREDENTIAL_NAME)
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN);
    mockMvc.perform(getRequest)
        .andExpect(status().isNotFound());
  }

  @Test
  public void DELETE_whenTheUserLacksPermissionToDeleteTheCredential_returns404() throws Exception {
    final MockHttpServletRequestBuilder deleteRequest = delete(
        "/api/v1/data?name=" + CREDENTIAL_NAME)
        .header("Authorization", "Bearer " + USER_A_TOKEN);
    mockMvc.perform(deleteRequest)
        .andExpect(status().isNotFound());

    final MockHttpServletRequestBuilder getRequest = get("/api/v1/data?name=" + CREDENTIAL_NAME)
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN);
    mockMvc.perform(getRequest)
        .andExpect(status().isOk());
  }

  private void seedCredentials() throws Exception {
    final MockHttpServletRequestBuilder post = post("/api/v1/data")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        //language=JSON
        .content("{\n"
            + "  \"name\": \"" + CREDENTIAL_NAME + "\",\n"
            + "  \"type\": \"password\",\n"
            + "  \"overwrite\": true,\n"
            + "  \"additional_permissions\": [\n"
            + "    {\n"
            + "      \"actor\": \"" + USER_A_ACTOR_ID + "\",\n"
            + "      \"operations\": [\n"
            + "        \"read\"\n"
            + "      ]\n"
            + "    }\n"
            + "  ]\n"
            + "}");

        mockMvc.perform(post)
        .andExpect(status().isOk())
        .andReturn();

    final MockHttpServletRequestBuilder postAddPermissions = post("/api/v1/data")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        //language=JSON
        .content("{\n"
            + "  \"name\": \"" + CREDENTIAL_NAME + "\",\n"
            + "  \"type\": \"password\",\n"
            + "  \"overwrite\": true,\n"
            + "  \"additional_permissions\": [\n"
            + "    {\n"
            + "      \"actor\": \"" + USER_A_ACTOR_ID + "\",\n"
            + "      \"operations\": [\"read\", \"read_acl\"]\n"
            + "    }\n"
            + "  ]\n"
            + "}");



    final MvcResult result = mockMvc.perform(postAddPermissions)
        .andExpect(status().isOk())
        .andReturn();

    final MockHttpServletRequestBuilder otherPost = post("/api/v1/data")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .accept(APPLICATION_JSON)
        .contentType(APPLICATION_JSON)
        .content("{"
            + "  \"name\": \"" + SECOND_CREDENTIAL_NAME + "\",\n"
            + "  \"type\": \"password\","
            + "  \"additional_permissions\": [\n"
            + "     { \n"
            + "       \"actor\": \"" + USER_A_ACTOR_ID + "\",\n"
            + "       \"operations\": [\"read\", \"read_acl\", \"write\"]\n"
            + "     }]"
            + "}");

    mockMvc.perform(otherPost)
        .andExpect(status().isOk())
        .andReturn();

    result.getResponse().getContentAsString();
    final DocumentContext context = JsonPath.parse(result.getResponse().getContentAsString());
    uuid = context.read("$.id");
  }

  @Test
  public void interpolate_whenTheUserHasAccessToAllReferencedCredentials_returnsInterpolatedBody() throws Exception {
    makeJsonCredential(ALL_PERMISSIONS_TOKEN, "secret1");
    makeJsonCredential(ALL_PERMISSIONS_TOKEN, "secret2");

    MockHttpServletRequestBuilder request = post("/api/v1/interpolate")
        .header("Authorization", "Bearer " + ALL_PERMISSIONS_TOKEN)
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            "{" +
                "    \"pp-config-server\": [" +
                "      {" +
                "        \"credentials\": {" +
                "          \"credhub-ref\": \"/secret1\"" +
                "        }," +
                "        \"label\": \"pp-config-server\"" +
                "      }" +
                "    ]," +
                "    \"pp-something-else\": [" +
                "      {" +
                "        \"credentials\": {" +
                "          \"credhub-ref\": \"/secret2\"" +
                "        }," +
                "        \"something\": [\"pp-config-server\"]" +
                "      }" +
                "    ]" +
                "  }"
        );

    this.mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.pp-config-server[0].credentials.secret1")
            .value(equalTo("secret1-value")))
        .andExpect(jsonPath("$.pp-something-else[0].credentials.secret2")
            .value(equalTo("secret2-value")));
  }

  @Test
  public void interpolate_whenTheUserDoesNotHaveAccessToAllReferencedCredentials_returnsAnError() throws Exception {
    makeJsonCredential(ALL_PERMISSIONS_TOKEN, "secret1");
    makeJsonCredential(USER_A_TOKEN, USER_A_PATH + "secret2");

    MockHttpServletRequestBuilder request = post("/api/v1/interpolate")
        .header("Authorization", "Bearer " + USER_A_TOKEN)
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            "{" +
                "    \"pp-config-server\": [" +
                "      {" +
                "        \"credentials\": {" +
                "          \"credhub-ref\": \"/secret1\"" +
                "        }," +
                "        \"label\": \"pp-config-server\"" +
                "      }" +
                "    ]," +
                "    \"pp-something-else\": [" +
                "      {" +
                "        \"credentials\": {" +
                "          \"credhub-ref\": \"" + USER_A_PATH + "secret2\"" +
                "        }," +
                "        \"something\": [\"pp-config-server\"]" +
                "      }" +
                "    ]" +
                "  }"
        );

    String expectedError = "The request could not be completed because the credential does not exist or you do not have sufficient authorization.";

    this.mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error", equalTo(expectedError)));
  }

  private void makeJsonCredential(String userToken, String credentialName) throws Exception {
    MockHttpServletRequestBuilder createRequest1 = put("/api/v1/data")
        .header("Authorization", "Bearer " + userToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            "{" +
                "\"name\":\"" + credentialName + "\"," +
                "\"type\":\"json\"," +
                "\"value\":{" +
                "\"" + credentialName + "\":\"" + credentialName + "-value\"" +
                "}" +
                "}"
        );
    this.mockMvc.perform(createRequest1)
        .andExpect(status().isOk());
  }
}
