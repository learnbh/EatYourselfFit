package org.bea.backend.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    StringUtil stringUtil = new StringUtil();
    @ParameterizedTest
    @CsvSource({
            "Ä, ae",
            "Fuß, fuss",
            "tütütü, tuetuetue",
            "tötötö, toetoetoe",
            "tätatä, taetatae",
    })
    void mapLowercaseUmlautAndSharpS_shouldCorrectMapping_andReturnTrue(String actual, String expected) {
        //when
        String result = stringUtil.mapLowercaseUmlautAndSharpS(actual);
        // then
        assertTrue(result.contains(expected));
    }
}