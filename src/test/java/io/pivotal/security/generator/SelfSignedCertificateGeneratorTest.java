package io.pivotal.security.generator;

import io.pivotal.security.CredentialManagerApp;
import io.pivotal.security.controller.v1.CertificateSecretParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.exparity.hamcrest.BeanMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.x500.X500Principal;
import javax.validation.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CredentialManagerApp.class)
public class SelfSignedCertificateGeneratorTest {

  @Autowired
  private SignedCertificateGenerator signedCertificateGenerator;
  private static KeyPair keyPair;

  @BeforeClass
  public static void setupClass() throws NoSuchProviderException, NoSuchAlgorithmException {
    Security.addProvider(new BouncyCastleProvider());
    keyPair = generateKeyPair();
  }

  @Test
  public void getSucceeds() throws Exception {
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.setOrganizationUnit("My Unit");
    inputParameters.setLocality("My Locality");
    inputParameters.setCommonName("My Common Name");

    X500Principal expectedPrincipal = new X500Principal("L=My Locality,OU=My Unit,CN=My Common Name,C=USA,ST=NY,O=my-org");
    X509Certificate actualCert = signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);

    actualCert.checkValidity();
    assertThat(actualCert, notNullValue());
    assertThat(actualCert.getSubjectX500Principal(), equalTo(expectedPrincipal));
    assertThat(actualCert.getSigAlgName(), equalTo("SHA256WITHRSA"));

    checkDuration(actualCert, 365);
  }

  @Test
  public void canGenerateCertificateWithAlternateNames() throws Exception {
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.addAlternativeName("1.1.1.1");
//    inputParameters.addAlternateName("2.2.2.0/24");  // spec indicates that bitmask is legal
    inputParameters.addAlternativeName("example.com");
    inputParameters.addAlternativeName("foo.pivotal.io");
    inputParameters.addAlternativeName("*.pivotal.io");

    X509Certificate actualCert = signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);

    actualCert.checkValidity();
    Collection<List<?>> subjectAlternativeNames = actualCert.getSubjectAlternativeNames();
    ArrayList<String> alternateNames = subjectAlternativeNames.stream().map(generalName ->
        generalName.get(1).toString()).collect(Collectors.toCollection(ArrayList::new));

    assertThat(alternateNames, containsInAnyOrder(
        "1.1.1.1",
        "example.com",
        "foo.pivotal.io",
        "*.pivotal.io"
        // "2.2.2.0/24"
    ));
  }

  @Test
  public void canGenerateCertificateWithArbitraryDuration() throws Exception {
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.setDurationDays(555);
    X509Certificate actualCert = signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);
    checkDuration(actualCert, 555);
  }

  @Test
  public void zeroAlternateNamesYieldsEmptyArrayOfNames() throws Exception {
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();

    X509Certificate actualCert = signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);

    actualCert.checkValidity();
    assertThat(actualCert.getSubjectAlternativeNames(), nullValue());
  }

  @Test(expected = ValidationException.class)
  public void alternativeNamesInvalidatesSpecialCharsDns() throws Exception {
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.addAlternativeName("foo!@#$%^&*()_-+=.com");

    signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);
  }

  @Test(expected = ValidationException.class)
  public void alternativeNamesInvalidatesSpaceInDns() throws Exception {
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.addAlternativeName("foo pivotal.io");

    signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);
  }

  @Test(expected = ValidationException.class)
  public void alternativeNamesInvalidateBadIpAddresses() throws Exception {
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.addAlternativeName("1.2.3.999");

    signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);
  }

  @Test(expected = ValidationException.class)
  public void alternativeNamesInvalidateEmailAddresses() throws Exception {
    // email addresses are allowed in certificate spec, but we do not allow them per PM requirements
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.addAlternativeName("x@y.com");

    signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);
  }

  @Test(expected = ValidationException.class)
  public void alternativeNamesInvalidateUrls() throws Exception {
    // URLs are allowed in certificate spec, but we do not allow them per PM requirements
    CertificateSecretParameters inputParameters = getMinimumCertificateSecretParameters();
    inputParameters.addAlternativeName("https://foo.com");

    signedCertificateGenerator.getSelfSigned(keyPair, inputParameters);
  }

  private CertificateSecretParameters getMinimumCertificateSecretParameters() {
    CertificateSecretParameters inputParameters = new CertificateSecretParameters();
    inputParameters.setOrganization("my-org");
    inputParameters.setState("NY");
    inputParameters.setCountry("USA");
    return inputParameters;
  }

  private static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
    generator.initialize(1024); // for testing only; strength not important
    return generator.generateKeyPair();
  }

  private void checkDuration(X509Certificate actualCert, int durationDays) {
    long durationMillis = actualCert.getNotAfter().getTime() - actualCert.getNotBefore().getTime();
    assertThat(durationMillis, equalTo(Instant.EPOCH.plus(durationDays, ChronoUnit.DAYS).toEpochMilli()));
  }
}