package dico;

public class Dico {

    public static class Regex {
        public static final String MATCH_ANY_STRING = "(.|\\n)*";
        public static final String MATCH_LOCALIZED_FRAGMENT_PART1 = "LocalizedFragment\\(";
        public static final String MATCH_LOCALIZED_FRAGMENT_PART2 = "Dico\\)\\(\\(\\{";
        public static final String MATCH_ARROW = "\\}\\) => ";
        public static final String MATCH_PROPS_TYPE = ".propTypes = \\{";
        public static final String MATCH_DEFAULT_PROPS = ".defaultProps = \\{";
        public static final String MATCH_BRACKET = "\\}";
        public static final String MATCH_BRACKET_AND_COMMA_POINT = "\\};";
        public static final String MATCH_BEFORE_WITH_NO_LETTER_AND_NUMBER_CHEVRON_SLASH = "(?<![0-9a-zA-Z</>])";
        public static final String MATCH_AFTER_WITH_NO_LETTER_AND_NUMBER_CHEVRON_SLASH = "(?![0-9a-zA-Z</>])";
        public static final String MATCH_TAB = "\\t";
        public static final String MATCH_START_EXPORT_PROPS_IN_PAGE = "";
        public static final String MATCH_AUTO_CLOSE = "/>";
    }

    public static class Schema {
        public static final String MATCH_PARENTHESIS_AND_BRACKET = ")}";
        public static final String MATCH_CLOSE_FRAGMENT = "</>";
    }

    public static class Information {
        public static final String INFORMATION = "Information";
        public static final String ERREUR = "Erreur";
        public static final String MORE1_MATCH = "You have more than 1 match";
        public static final String EXECUTE_SUCCESS = "Execute success";
    }

    public static class Type {
        public static final String STRING = "string";
        public static final String FUNCTION = "function";
        public static final String OBJ = "obj";
        public static final String PROPS_TYPE_STRING = ": PropTypes.string,";
        public static final String PROPS_TYPE_FUNCTION = ": PropTypes.func,";
        public static final String PROPS_TYPE_OBJECT = ": PropTypes.shape({}),";
    }

    public static class Valeur {
        public static final String MESSAGE_BEFORE_DECLARATION_PROPS_TYPE = "// prop types validation";
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
        System.out.println(Regex.MATCH_ANY_STRING); // Affiche le regex pour correspondre à n'importe quelle chaîne
        System.out.println(Information.ERREUR);     // Affiche le message d'erreur
    }
}
