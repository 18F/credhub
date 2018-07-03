package org.cloudfoundry.credhub.util;

public class AuthConstants {
  // Actor ID: uaa-user:df0c1a26-2875-4bf5-baf9-716c6bb5ea6d
  // Grant type: password
  // Client ID: credhub_cli
  // User ID: df0c1a26-2875-4bf5-baf9-716c6bb5ea6d
  //
  // JWT token signed by private key for public key in `application-unit-test.yml`
  // Valid for about 50 years!!!
  // Check and change at jwt.io
  public static final String UAA_OAUTH2_PASSWORD_GRANT_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI5YTk3YWVlNjVhYWY0Yzc0ODVhMTZmZWU1MWY5NTVmMSIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1Yl9jbGkiLCJjaWQiOiJjcmVkaHViX2NsaSIsImF6cCI6ImNyZWRodWJfY2xpIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ5MDkwNjA5MCwicmV2X3NpZyI6ImU0NDNkNzFlIiwiaWF0IjoxNDkwOTAzMzUzLCJleHAiOjMwNjc3MDYwOTAsImlzcyI6Imh0dHBzOi8vZXhhbXBsZS5jb206ODQ0My9vYXV0aC90b2tlbiIsInppZCI6InVhYSIsImF1ZCI6WyJjcmVkaHViX2NsaSIsImNyZWRodWIiXX0.b_gqc3Dzc5AuojrD0t1sNVy5fmepO0Xgsq3VdtZGLHrKJ6qbZWW1KtBBPFDeNGugMda5w2Q6faETfn7orJkKePFnx6eCfSbWuUKmB7Y9vH8hvjBZ3e9jA0S0TJ8qba8wsoYQbE6YrVJXnF_WJSu4GkHNQh0NqpBWo52pNHKa2xCbMIlp5Z5VPZpWURpAIrttnb7Qb4EEeiWFyWtlCfi5LkKlC9jc-hy2ngqia0GedTsxe43R3k2nNeLUdfoICT5uNCdVLSR8VS7pjMm1yz7-jZYCy7WT17X2mJeA1X__McRa9NIlD6gYlInN9Prncp2mOTU4myvb9tte6IpKyeKrmg";
  public static final String UAA_OAUTH2_PASSWORD_GRANT_ACTOR_ID = "uaa-user:df0c1a26-2875-4bf5-baf9-716c6bb5ea6d";
  public static final String UAA_USER_WITH_ALL_PERMISSIONS_ACTOR_ID = UAA_OAUTH2_PASSWORD_GRANT_ACTOR_ID;
  public static final String UAA_USER_WITH_ALL_PERMISSIONS_TOKEN = UAA_OAUTH2_PASSWORD_GRANT_TOKEN;

  // Actor ID: uaa-client:credhub_test
  // Grant type: client_credentials
  // Client ID: credhub_test
  public static final String UAA_OAUTH2_CLIENT_CREDENTIALS_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI0NWRjYTFiM2UzMGY0NDhjYjE5Y2U4YTVkYzRhMDdmYyIsInN1YiI6ImNyZWRodWJfdGVzdCIsImF1dGhvcml0aWVzIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwic2NvcGUiOlsiY3JlZGh1Yi53cml0ZSIsImNyZWRodWIucmVhZCJdLCJjbGllbnRfaWQiOiJjcmVkaHViX3Rlc3QiLCJjaWQiOiJjcmVkaHViX3Rlc3QiLCJhenAiOiJjcmVkaHViX3Rlc3QiLCJncmFudF90eXBlIjoiY2xpZW50X2NyZWRlbnRpYWxzIiwicmV2X3NpZyI6ImRlNTQxYmEwIiwiaWF0IjoxNDkxMjQ0NzgwLCJleHAiOjMwNjgwNDQ3ODAsImlzcyI6Imh0dHBzOi8vZXhhbXBsZS5jb206ODQ0My9vYXV0aC90b2tlbiIsInppZCI6InVhYSIsImF1ZCI6WyJjcmVkaHViX3Rlc3QiLCJjcmVkaHViIl19.Z5vIzkzJkvPgLCok6eHi-giRMUvJCVWaRz4W299zOValsstADZsw2aKn0ow_RqCOWGuXWhY2vEgZZlwYq7VfnbJhY1rB_arqu-wiRW22xBsdeKDTpDfE7LmGaSyDsFKArNCZ8Bw-OZt3iW-F9iCvAqLqOHOBTtkeyoJfNDu44D5yDqB8tQLBPmkCxQt1OOFlfImP6YkkpqjWj9KuvUhx6lruGccaXQfGunJQ0JeMiNagG4p5VKhI1MZcfsQFEq3Uw-MOfOmYA0b0QKKa9fUqJwGYJWgfAoihLgXfLbcflLFQBNmlqaMzOkeEVCRGFBSKg-P9RDmQiME7SYmw4VyVaw";
  public static final String UAA_OAUTH2_CLIENT_CREDENTIALS_ACTOR_ID ="uaa-client:credhub_test";
  public static final String UAA_USER_WITH_NO_PERMISSIONS_ACTOR_ID = UAA_OAUTH2_CLIENT_CREDENTIALS_ACTOR_ID;
  public static final String UAA_USER_WITH_NO_PERMISSIONS_TOKEN = UAA_OAUTH2_CLIENT_CREDENTIALS_TOKEN;

  // Actor ID: uaa-user:df0c1a26-2875-4bf5-baf9-716c6bb5ea6d
  // Grant type: password
  // Client ID: credhub_cli
  // User ID: df0c1a26-2875-4bf5-baf9-716c6bb5ea6d
  public static final String INVALID_SCOPE_KEY_JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiJlOWU1NzM5Y2QzODc0NDgzOGFjZjY4M2I3YWI0N2IwNCIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIuYmFkX3Njb3BlIl0sImNsaWVudF9pZCI6ImNyZWRodWJfY2xpIiwiY2lkIjoiY3JlZGh1Yl9jbGkiLCJhenAiOiJjcmVkaHViX2NsaSIsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsInVzZXJfaWQiOiJkZjBjMWEyNi0yODc1LTRiZjUtYmFmOS03MTZjNmJiNWVhNmQiLCJvcmlnaW4iOiJ1YWEiLCJ1c2VyX25hbWUiOiJjcmVkaHViX2NsaSIsImVtYWlsIjoiY3JlZGh1Yl9jbGkiLCJhdXRoX3RpbWUiOjE0OTA5MDMzNTMsInJldl9zaWciOiJlNDQzZDcxZSIsImlhdCI6MTQ5MDkwMzM1MywiZXhwIjozNDkwOTAzMzU0LCJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tOjg0NDMvb2F1dGgvdG9rZW4iLCJ6aWQiOiJ1YWEiLCJhdWQiOlsiY3JlZGh1Yl9jbGkiLCJjcmVkaHViIl19.Bo5ABjYR132PZAoPRQu4d8Oobx3FReRZHX42ZznyWMB5gZFCfrkrkzpxl5hFO8Qo71_80KlRjciTS_xnYxLIlZL0xkh0IhNfEsF1UlwuMt-9nyCD7BBJ3P8CJU1XS26NSTwkdxPTod4Bkq2zU6tTp5H5YBbIjuxKm7R6qGHIe8eufvXRW_kD7urKX-fhshtilMAWRON6EbRn4785dteNR4Hv7a6iUBwMA0RKm4S2_YYxm7wt5bUAUe5iMS8cQrqW1ydFb-RZHtLiy03ggtSK-1nfMQtbSN6RrN7eyiNdDz1XqteIfl-UtqHqYbFP2ZFq7M9cXs_lRKkR-csSCD40fA";

  // Actor ID: uaa-user:df0c1a26-2875-4bf5-baf9-716c6bb5ea6d
  // Grant type: password
  // Client ID: credhub_cli
  // User ID: df0c1a26-2875-4bf5-baf9-716c6bb5ea6d
  public static final String EXPIRED_KEY_JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiJlOWU1NzM5Y2QzODc0NDgzOGFjZjY4M2I3YWI0N2IwNCIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1Yl9jbGkiLCJjaWQiOiJjcmVkaHViX2NsaSIsImF6cCI6ImNyZWRodWJfY2xpIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ5MDkwMzM1MywicmV2X3NpZyI6ImU0NDNkNzFlIiwiaWF0IjoxMDkwOTAzMzUzLCJleHAiOjEyOTA5MDMzNTQsImlzcyI6Imh0dHBzOi8vZXhhbXBsZS5jb206ODQ0My9vYXV0aC90b2tlbiIsInppZCI6InVhYSIsImF1ZCI6WyJjcmVkaHViX2NsaSIsImNyZWRodWIiXX0.PWEChjBzs3WIM3hDG55Vgct8DE7iHJxI83EcGI9jazM3Cip2DHo4il1k1SeQG2aqofmFZ_SObWQW-vxPc0cSCnF8dcqeSjBsjrrBGB5T1hwE0wS_nmP4vxuz1wPTIQZKXbpVvF3m7K9m79ogL8eWQz8rDhoo7JYEhlDJ2VyZ0dsTDpwQY4EGdvh1GtMsrun-T5P98gav2XeAcOu1XQGQNBV2RhZ0T7olugyDxbaSLuG7AJPh2e6ygPWcldRL7hJhG3q4Uo9iNbyp3Nmn_CXWppyQHMFYl8wwlFVZf6hU0nosUJ8I4LjGbA3165PEmKI-ZMlcQ6LJWUloL7vJG1eUAA";

  // Actor ID: uaa-user:9302d419-79e5-474d-ae4f-252206144db6
  // Grant type: password
  // Client ID: credhub_cli
  // User ID: 9302d419-79e5-474d-ae4f-252206144db6
  public static final String INVALID_JSON_JWT = "eyJhbGciOiJSUzI1NiJ9.ewogICJqdGkiOiAiNGE3ZjY0MWVkOGExNDI1Njk2NWQxYjNmYmFlNjcxNGUiLAogICJzdWIiOiAiOTMwMmQ0MTktNzllNS00NzRkLWFlNGYtMjUyMjA2MTQ0ZGI2IiwKICAic2NvcGUiOiBbCiAgICAiY3JlZGh1Yi53cml0ZSIsCiAgICAiY3JlZGh1Yi5yZWFkIgogIF0sCiAgImNsaWVudF9pZCI6ICJjcmVkaHViX2NsaSIsCiAgImNpZCI6ICJjcmVkaHViX2NsaSIsCiAgImF6cCI6ICJjcmVkaHViX2NsaSIsCiAgInJldm9jYWJsZSI6IHRydWUsCiAgImdyYW50X3R5cGUiOiAicGFzc3dvcmQiLAogICJ1c2VyX2lkIjogIjkzMDJkNDE5LTc5ZTUtNDc0ZC1hZTRmLTI1MjIwNjE0NGRiNiIsCiAgIm9yaWdpbiI6ICJ1YWEiLAogICJ1c2VyX25hbWUiOiAiY3JlZGh1YiIsCiAgImVtYWlsIjogImNyZWRodWIiLAogICJhdXRoX3RpbWUiOiAxNDkwODE4MzgwLAogICJyZXZfc2lnIjogIjgzOWFhZGQ3IiwKICAiaWF0IjogMTQ5MDgxODM4MCwKICAiZXhwIjogMTQ5MDkwNDc4MCwKICAiaXNzIjogImh0dHBzOi8vZXhhbXBsZS5jb206ODQ0My9vYXV0aC90b2tlbiIsCiAgInppZCI6ICJ1YWEiLAogICJhdWQiOiBbCiAgICAiY3JlZGh1Yl9jbGkiLAogICAgImNyZWRodWIiCiAgXQo.jrsrDFfNKKtrtBt7AqUFm2orXlCVvil_kB4eEZ0z7FfZ29L8hLmiLGE7prVV_hvGgJnL0uco_bCCWNYZ1h69gARJj6NNYb0Uwo_gn3kXl5DmJ2Z071BbvbQx4fQhSKYvcZJZmqlviZV6LsU12n31Rtbuq77CzGyWh75H3I7iR98-5dc3hVLEcIS2pHrry6LSIX5Xc-DmHz8p-58h4hbWJf_mIgHnm-GUbH26gkt8dSw2OXfR-WLDxbldjKzKmmvCjg1H1GsouDNtxQUGtC5aVLdo9RnJa6e0xdzYr_g8J2jY94hbS66ml1m1jWx_urNoI2R1w_vrrpB16jzS30o8Pg";

  // Actor ID: uaa-user:1cc4972f-184c-4581-987b-85b7d97e909c
  // Grant type: password
  // Client ID: credhub
  // User ID: 1cc4972f-184c-4581-987b-85b7d97e909c
  public static final String INVALID_SIGNATURE_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJiOTc3NzIxNGI1ZDM0Zjc4YTJlMWMxZjZkYjJlYWE3YiIsInN1YiI6IjFjYzQ5NzJmLTE4NGMtNDU4MS05ODdiLTg1YjdkOTdlOTA5YyIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1YiIsImNpZCI6ImNyZWRodWIiLCJhenAiOiJjcmVkaHViIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6IjFjYzQ5NzJmLTE4NGMtNDU4MS05ODdiLTg1YjdkOTdlOTA5YyIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ2OTA1MTcwNCwicmV2X3NpZyI6ImU1NGFiMzlhIiwiaWF0IjoxNDY5MDUxNzA0LCJleHAiOjM0NjkwNTE4MjQsImlzcyI6Imh0dHBzOi8vZXhhbXBsZS5jb206ODQ0My9vYXV0aC90b2tlbiIsInppZCI6InVhYSIsImF1ZCI6WyJjcmVkaHViIl19.fCu86RFbzyx_GBf0Mx8SJ8HQN0JuFbnQqodKOUl0cF3omntMIcaoowB-BhwcAc0kd1HZrn6Ba3lTI2GPtemH6BdmGdK5Uh4u5kMku7J-bDOT4xtMwqKBmucY47sHc0hltUduLE7kfJLjTmg-Jzw6pAjeh-W4p9ul_tgW5XDJYn47H8ho1KvpiJWWGwFralgrrZDQPte6-J-QQWhgnBX3RWs3BxBqB-5pdB0jJ41ryQMTqmZTzrtZF3XbvBjt2gdmUsafzTYm7Wefv0xa92CJwrrS-urOR1G4bCLO8eAVFzl4vKGDDtImQ-4f3N7vsuH19qvMV4lWYGLzy36TADUp4Q";

  public static final String INVALID_ISSUER_JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI5YTk3YWVlNjVhYWY0Yzc0ODVhMTZmZWU1MWY5NTVmMSIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1Yl9jbGkiLCJjaWQiOiJjcmVkaHViX2NsaSIsImF6cCI6ImNyZWRodWJfY2xpIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ5MDkwNjA5MCwicmV2X3NpZyI6ImU0NDNkNzFlIiwiaWF0IjoxNDkwOTAzMzUzLCJleHAiOjMwNjc3MDYwOTAsImlzcyI6ImlzX2ludmFsaWQiLCJ6aWQiOiJ1YWEiLCJhdWQiOlsiY3JlZGh1Yl9jbGkiLCJjcmVkaHViIl19.llBhxtpdpRWon-iLMopXsgOHNSqslG3PPTWngfsbFGaNQzbzHqqMQAJ5coDz54gUshwT8Ccvv2Qfl44KcNCpmL606gf72640tlUr2bftSdUhjOE4fvb3_57ViVMXj7pfuwaoDj3lFWxuOqNw9UfwhTtf7xHCcta5TNozrJukehQ2qhLG0ZHzfj9JlVkcnN8HG1aQCuymViSaH8KbREzaAUGDtYuqJ8CR4YYj7WVT585M2f0rf4qQB7AUvhybRqcjvfRSpghcCRKOtsuMs7SYhMLJH1nWOMGPbk2E-2qO4i6GPG9ASmloogz2OGxsPzVpJTW44IuPiu5-zo-SQs4Tkg";

  public static final String NULL_ISSUER_JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI5YTk3YWVlNjVhYWY0Yzc0ODVhMTZmZWU1MWY5NTVmMSIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1Yl9jbGkiLCJjaWQiOiJjcmVkaHViX2NsaSIsImF6cCI6ImNyZWRodWJfY2xpIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ5MDkwNjA5MCwicmV2X3NpZyI6ImU0NDNkNzFlIiwiaWF0IjoxNDkwOTAzMzUzLCJleHAiOjMwNjc3MDYwOTAsInppZCI6InVhYSIsImF1ZCI6WyJjcmVkaHViX2NsaSIsImNyZWRodWIiXX0.OsgEbR1anAwcLAfbUuvR6E_HnQQZB08Al7rwXLzXrRtGdXCBf1BiCgGvITqfYlh2E6rIB71kDOUL0lPGes60Hn7vnZOuhzdV7wheBwJ_hjhYL7WKzdeFAkSTjAt8ETLW-9tM5YS8cnKeFSDN39dIzc9LtbnhOQCZByv9xIorhy_MfhH2EgVKFFKl9VOPn3r--XtN3g7e3t2R5bDyn7J-ROIrDbsPJxV0hR6UrLLyUDvc3DIxTT6KbMZrFyN292j-4MhDsbPt61_HrNzFJeZT0r8vA_UZH3DowClEW3wGto9ftesz6G07V0IEpTUKcrb-Tgfvvd1OZorTOpHNaAkMPQ";

  public static final String EMPTY_ISSUER_JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI5YTk3YWVlNjVhYWY0Yzc0ODVhMTZmZWU1MWY5NTVmMSIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1Yl9jbGkiLCJjaWQiOiJjcmVkaHViX2NsaSIsImF6cCI6ImNyZWRodWJfY2xpIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ5MDkwNjA5MCwicmV2X3NpZyI6ImU0NDNkNzFlIiwiaWF0IjoxNDkwOTAzMzUzLCJleHAiOjMwNjc3MDYwOTAsImlzcyI6IiIsInppZCI6InVhYSIsImF1ZCI6WyJjcmVkaHViX2NsaSIsImNyZWRodWIiXX0.TDs8kXA_QmGHaO7WMSqqjVtqZcjfbPI6ynjR2UsH2wX_c_JafYTTdRDR-foaJyf9OhjrC45wWAiucn5l1CxdSbAxNtbP4M4AgQSik9tOl0ivbW2R0T3omfZd4ZgLJZi1jSOFWyfQ8Pg1TLfI8nmtIUhMjzdGGR_hLHuFZSEeIt0eGjtD2Y5IeqKV5-YJfJh5KZn7u-3GM5iCV7npBHRLqu9Uex78dZsHptbPGcePD54-_X-iu5rUHS3OSRWXUV4aCMRI7trmKjedD-yUERcIJa7Afm2DtdfzzVsQIbVpW_rjqny69W1mSxmp3eXQzbX5MbL-R75dc5GS6OBz796oLA";

  public static final String VALID_ISSUER_JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI5YTk3YWVlNjVhYWY0Yzc0ODVhMTZmZWU1MWY5NTVmMSIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1Yl9jbGkiLCJjaWQiOiJjcmVkaHViX2NsaSIsImF6cCI6ImNyZWRodWJfY2xpIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ5MDkwNjA5MCwicmV2X3NpZyI6ImU0NDNkNzFlIiwiaWF0IjoxNDkwOTAzMzUzLCJleHAiOjMwNjc3MDYwOTAsImlzcyI6Imh0dHBzOi8vdmFsaWQtdWFhOjg0NDMvdWFhL29hdXRoL3Rva2VuIiwiemlkIjoidWFhIiwiYXVkIjpbImNyZWRodWJfY2xpIiwiY3JlZGh1YiJdfQ.YFmH-f6yJt-YyNMhQxSmiItdzOuzHwcW3WUYMaZ-XliW_vYhEg0eU7eiCCU_pYdlnZVpXZysFyuq2gZ9MZ_wVXcvuBkVrm4WfXIyRb0tzmXp7WqRPo70GyTYHv5vVC3kXKV7k4tekc2pys8-iGtN5C36SE6LmNDfucQYvwWqUOTbvaH181UKVPM83tVwcJhJeT6oMiQzewBN16OqfDcRFyfb0KTxqe4_JXZYK6uJ6t4yK7vAk-7CCiR1y0L4GfBobT8j3gM7BfHUNrRPRLvN9MbB1-H3qMQu3mL08jHXi3m7mvU48A8e5t_pVuYQr5kXr5mLNJMzj0iK0NiCfKhqCg";

  public static final String MALFORMED_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI5YTk3YWVlNjVhYWY0Yzc0ODVhMTZmZWU1MWY5NTVmMSIsInN1YiI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsInNjb3BlIjpbImNyZWRodWIud3JpdGUiLCJjcmVkaHViLnJlYWQiXSwiY2xpZW50X2lkIjoiY3JlZGh1Yl9jbGkiLCJjaWQiOiJjcmVkaHViX2NsaSIsImF6cCI6ImNyZWRodWJfY2xpIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImRmMGMxYTI2LTI4NzUtNGJmNS1iYWY5LTcxNmM2YmI1ZWE2ZCIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImNyZWRodWJfY2xpIiwiZW1haWwiOiJjcmVkaHViX2NsaSIsImF1dGhfdGltZSI6MTQ5MDkwNjA5MCwicmV2X3NpZyI6ImU0NDNkNzFlIiwiaWF0IjoxNDkwOTAzMzUzLCJleHAiOjMwNjc3MDYwOTAsImlzcyI6Imh0dHBzOi8vdmFsaWQtdWFhOjg0NDMvdWFhL29hdXRoL3Rva2VuIiwiemlkIjoidWFhIiwiYXVkIjpbImNyZWRodWJfY2xpIiwiY3JlZGh1YiJdfQ.YFmH-f6yJt-YyNMhQxSmiItdzOuzHwcW3WUYMaZ-XliW_vYhEg0eU7eiCCU_pYdlnZVpXZysFyuq2gZ9MZ_wVXcvuBkVrm4WfXIyRb0tzmXp7WqRPo70GyTYHv5vVC3kXKV7k4tekc2pys8-iGtN5C36SE6LmNDfucQYvwWqUOTbvaH181UKVPM83tVwcJhJeT6oMiQzewBN16OqfDcRFyfb0KTxqe4_JXZYK6uJ6t4yK7vAk-7CCiR1y0L4GfBobT8j3gM7BfHUNrRPRLvN9MbB1-H3qMQu3mL08jHXi3m7mvU48A8e5t_pVuYQr5kXr5";
}
