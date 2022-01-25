package com.hylicmerit;

import com.hylicmerit.cypher.Cypher;
import com.hylicmerit.util.Constants;
import com.hylicmerit.util.DirectoryHelper;

import java.io.IOException;

public class Driver {
  public static void main(String[] args) throws IOException {
    run(args);
  }

  public static String run(String[] args) throws IOException {
    String result = "";
    if (args.length != 3) {
      System.out.println("Please provide the required arguments: 'operation' 'key' 'target'");
    } else {
      String operation = args[0];
      String key = args[1];
      String target = args[2];

      if (!Constants.VALID_OPERATIONS.contains(operation)) {
        System.out.println(
            "Please provide one of the following operations: " + Constants.VALID_OPERATIONS);
      } else {
        switch (operation) {
          case Constants.ENCRYPT_OP:
          case Constants.DECRYPT_OP:
            result = Cypher.processChunk(operation, key, target);
            break;
          case Constants.ENCRYPT_DIR_OP:
          case Constants.DECRYPT_DIR_OP:
            DirectoryHelper.processDirectoryBFS(operation, key, target, null);
            break;
          default:
            break;
        }
      }
    }
    return result;
  }
}
