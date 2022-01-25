package com.hylicmerit.util;

import com.hylicmerit.cypher.Cypher;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Stream;

public class DirectoryHelper {
  private DirectoryHelper() {}

  public static void processDirectoryDFS(
      String operation, String key, String rootPath, FileFilter filter) throws IOException {
    File root = Paths.get(rootPath).toFile();
    String rootDirPath = evaluateRootDir(operation, root);
    File inputDir = evaluateInputDir(operation, root);
    File outputDir = new File(rootDirPath);

    cloneDirectory(inputDir, outputDir);

    processDirectoryDFSRecursive(operation, key, inputDir.getAbsolutePath(), outputDir, filter);
  }

  private static void processDirectoryDFSRecursive(
      String operation, String key, String targetPath, File outputDir, FileFilter filter)
      throws IOException {
    File target = new File(targetPath);
    File[] filesAndDirectoriesInDir = target.listFiles(filter);

    if (filesAndDirectoriesInDir != null) {
      for (File file : filesAndDirectoriesInDir) {
        if (file.isDirectory()) {
          processDirectoryDFSRecursive(operation, key, file.getAbsolutePath(), outputDir, filter);
        } else {
          // process file
          processFile(operation, key, file, outputDir);
        }
      }
    }
  }

  public static void processDirectoryBFS(
      String operation, String key, String rootPath, FileFilter filter) throws IOException {
    File root = Paths.get(rootPath).toFile();
    String rootDirPath = evaluateRootDir(operation, root);
    File inputDir = evaluateInputDir(operation, root);
    File outputDir = new File(rootDirPath);

    //clone original directory
    cloneDirectory(inputDir, outputDir);

    Queue<File> queue = new ArrayDeque<>();
    queue.add(inputDir);

    while (!queue.isEmpty()) {
      File current = queue.poll();

      File[] filesAndDirectoriesInDir = current.listFiles(filter);

      if (filesAndDirectoriesInDir != null) {
        for (File file : filesAndDirectoriesInDir) {
          if (file.isDirectory()) {
            queue.add(file);
          } else {
            // process file
            processFile(operation, key, file, outputDir);
          }
        }
      }
    }
  }

  private static synchronized void processFile(
      String operation, String key, File inputFile, File outputDir) throws IOException {
    Path outputFilePath = evaluateOutputFilePath(inputFile, outputDir);
    Files.deleteIfExists(outputFilePath);
    // get all text in file including new line, spaces and tabs
    try (FileWriter writer = new FileWriter(outputFilePath.toString())) {
      // read all bytes from file to avoid issues with \r,\n,\r\n
      String fileContent = new String(Files.readAllBytes(inputFile.toPath()));

      StringBuilder processedContent = new StringBuilder();

      if (operation.equals(Constants.ENCRYPT_DIR_OP)) {
        String encryptedContent = Cypher.processChunk(Constants.ENCRYPT_OP, key, fileContent);
        processedContent.append(encryptedContent);
      } else if (operation.equals(Constants.DECRYPT_DIR_OP)) {
        String decryptedContent = Cypher.processChunk(Constants.DECRYPT_OP, key, fileContent);
        processedContent.append(decryptedContent);
      }

      // write to new file
      writer.write(processedContent.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Path evaluateOutputFilePath(File inputFile, File outputDir) {
    String outputDirPath = outputDir.getAbsolutePath();
    String strippedOutputPath = stripOutputSuffixes(outputDirPath);

    String filePath = inputFile.getAbsolutePath();
    StringBuilder cleanFilePath = new StringBuilder();
    cleanFilePath.append(stripOutputSuffixes(filePath));
    cleanFilePath.replace(
        cleanFilePath.indexOf(strippedOutputPath), strippedOutputPath.length(), "");

    return Path.of(outputDirPath, cleanFilePath.toString());
  }

  private static String evaluateRootDir(String operation, File root) {
    StringBuilder outputDirPath = new StringBuilder();
    outputDirPath.append(root.getAbsolutePath());

    if (operation.equals(Constants.ENCRYPT_DIR_OP)) {
      outputDirPath.append(Constants.ENCRYPTED_DIR_SUFFIX);
    } else if (operation.equals(Constants.DECRYPT_DIR_OP)) {
      outputDirPath.append(Constants.DECRYPTED_DIR_SUFFIX);
    }

    return outputDirPath.toString();
  }

  private static File evaluateInputDir(String operation, File root) {
    StringBuilder inputDirPath = new StringBuilder();
    inputDirPath.append(root.getAbsolutePath());

    if (operation.equals(Constants.DECRYPT_DIR_OP)) {
      inputDirPath.append(Constants.ENCRYPTED_DIR_SUFFIX);
    }

    return new File(inputDirPath.toString());
  }

  private static String stripOutputSuffixes(String path) {
    StringBuilder cleanPath = new StringBuilder();
    cleanPath.append(path);
    int startIndex;
    int endIndex;
    if (cleanPath.indexOf(Constants.ENCRYPTED_DIR_SUFFIX) != -1) {
      startIndex = cleanPath.indexOf(Constants.ENCRYPTED_DIR_SUFFIX);
      endIndex = startIndex + Constants.ENCRYPTED_DIR_SUFFIX.length();
      cleanPath.replace(startIndex, endIndex, "");
    }
    if (cleanPath.indexOf(Constants.DECRYPTED_DIR_SUFFIX) != -1) {
      startIndex = cleanPath.indexOf(Constants.DECRYPTED_DIR_SUFFIX);
      endIndex = startIndex + Constants.DECRYPTED_DIR_SUFFIX.length();
      cleanPath.replace(startIndex, endIndex, "");
    }
    return cleanPath.toString();
  }

  private static void cloneDirectory(File sourceDir, File destinationDir) throws IOException {
    // check if destination directory is clean
    boolean destinationDirIsClean = true;
    // delete destination dir and contained files if already exists
    if (destinationDir.exists()) {
      destinationDirIsClean = deleteDirectory(destinationDir);
    }
    if (destinationDirIsClean) {
      try (Stream<Path> fileStream = Files.walk(sourceDir.toPath())) {
        fileStream.forEach(
            source -> {
              Path destination =
                  Paths.get(
                      destinationDir.getAbsolutePath(),
                      source.toString().substring(sourceDir.getAbsolutePath().length()));
              try {
                Files.copy(source, destination);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
      }
    }
  }

  private static boolean deleteDirectory(File directoryToDelete) throws IOException {
    File[] allContents = directoryToDelete.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }
    return Files.deleteIfExists(directoryToDelete.toPath());
  }
}
