package com.exemple.filemanagement;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.NoSuchFileException;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Main {

    public static void main(String[] args) {

    System.out.println("TP 12 : Gestion des fichiers en Java");
    System.out.println("Environnement prêt pour les tests.");

    // 1. Lecture avec BufferedReader
    System.out.println("\n1. Lecture avec BufferedReader");
    readFileWithBufferedReader("data/employees.txt");

    // 2. Ecriture avec BufferedWriter
    System.out.println("\n2. Ecriture avec BufferedWriter");
    writeFileWithBufferedWriter(
        "data/employees.txt",
        "salma,14,auditeur"
    );

    // 3. Ecriture avec PrintWriter
    System.out.println("\n3. Ecriture avec PrintWriter");
    writeWithPrintWriter("data/employees.txt");

    // 4. Lecture avec Files API
    System.out.println("\n4. Lecture avec Files API");
    readWithFilesApi("data/employees.txt");

    // 5. Ecriture avec Files API
    System.out.println("\n5. Ecriture avec Files API");
    writeWithFilesApi(
        "data/employees.txt",
        "monir,35,directeur"
    );

    // 6. Copie du fichier
    System.out.println("\n6. Copie du fichier");
    copyFile(
        "data/employees.txt",
        "data/employees_copy.txt"
    );

    // 7. Déplacement du fichier
    System.out.println("\n7. Déplacement du fichier");
    moveFile(
        "data/employees_copy.txt",
        "data/employees_move.txt"
    );

    // 8. Lecture binaire
    System.out.println("\n8. Lecture binaire");
    readBinaryFile("data/employees.txt");

    // 9. Ecriture binaire
    System.out.println("\n9. Ecriture binaire");
    writeBinaryFile(
        "data/test.bin",
        new byte[]{10, 20, 30, 40, 50}
    );

    // 10. Métadonnées
    System.out.println("\n10. Métadonnées");
    getFileMetadata("data/employees.txt");

    // 11. Suppression
    System.out.println("\n11. Suppression");
    deleteFile("data/employees_move.txt");

    deleteFile("data/test.bin");

    System.out.println("\n=== FIN DES TESTS ===");
}

       

    // Lecture avec BufferedReader
    public static void readFileWithBufferedReader(String filePath) {

        try (BufferedReader br = new BufferedReader(
                new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println(
                "Erreur lors de la lecture du fichier : "
                + e.getMessage()
            );
        }
    }

    // Écriture avec BufferedWriter
    public static void writeFileWithBufferedWriter(
            String filePath, String content) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(filePath))) {

            bw.write(content);
            bw.newLine();
            bw.write("Ligne ajoutée avec succès.");

        } catch (IOException e) {
            System.err.println(
                "Erreur lors de l'écriture du fichier : "
                + e.getMessage()
            );
        }
    }

    // Écriture avec PrintWriter
    public static void writeWithPrintWriter(String filePath) {

        try (PrintWriter pw = new PrintWriter(
                new FileOutputStream(filePath))) {

            pw.println("=== Rapport des employés ===");

            pw.printf(
                "Nom: %s, Âge: %d, Poste: %s%n",
                "Alice", 25, "Engineer"
            );

            pw.printf(
                "Nom: %s, Âge: %d, Poste: %s%n",
                "Bob", 30, "Manager"
            );

            pw.println("Fin du rapport.");

        } catch (IOException e) {
            System.err.println(
                "Erreur lors de l'écriture : " + e.getMessage()
            );
        }
    }

    // Lecture avec Files API
    public static void readWithFilesApi(String filePath) {

        try {

            Path path = Paths.get(filePath);

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println(
                "Erreur lors de la lecture : " + e.getMessage()
            );
        }
    }

    // Écriture avec Files API
    public static void writeWithFilesApi(
            String filePath, String content) {

        try {

            Path path = Paths.get(filePath);

            Files.write(
                path,
                content.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );

            System.out.println(
                "Fichier écrit avec succès : " + filePath
            );

        } catch (IOException e) {
            System.err.println(
                "Erreur lors de l'écriture : " + e.getMessage()
            );
        }
    }

    // Copie d'un fichier
    public static void copyFile(
            String source, String destination) {

        try {

            Path sourcePath = Paths.get(source);
            Path destPath = Paths.get(destination);

            Files.copy(
                sourcePath,
                destPath,
                StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                "Fichier copié de " + source
                + " vers " + destination
            );

        } catch (IOException e) {
            System.err.println(
                "Erreur lors de la copie : " + e.getMessage()
            );
        }
    }

    // Déplacement d'un fichier
    public static void moveFile(
            String source, String destination) {

        try {

            Path sourcePath = Paths.get(source);
            Path destPath = Paths.get(destination);

            Files.move(
                sourcePath,
                destPath,
                StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                "Fichier déplacé de " + source
                + " vers " + destination
            );

        } catch (IOException e) {
            System.err.println(
                "Erreur lors du déplacement : " + e.getMessage()
            );
        }
    }

    // Suppression d'un fichier
    public static void deleteFile(String filePath) {

        try {

            Path path = Paths.get(filePath);

            if (Files.exists(path)) {

                Files.delete(path);

                System.out.println(
                    "Fichier supprimé : " + filePath
                );

            } else {

                System.out.println(
                    "Le fichier n'existe pas : " + filePath
                );
            }

        } catch (NoSuchFileException e) {

            System.err.println(
                "Le fichier a été supprimé entre-temps : "
                + e.getMessage()
            );

        } catch (IOException e) {

            System.err.println(
                "Erreur lors de la suppression : "
                + e.getMessage()
            );
        }
    }

    // Lecture d'un fichier binaire
    public static void readBinaryFile(String filePath) {

        try (BufferedInputStream bis =
                new BufferedInputStream(
                    new FileInputStream(filePath))) {

            byte[] buffer = new byte[1024];

            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = bis.read(buffer)) != -1) {
                totalBytes += bytesRead;
            }

            System.out.println(
                "Fichier binaire lu : " + filePath
            );

            System.out.println(
                "Taille totale : " + totalBytes + " octets"
            );

        } catch (IOException e) {

            System.err.println(
                "Erreur lors de la lecture binaire : "
                + e.getMessage()
            );
        }
    }

    // Écriture d'un fichier binaire
    public static void writeBinaryFile(
            String filePath, byte[] data) {

        try (BufferedOutputStream bos =
                new BufferedOutputStream(
                    new FileOutputStream(filePath))) {

            bos.write(data);
            bos.flush();

            System.out.println(
                "Fichier binaire écrit : " + filePath
            );

            System.out.println(
                "Taille écrite : " + data.length + " octets"
            );

        } catch (IOException e) {

            System.err.println(
                "Erreur lors de l'écriture binaire : "
                + e.getMessage()
            );
        }
    }

    // Récupération des métadonnées
    public static void getFileMetadata(String filePath) {

        try {

            Path path = Paths.get(filePath);

            BasicFileAttributes attrs =
                Files.readAttributes(
                    path,
                    BasicFileAttributes.class
                );

            System.out.println(
                "=== Métadonnées du fichier : "
                + filePath + " ==="
            );

            System.out.println(
                "Taille : " + attrs.size() + " octets"
            );

            System.out.println(
                "Dernière modification : "
                + attrs.lastModifiedTime().toInstant()
            );

            System.out.println(
                "Est un répertoire : " + attrs.isDirectory()
            );

            System.out.println(
                "Est un fichier : " + attrs.isRegularFile()
            );

        } catch (IOException e) {

            System.err.println(
                "Erreur lors de la récupération des métadonnées : "
                + e.getMessage()
            );
        }
    }
}

