package com.hylicmerit.cypher;

import com.hylicmerit.util.Constants;

public final class Cypher {
  private Cypher() {}

  public static String processChunk(String operation, String key, String input) {
    StringBuilder processedInput = new StringBuilder();
    // infinite loop of key based on pointer manipulation
    int keyIndex = 0;
    for (String curChar : input.split("")) {
      if (existsInAlphabet(curChar)) {
        if (keyIndex > key.length() - 1) {
          keyIndex = 0;
        }

        // generate alphabet based on current character in key
        String keyChar = String.valueOf(key.charAt(keyIndex));
        String alphabet = generateAlphabet(keyChar);

        if (operation.equals(Constants.ENCRYPT_OP)) {
          processedInput.append(encryptChar(curChar, alphabet));
        } else if (operation.equals(Constants.DECRYPT_OP)) {
          processedInput.append(decryptChar(curChar, alphabet));
        }

        keyIndex++;
      } else {
        processedInput.append(curChar);
      }
    }
    return processedInput.toString();
  }

  /*
  encrypting uses the original alphabet as the source for the char index
  the return value is the character found in the generated alphabet at the char index
  * */
  private static String encryptChar(String curChar, String alphabet) {
    int curCharIndex = Constants.ALPHABET.indexOf(curChar);
    return String.valueOf(alphabet.charAt(curCharIndex));
  }

  /*
  decrypting uses the generated alphabet as the source for the char index
  the return value is the character found in the generated alphabet at the char index
  * */
  private static String decryptChar(String curChar, String alphabet) {
    int curCharIndex = alphabet.indexOf(curChar);
    return String.valueOf(Constants.ALPHABET.charAt(curCharIndex));
  }

  private static String generateAlphabet(String keyChar) {
    int startIndex = Constants.ALPHABET.indexOf(keyChar);

    // starting from the key char to the end of the alphabet
    String firstChunk = extractAlphabetChunk(startIndex, Constants.ALPHABET.length());

    // from the first character up until one before the key char
    int remainder = Constants.ALPHABET.length() - firstChunk.length();
    String secondChunk = extractAlphabetChunk(0, remainder);

    return firstChunk + secondChunk;
  }

  private static String extractAlphabetChunk(int start, int end) {
    return Constants.ALPHABET.substring(start, end);
  }

  private static boolean existsInAlphabet(String curChar) {
    return Constants.ALPHABET.contains(curChar);
  }
}
