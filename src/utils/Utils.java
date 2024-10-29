package utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {


    public static int getLineIndex(List<String> contents, String regexPattern) {
        // Trouve l'index de la première ligne contenant le motif regex donné
        Pattern regex = Pattern.compile(regexPattern);

        for (int index = 0; index < contents.size(); index++) {
            String contentLine = contents.get(index);
            Matcher matcher = regex.matcher(contentLine);
            if (matcher.find()) {
                return index + 1;
            }
        }
        return -1;
    }

    public static int getLineIndexFromStart(List<String> contents, String regexPattern, int indexStart) {
        // Trouve l'index de la première ligne contenant le motif regex donné, à partir d'un index de départ
        Pattern regex = Pattern.compile(regexPattern);

        for (int index = indexStart; index < contents.size(); index++) {
            String contentLine = contents.get(index);
            Matcher matcher = regex.matcher(contentLine);
            if (matcher.find()) {
                return index + 1;
            }
        }
        return -1;
    }

    public static String getTri() {
        return "src";
    }

    public static String getFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    public static String getPageFromView(String path) {
        String fileName = getFileName(path);
        if (fileName.length() < 3) {
            throw new IllegalArgumentException("Le nom du fichier est invalide.");
        }

        // Extraire les trois premiers caractères
        String tri = fileName.substring(0, 3);
        String[] list = path.split("\\\\" + tri + "\\\\");

        if (list.length < 2) {
            throw new IllegalArgumentException("Le chemin ne contient pas suffisamment de parties.");
        }

        // Modifier le chemin pour remplacer "components" par "pages" et "View.jsx" par "Page.jsx"
        list[1] = list[1].replace("components", "pages").replace("View.jsx", "Page.jsx");

        String resultPath = String.join("/" + tri + "/", list);
        System.out.println(resultPath);
        return resultPath;
    }


    public static void main(String[] args) {
        String filePath = "C:\\Users\\lefeb\\IdeaProjects\\Clean Code Java\\src\\utils\\test.txt";
        List<String> contents = EditFileUtils.readFile(filePath);
        int indexLigne = getLineIndex(contents, "ligne 2");
        System.out.println(indexLigne);
    }
}

