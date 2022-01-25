package com.hylicmerit;

import com.hylicmerit.cypher.Cypher;
import com.hylicmerit.util.Constants;
import com.hylicmerit.util.DirectoryHelper;

import java.io.IOException;

public class Driver {
  public static void main(String[] args) throws IOException {
    System.out.println("Driver running...");

    System.out.println(Cypher.processChunk(Constants.ENCRYPT_OP, "encrypt", "top secret"));
    System.out.println(Cypher.processChunk(Constants.DECRYPT_OP, "encrypt", "?3\" B4\"C[8"));

    System.out.println(
        Cypher.processChunk(Constants.ENCRYPT_OP, "encrypt", "lorem ipsum lorem ipsum?! \toh???"));
    System.out.println(
        Cypher.processChunk(Constants.DECRYPT_OP, "encrypt", "'3.<C ?A>9; 4E8?: <\"BK3sA F:0xos"));

    DirectoryHelper.processDirectoryBFS(
        Constants.ENCRYPT_DIR_OP,
        "encrypt",
        "C:/Users/mquintero/cyprography/src/main/resources/test",
        null);

    DirectoryHelper.processDirectoryBFS(
            Constants.DECRYPT_DIR_OP,
            "encrypt",
            "C:/Users/mquintero/cyprography/src/main/resources/test",
            null);

    DirectoryHelper.processDirectoryDFS(
            Constants.ENCRYPT_DIR_OP,
            "encrypt",
            "C:/Users/mquintero/cyprography/src/main/resources/test",
            null);

    DirectoryHelper.processDirectoryDFS(
            Constants.DECRYPT_DIR_OP,
            "encrypt",
            "C:/Users/mquintero/cyprography/src/main/resources/test",
            null);
  }
}
