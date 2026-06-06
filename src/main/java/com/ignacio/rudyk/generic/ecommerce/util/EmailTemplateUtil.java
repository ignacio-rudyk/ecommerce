package com.ignacio.rudyk.generic.ecommerce.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailTemplateUtil {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([^}]+)\\}\\}");

    private EmailTemplateUtil() {
    }

    public static String applyVariables(String template, Map<String, String> variables) {
        if (template == null) {
            return "";
        }
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = variables.getOrDefault(key, "");
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static String buildHtmlBody(String title, String message) {
        return """
                <!DOCTYPE html>
                <html>
                <body>
                  <h2>%s</h2>
                  <div>%s</div>
                </body>
                </html>
                """.formatted(title, message);
    }

}
