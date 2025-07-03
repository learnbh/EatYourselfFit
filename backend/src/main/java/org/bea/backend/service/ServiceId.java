package org.bea.backend.service;

import org.bea.backend.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;

@Service
public class ServiceId {
    private final StringUtil slugUtil;

    public ServiceId(StringUtil stringUtil) {
        this.slugUtil = stringUtil;
    }

    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public String generateSlug(String nameSEO){

        String noUmlaut = slugUtil.mapLowercaseUmlautAndSharpS(nameSEO);
        String nowhitespace = noUmlaut.trim().replaceAll("\\s+", "-");
        String lowerCase = nowhitespace.toLowerCase();
        String normalized = Normalizer.normalize(lowerCase, Normalizer.Form.NFD);
        String onlyGuiltySigns = normalized.replaceAll("[^\\w-]", "");
        return onlyGuiltySigns+"-"+generateUUIDWithLength(10);
    }

    public String generateUUIDWithLength(Integer length){

        return UUID.randomUUID()
                .toString()
                .replace("-","")
                .substring(0, length);
    }
}
