package utils;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class EditFileUtils {

    public static List<String> readFile(String filePath) {
        // Récupère le contenu d'un fichier
        List<String> content = new ArrayList<>();
        try {
            content = Files.readAllLines(Paths.get(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : Le fichier '" + filePath + "' n'existe pas.");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la lecture du fichier : " + e.getMessage());
        }
        return content;
    }

    public static void addLine(String filePath, String line, int index) {
        // Ajoute une ligne à un index spécifique dans le fichier
        try {
            List<String> contents = readFile(filePath);
            if (index < 0 || index > contents.size()) {
                System.out.println("Erreur : L'index " + index + " est hors limites.");
                return;
            }
            contents.add(index, line);

            // Réécrit le fichier avec le nouveau contenu
            Files.write(Paths.get(filePath), contents);
            System.out.println("Ligne ajoutée à l'index " + index + ".");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'ajout de la ligne : " + e.getMessage());
        }
    }

    public static void setContents(String filePath, List<String> contents) {
        // Remplace le contenu du fichier avec le nouveau contenu
        try {
            Files.write(Paths.get(filePath), contents);
            System.out.println("Fichier mis à jour.");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la mise à jour du fichier : " + e.getMessage());
        }
    }

    public static void removeLine(String filePath, int index) {
        // Supprime une ligne à un index spécifique dans le fichier
        try {
            List<String> content = readFile(filePath);
            if (index < 0 || index >= content.size()) {
                System.out.println("Erreur : L'index " + index + " est hors limites.");
                return;
            }
            String removedLine = content.remove(index);

            // Réécrit le fichier avec le nouveau contenu
            Files.write(Paths.get(filePath), content);
            System.out.println("Ligne supprimée : '" + removedLine.trim() + "' à l'index " + index + ".");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la suppression de la ligne : " + e.getMessage());
        }
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
        String filePath = "C:\\Users\\lefeb\\IdeaProjects\\Clean Code Java\\src\\utils\\test.txt";

        // Lecture du contenu du fichier
        List<String> content = readFile(filePath);
        System.out.println("Contenu du fichier : " + content);

        // Ajout d'une ligne
        addLine(filePath, "Ceci est une nouvelle ligne.", 2);

        // Suppression d'une ligne
        removeLine(filePath, 5);

        setContents(filePath, content);
    }
}
