package task.EndClean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dico.Dico;
import utils.EditFileUtils;
import utils.Utils;

public class ImportViewCleanTask {

    public static List<String> importViewCleanTask(List<String> contents) {
        // Création du motif regex pour LocalizedFragment
        String matchLocalizedFragment = Dico.Regex.MATCH_LOCALIZED_FRAGMENT_PART1 + utils.Utils.getTri() + Dico.Regex.MATCH_LOCALIZED_FRAGMENT_PART2;

        int indexStart = Utils.getLineIndex(contents, matchLocalizedFragment);
        int indexEnd = Utils.getLineIndexFromStart(contents, Dico.Regex.MATCH_ARROW, indexStart) - 1;

        // Création de la liste des propriétés importées
        List<String> listImportedProps = new ArrayList<>();
        for (String content : contents.subList(indexStart, indexEnd)) {
            String cleanedContent = content.replace(" ", "").replace("\n", "").replace("\t", "").replace(",", "");
            listImportedProps.add(cleanedContent);
        }
        System.out.println("list_imported_props\n" + listImportedProps);

        int indexStartToCheck = Utils.getLineIndex(contents, Dico.Regex.MATCH_ARROW);
        int indexEndToCheck = Utils.getLineIndexFromStart(contents, Dico.Regex.MATCH_PROPS_TYPE, indexStartToCheck) - 1;

        List<String> listPropsToCheck = contents.subList(indexStartToCheck, indexEndToCheck);
        System.out.println("list_props_to_check\n" + listPropsToCheck);

        // Création de la liste des propriétés à conserver
        List<Integer> listPropsToSave = new ArrayList<>();
        for (int i = 0; i < listImportedProps.size(); i++) {
            String props = listImportedProps.get(i);
            for (String line : listPropsToCheck) {
                Pattern pattern = Pattern.compile(props);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    listPropsToSave.add(i);
                    break;
                }
            }
        }
        System.out.println("list_props_to_save\n" + listPropsToSave);

        // Création de la liste des propriétés à supprimer
        Set<Integer> allIndices = new HashSet<>();
        for (int i = 0; i < listImportedProps.size(); i++) {
            allIndices.add(i);
        }
        allIndices.removeAll(listPropsToSave);
        List<Integer> listPropsToDelete = new ArrayList<>(allIndices);
        Collections.sort(listPropsToDelete, Collections.reverseOrder());
        System.out.println("list_props_to_delete\n" + listPropsToDelete);

        // Suppression des propriétés inutiles dans le contenu
        for (int index : listPropsToDelete) {
            contents.remove(indexStart + index);
        }
        return contents;
    }

    public static void main(String[] args) {
        List<String> contents = EditFileUtils.readFile("C:\\Users\\lefeb\\IdeaProjects\\Clean Code Java\\src\\task\\test.txt");
        contents = importViewCleanTask(contents);
        EditFileUtils.setContents("C:\\Users\\lefeb\\IdeaProjects\\Clean Code Java\\src\\task\\test.txt", contents);
    }
}
