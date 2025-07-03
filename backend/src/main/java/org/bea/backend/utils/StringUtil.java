package org.bea.backend.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class StringUtil {
    private static final Map<Character, String> umlautMap = new HashMap<>();

    static {
        umlautMap.put('ä', "ae");
        umlautMap.put('ö', "oe");
        umlautMap.put('ü', "ue");
        umlautMap.put('ß', "ss");
    }

    public String mapLowercaseUmlautAndSharpS(String name){
        String lowerCase = name.toLowerCase();
        StringBuilder nameWithoutUmlautBuilder = new StringBuilder();
        for (char c : lowerCase.toCharArray()) {
            if (umlautMap.containsKey(c)) {
                nameWithoutUmlautBuilder.append(umlautMap.get(c));
            } else {
                nameWithoutUmlautBuilder.append(c);
            }
        }
        return nameWithoutUmlautBuilder.toString();
    }
}
