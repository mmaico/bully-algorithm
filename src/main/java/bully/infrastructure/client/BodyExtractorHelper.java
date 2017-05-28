package bully.infrastructure.client;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BodyExtractorHelper {

    public static String extractId(String body) {
        final String pattern = ":(\\s)?\".+?\"";
        final Pattern compile = Pattern.compile(pattern);
        final Matcher matcher = compile.matcher(body);
        if (matcher.find()) {
            String result = matcher.group(0);
            return result.replaceAll("(\\s|:|\")", "");
        }

        return "";
    }
}
