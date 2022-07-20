package com.pauloeduardocosta.forum.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String primeiraLetraMaiuscula(String texto) {
        StringBuilder strbf = new StringBuilder();
        Matcher match = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(texto.toLowerCase());
        while(match.find()) {
            match.appendReplacement(strbf, match.group(1).toUpperCase() + match.group(2).toLowerCase());
        }
        return match.appendTail(strbf).toString();
    }

    public static String converterParaStringSeparadaPorVirgula(List<String> lista) {
        if(lista.size() != 0) {
            StringBuilder sb = new StringBuilder();
            lista.forEach(tag -> sb.append(tag).append(", "));

            return sb.substring(0, sb.length() - 2);
        }
        return null;
    }
}
