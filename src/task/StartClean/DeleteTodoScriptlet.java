package task.StartClean;

import dico.Dico;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.List;

import static utils.EditFileUtils.*;

public class DeleteTodoScriptlet {
    public static List<String> deleteTodoScriptlet(List<String> contents) {
        List<Integer> listToDelete = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            Pattern regex = Pattern.compile("\\{/\\* TODO SCRIPTLET");
            Matcher matcher = regex.matcher(contents.get(i));
            if (matcher.find()
                    && Pattern.compile("\\{(?!.*?TODO SCRIPTLET.*?(&&|\\?)).*?(&&|\\?)").matcher(contents.get(i + 2)).find()
                    && Pattern.compile("<>").matcher(contents.get(i + 3)).find()
                    && Pattern.compile("\\s*FIN TODO SCRIPTLET \\*/\\}").matcher(contents.get(i + 5)).find()) {
                List<Integer> listToDeleteTemp = new ArrayList<>();
                for (int j = i + 1; j < contents.size(); j++) {
                    if (Pattern.compile("\\{/\\* TODO SCRIPTLET").matcher(contents.get(j)).find()) {
                        if (Pattern.compile("</>").matcher(contents.get(j + 2)).find()
                                && Pattern.compile("\\)\\}").matcher(contents.get(j + 3)).find()
                                && Pattern.compile("\\s*FIN TODO SCRIPTLET \\*/\\}").matcher(contents.get(j + 5)).find()) {
                            System.out.println("yes1");
                            listToDelete.addAll(listToDeleteTemp);
                            listToDeleteTemp.clear();
                            listToDelete.add(i);
                            listToDelete.add(i+1);
                            listToDelete.add(i + 4);
                            listToDelete.add(i + 5);
                            listToDelete.add(j);
                            listToDelete.add(j+1);
                            listToDelete.add(j + 4);
                            listToDelete.add(j + 5);
                            System.out.println(listToDelete);
                            break;
                        } else if (Pattern.compile("</>").matcher(contents.get(j + 2)).find()
                                && Pattern.compile("\\) : \\(").matcher(contents.get(j + 3)).find()
                                && Pattern.compile("<>").matcher(contents.get(j + 4)).find()
                                && Pattern.compile("\\s*FIN TODO SCRIPTLET \\*/\\}").matcher(contents.get(j + 6)).find()) {
                            System.out.println("yes1");
                            listToDeleteTemp.clear();
                            listToDeleteTemp.add(j);
                            listToDeleteTemp.add(j + 1);
                            listToDeleteTemp.add(j + 5);
                            listToDeleteTemp.add(j + 6);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        Collections.sort(listToDelete);
        for (int i = listToDelete.size() - 1; i >= 0; i--) {
            contents.remove(listToDelete.get(i).intValue());
        }
        return contents;
    }

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\lefeb\\IdeaProjects\\Clean-Code-Java\\src\\task\\test.txt";
        List<String> lines = readFile(path); // Lire toutes les lignes du fichier

        lines = deleteTodoScriptlet(lines);

        setContents(path, lines);
    }
}
