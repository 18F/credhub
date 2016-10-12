package io.pivotal.security.entity;


import io.pivotal.security.view.SecretKind;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedRsaSshSecret extends NamedSecret {
  @Column(length = 7000)
  private String publicKey;

  public NamedRsaSshSecret(String name) {
    super(name);
  }

  public String getPublicKey() {
    return publicKey;
  }

  public <T extends NamedRsaSshSecret> T setPublicKey(String publicKey) {
    this.publicKey = publicKey;
    return (T) this;
  }

  public String getPrivateKey() {
    return new SecretEncryptionHelper().retrieveClearTextValue(this);
  }

  public <T extends NamedRsaSshSecret> T setPrivateKey(String privateKey) {
    new SecretEncryptionHelper().refreshEncryptedValue(this, privateKey);
    return (T) this;
  }

  public abstract SecretKind getKind();
}