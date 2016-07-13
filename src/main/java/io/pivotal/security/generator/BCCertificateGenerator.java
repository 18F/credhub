package io.pivotal.security.generator;

import io.pivotal.security.controller.v1.CertificateSecretParameters;
import io.pivotal.security.entity.NamedCertificateAuthority;
import io.pivotal.security.repository.InMemoryAuthorityRepository;
import io.pivotal.security.util.CertificateFormatter;
import io.pivotal.security.view.CertificateSecret;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

import javax.security.auth.x500.X500Principal;
import javax.validation.ValidationException;

@Component
public class BCCertificateGenerator implements SecretGenerator<CertificateSecretParameters, CertificateSecret> {

  @Autowired
  KeyPairGenerator keyGenerator;

  @Autowired
  SignedCertificateGenerator signedCertificateGenerator;

  @Autowired
  InMemoryAuthorityRepository authorityRepository;

  @Override
  public CertificateSecret generateSecret(CertificateSecretParameters params) {
    keyGenerator.initialize(params.getKeyLength());
    KeyPair keyPair = keyGenerator.generateKeyPair();

    NamedCertificateAuthority ca = findCa(params.getCa());
    if (ca != null) {
      try {
        X500Principal issuerDn = getIssuer(ca);
        PrivateKey issuerKey = getPrivateKey(ca);

        X509Certificate cert = signedCertificateGenerator.getSignedByIssuer(issuerDn, issuerKey, keyPair, params);

        String certPem = CertificateFormatter.pemOf(cert);
        String privatePem = CertificateFormatter.pemOf(keyPair.getPrivate());
        return new CertificateSecret(ca.getCertificate(), certPem, privatePem);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new ValidationException("error.default_ca_required");
      // Dan says we are going to reinstate functionality for self-signed certificates soon.
      // See git history at SHA 5595fc9
    }
  }

  private NamedCertificateAuthority findCa(String caName) {
    boolean hasCaName = !StringUtils.isEmpty(caName);
    if (!hasCaName) {
      caName = "default";
    }

    return (NamedCertificateAuthority) authorityRepository.findOneByName(caName);
  }

  private PrivateKey getPrivateKey(NamedCertificateAuthority ca) throws IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
    PEMParser pemParser = new PEMParser(new StringReader(ca.getPrivateKey()));
    PEMKeyPair pemKeyPair = (PEMKeyPair) pemParser.readObject();
    PrivateKeyInfo privateKeyInfo = pemKeyPair.getPrivateKeyInfo();
    return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
  }

  private X500Principal getIssuer(NamedCertificateAuthority ca) throws IOException, CertificateException, NoSuchProviderException {
    X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509", "BC")
        .generateCertificate(new ByteArrayInputStream(ca.getCertificate().getBytes()));
    return certificate.getIssuerX500Principal();
  }
}
