package task.EndClean;

import dico.Dico;
import utils.EditFileUtils;
import utils.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.EditFileUtils.setContents;
import static utils.Utils.getFileName;
import static utils.Utils.getPageFromView;

public class ExportPageCleanTask {

    public static void exportPageCleanTask(List<String> contents, String path) {
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

        String pathOfPage = getPageFromView(path);
        List<String> contentsExport = EditFileUtils.readFile(pathOfPage);
        int indexStartToCheck = Utils.getLineIndex(
                contentsExport,
                "<" + getFileName(path).substring(0, getFileName(path).lastIndexOf(".jsx")));
        int indexEndToCheck = Utils.getLineIndexFromStart(contentsExport, Dico.Regex.MATCH_AUTO_CLOSE, indexStartToCheck) - 1;

        // Suppression ligne vide
        for (int i = indexEndToCheck; i >= indexStartToCheck; i--) {
            if (contentsExport.get(i).trim().isEmpty()) {
                contentsExport.remove(i);
                indexEndToCheck--;
            }
        }
        List<String> listPropsToCheck = contentsExport.subList(indexStartToCheck, indexEndToCheck);
        System.out.println("list_props_to_check\n" + listPropsToCheck);

        // Création de la liste des propriétés à conserver
        List<Integer> listPropsToDelete = new ArrayList<>();
        for (int i = 0; i < listPropsToCheck.size(); i++) {
            String props = listPropsToCheck.get(i);
            boolean toAdd = true;
            for (String line : listImportedProps) {
                Pattern pattern = Pattern.compile(line + Dico.Regex.MATCH_AFTER_WITH_NO_LETTER_AND_NUMBER_CHEVRON_SLASH);
                Matcher matcher = pattern.matcher(props);
                if (matcher.find()) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                listPropsToDelete.add(i);
            }
        }
        System.out.println("list_props_to_save\n" + listPropsToDelete);



        // Suppression des propriétés inutiles dans le contenu
        for (int i = listPropsToDelete.size() - 1; i >= 0; i--) {
            contentsExport.remove(indexStartToCheck + listPropsToDelete.get(i));
        }
        setContents(pathOfPage, contentsExport);
    }

}
