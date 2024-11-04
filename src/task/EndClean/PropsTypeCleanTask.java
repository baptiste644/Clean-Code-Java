package task.EndClean;

import dico.Dico;
import utils.EditFileUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PropsTypeCleanTask {

    public static List<String> propsTypeCleanTask(List<String> contents) {
        // Création du motif Regex pour LocalizedFragment
        String matchLocalizedFragment = Dico.Regex.MATCH_LOCALIZED_FRAGMENT_PART1 + Utils.getTri() + Dico.Regex.MATCH_LOCALIZED_FRAGMENT_PART2;

        // Trouver les indices de début et de fin des propriétés importées
        int indexStart = Utils.getLineIndex(contents, matchLocalizedFragment);
        int indexEnd = Utils.getLineIndexFromStart(contents, Dico.Regex.MATCH_ARROW, indexStart) - 1;

        // Créer la liste des propriétés importées
        List<String> listImportedProps = new ArrayList<>();
        for (String content : contents.subList(indexStart, indexEnd)) {
            String cleanedContent = content.replace(" ", "").replace("\n", "").replace("\t", "").replace(",", "");
            listImportedProps.add(cleanedContent);
        }

        // Trouver les propriétés à vérifier
        int indexStartToCheck = Utils.getLineIndex(contents, Dico.Regex.MATCH_PROPS_TYPE);
        int indexEndToCheck = Utils.getLineIndexFromStart(contents, Dico.Regex.MATCH_BRACKET_AND_COMMA_POINT, indexStartToCheck) - 1;

        // Créer la liste des propriétés à vérifier
        List<String> listPropsToCheck = new ArrayList<>();
        for (String content : contents.subList(indexStartToCheck, indexEndToCheck)) {
            String prop = content.split(":")[0].replace(" ", "").replace("\n", "").replace("\t", "").replace(",", "");
            listPropsToCheck.add(prop);
        }

        // Identifier les propriétés à supprimer
        Set<String> propsToDeleteSet = new HashSet<>(listPropsToCheck);
        propsToDeleteSet.removeAll(listImportedProps);

        // Supprimer les propriétés inutiles dans le contenu
        List<Integer> uniqueElementsWithIndices = new ArrayList<>();
        for (int i = 0; i < listPropsToCheck.size(); i++) {
            if (!listImportedProps.contains(listPropsToCheck.get(i))) {
                uniqueElementsWithIndices.add(i);
            }
        }

        for (int i = uniqueElementsWithIndices.size() - 1; i >= 0; i--) {
            contents.remove(indexStartToCheck + uniqueElementsWithIndices.get(i).intValue());
        }

        return contents;
    }
}
