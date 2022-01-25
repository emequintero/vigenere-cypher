package com.hylicmerit.util;

public final class Constants {
  private Constants() {}

  public static final String ALPHABET =
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\t\n\r~!@#$%^&*()_+-=[]\\{}|;':\",./<>?";
  public static final String ENCRYPT_OP = "encrypt";
  public static final String DECRYPT_OP = "decrypt";
  public static final String ENCRYPT_DIR_OP = "encryptDir";
  public static final String DECRYPT_DIR_OP = "decryptDir";
  public static final String ENCRYPTED_DIR_SUFFIX = ".encrypted";
  public static final String DECRYPTED_DIR_SUFFIX = ".decrypted";
}
