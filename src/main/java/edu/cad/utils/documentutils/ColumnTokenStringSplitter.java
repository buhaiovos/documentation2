package edu.cad.utils.documentutils;

import edu.cad.utils.Utils;

/**
 *
 * @author Олександр
 */
public class ColumnTokenStringSplitter {
    
    public static final String CURRICULUM_TOKEN_BEGINNING = "#";
    public static final String K3_ST_LOAD_TOKEN_BEGINNING = "k3(SL)";
    public static final String      K3_WP_TOKEN_BEGINNING = "k3(WP)";
    
    private final String token;
    
    private String type;
    private String formula;
    private String firstNumString;
    private String secondNumString;
    
    public ColumnTokenStringSplitter(String token) {
        this.token = token.trim();
        split();
    }

    private void split() {
        if ( ( ! token.contains(K3_ST_LOAD_TOKEN_BEGINNING))
                && ( ! token.contains(K3_WP_TOKEN_BEGINNING)) ) {
            setType();
            setNumStrings();
        }
        else if (token.contains(K3_ST_LOAD_TOKEN_BEGINNING)){
            setType();
            setFormula();
        }
        else {
            setType();
        }
    }

    private void setType() {
        int typeStartIndex = token.indexOf('#') + 1;
        int typeEndIndex = token.indexOf('_');
        type = token.substring(typeStartIndex, typeEndIndex).trim();
    }

    private void setNumStrings() {
        String[] parts = token.split("_");
        if (parts != null && parts.length > 0) {
            switch (parts.length) {
                case 1:
                    firstNumString = null;
                    secondNumString = null;
                    break;
                case 2:
                    if (Utils.isParseable(parts[1].trim())) {
                        firstNumString = parts[1].trim();
                    }
                    secondNumString = null;
                    break;
                default:
                    if (Utils.isParseable(parts[1].trim())) {
                        firstNumString = parts[1].trim();
                    }
                    if (Utils.isParseable(parts[2].trim())) {
                        secondNumString = parts[2].trim();
                    }
                    break;
            }
        }
        else {
            firstNumString = null;
            secondNumString = null;
        }
                    
    }
    
    private void setFormula() {
        formula = token.substring(token.indexOf('_') + 1).replaceAll(",", ".").replaceAll(";", ",");
    }

    public String getType() {
        return type;
    }

    public String getFormula() {
        return formula;
    }
    
    public String getFirstNumString() {
        return firstNumString;
    }

    public String getSecondNumString() {
        return secondNumString;
    }
    
}
