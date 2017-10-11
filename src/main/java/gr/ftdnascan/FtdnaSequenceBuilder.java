package gr.ftdnascan;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FtdnaSequenceBuilder {

    public static List<String> buildFromRawText( String text ) {

        String delimitedText = buildSequence(text);
        List<String> sequence = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(delimitedText, ">");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if( 1 <= token.length() )
                sequence.add(token);
        }

        return sequence;
    }

    public static String buildSequence( String original ) {

        String haploSequence = "";

        // extract main part - group with subgroup //
        StringTokenizer st = new StringTokenizer(original, " \t\n\r,."); // legal symbols: ?()-
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if( 3 < token.length() && token.contains(">") ) {
                haploSequence = token;
                break;
            }
        }

        if(haploSequence.isEmpty())
            return "";
        else
            return normalizeSequence(haploSequence);
    }

    public static String normalizeSequence( String haploSequence ) {

        String sequence = fixHaploSecuence(haploSequence);

        String normalized = sequence;

        // extract group, mutation and subgroup //
        if( 0 < sequence.length() && sequence.contains(">") ) {
            int indexOfLastAngle = sequence.lastIndexOf(">");
            String mutationAndSubgroup = sequence.substring(indexOfLastAngle + 1);

            // M420>YP4141>YP4132>YP4131-x1
            if( mutationAndSubgroup.contains("-") || mutationAndSubgroup.contains("*") ) {

                int indexOfDash = mutationAndSubgroup.contains("*")?
                        mutationAndSubgroup.indexOf("*") : mutationAndSubgroup.indexOf("-");

                String precedence = sequence.substring(0, indexOfLastAngle);
                String mutation = mutationAndSubgroup.substring(0, indexOfDash);

                normalized = precedence + ">" + mutation + ">" + mutationAndSubgroup;
            }
        }

        return normalized;
    }

    public static String fixHaploSecuence( String original ) {

        String fixed = original;

        fixed = fixed.replace("> ", ">"); // fix error in E21006: M420>M459> YP1272>YP1276-x
        fixed = fixed.replace("> -", "-"); // fix error in 344533: >M417>CTS4385>Y2894>L664>(S3478?)>S2894>YP285>YP282-C>YP441>YP1014> -A
        fixed = fixed.replace(" -", "-"); // fix error in 558105: >M417>CTS4385>Y2894>L664>(S3478?)>S2894>S2880 -y
        fixed = fixed.replace(">(", ">"); // fix error in N128746: YP326>(CLD56?)>CLD12*
        fixed = fixed.replace("?)>", ">"); // fix error in N128746: YP326>(CLD56?)>CLD12*

        fixed = fixed.replace("/", ">"); // needed for 72455 127747 454141: >Z93>Z94>Z2124>Z2125>S23592>S23201>YP1359/YFS102022

        fixed = fixed.replace("(?)", ""); // fix error in N128746: YP326>(CLD56?)>CLD12*
        fixed = fixed.replace("(", ""); // needed for N128746: YP326>(CLD56?)>CLD12*
        fixed = fixed.replace(")", ""); // needed for N128746: YP326>(CLD56?)>CLD12*
        fixed = fixed.replace("?", ""); // needed for N128746: YP326>(CLD56?)>CLD12*, M7267: >Z645>Z93(?)>Z94(?)
        fixed = fixed.replace(" ", ""); // trim

        fixed = fixed.trim();

        return fixed;
    }

    public static int extractSection( String original ) {

        int section = 0;
        if (0 < original.length() && original.contains(".")) {
            int indexOfGroupPoint = original.indexOf(".");
            String section_str = original.substring(0, indexOfGroupPoint);
            try {
                section = Integer.parseInt(section_str);
            } catch (NumberFormatException e) {
                section = 0;
            }
        }
        return section;
    }

    public static String extractRecommendation( String original ) {

        String recommendation = "";
        if( 0 < original.length() && original.contains(">") && original.contains("(")) {
            int indexOfLastOpen = original.lastIndexOf(">");
            String sinceLastOpen = original.substring(indexOfLastOpen);
            recommendation = FtdnaPage.extractBetween(sinceLastOpen, "(", ")");
            recommendation = recommendation.trim();
        }
        return recommendation;
    }

}
