package task.EndClean;

import dico.Dico;
import utils.EditFileUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DefaultPropsTypeCleanTask {


    public static List<String> defaultPropsTypeCleanTask(List<String> contents) {
        // Préparation du regex pour LocalizedFragment
        String matchLocalizedFragment = Dico.Regex.MATCH_LOCALIZED_FRAGMENT_PART1
                + Utils.getTri()
                + Dico.Regex.MATCH_LOCALIZED_FRAGMENT_PART2;

        int indexStart = Utils.getLineIndex(contents, matchLocalizedFragment);
        int indexEnd = Utils.getLineIndexFromStart(contents, Dico.Regex.MATCH_ARROW, indexStart) - 1;

        List<String> listImportedProps = contents.subList(indexStart, indexEnd).stream()
                .map(content -> content.replace(" ", "").replace("\n", "").replace("\t", "").replace(",", ""))
                .collect(Collectors.toList());
        System.out.println(listImportedProps);

        int indexStartToCheck = Utils.getLineIndex(contents, Dico.Regex.MATCH_DEFAULT_PROPS);
        int indexEndToCheck = Utils.getLineIndexFromStart(contents, Dico.Regex.MATCH_BRACKET_AND_COMMA_POINT, indexStartToCheck) - 1;

        List<String> listPropsToCheck = contents.subList(indexStartToCheck, indexEndToCheck).stream()
                .map(content -> content.split(":")[0].replace(" ", "").replace("\n", "").replace("\t", "").replace(",", ""))
                .collect(Collectors.toList());
        System.out.println(listPropsToCheck);

        Set<String> setImportedProps = new HashSet<>(listImportedProps);
        List<String> listPropsToDelete = listPropsToCheck.stream()
                .filter(prop -> !setImportedProps.contains(prop))
                .collect(Collectors.toList());
        System.out.println(listPropsToDelete);

        List<Integer> uniqueElementsIndices = new ArrayList<>();
        List<String> uniqueElements = new ArrayList<>();
        for (int i = 0; i < listPropsToCheck.size(); i++) {
            String element = listPropsToCheck.get(i);
            if (!setImportedProps.contains(element)) {
                uniqueElementsIndices.add(i);
                uniqueElements.add(element);
            }
        }

        // Supprimer les éléments du contenu original en partant de la fin
        for (int i =0; i< uniqueElementsIndices.size(); i++) {
            if (contents.get(indexStartToCheck + uniqueElementsIndices.get(i))
                    .contains(uniqueElements.get(i))) {
                contents.remove(indexStartToCheck + uniqueElementsIndices.get(i));
            }
        }

        return contents;
    }

    public static void main(String[] args) {
        List<String> contents = EditFileUtils.readFile("C:\\Users\\lefeb\\IdeaProjects\\Clean Code Java\\src\\task\\test.txt");
        contents = defaultPropsTypeCleanTask(contents);
        EditFileUtils.setContents("C:\\Users\\lefeb\\IdeaProjects\\Clean Code Java\\src\\task\\test.txt", contents);
    }
}
