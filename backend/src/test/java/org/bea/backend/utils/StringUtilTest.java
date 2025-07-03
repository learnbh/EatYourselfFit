package org.bea.backend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    StringUtil stringUtil = new StringUtil();

    @Test
    void mapLowercaseUmlautAndSharpS_shouldReturnMapped_Umlaut_a_To_ae() {
        // given
        String name = "tätätä";
        String nameWithoutUmlaute = "taetaetae";
        //when
        String result = stringUtil.mapLowercaseUmlautAndSharpS(name);
        // then
        assertTrue(result.contains(nameWithoutUmlaute));
    }

    @Test
    void mapLowercaseUmlautAndSharpS_shouldReturnMapped_Umlaut_o_To_oe() {
        // given
        String name = "tötötö";
        String nameWithoutUmlaute = "toetoetoe";
        //when
        String result = stringUtil.mapLowercaseUmlautAndSharpS(name);
        // then
        assertTrue(result.contains(nameWithoutUmlaute));
    }

    @Test
    void mapLowercaseUmlautAndSharpS_shouldReturnMapped_Umlaut_u_To_ue() {
        // given
        String name = "tütütü";
        String nameWithoutUmlaute = "tuetuetue";
        //when
        String result = stringUtil.mapLowercaseUmlautAndSharpS(name);
        // then
        assertTrue(result.contains(nameWithoutUmlaute));
    }
    @Test
    void mapLowercaseUmlautAndSharpS_shouldReturnMapped_sharp_s_To_ss() {
        // given
        String name = "Fuß";
        String nameWithoutUmlaute = "fuss";
        //when
        String result = stringUtil.mapLowercaseUmlautAndSharpS(name);
        // then
        assertTrue(result.contains(nameWithoutUmlaute));
    }
    @Test
    void mapLowercaseUmlautAndSharpS_shouldReturnMapped_Umlaut_A_To_ae_InLowerCase() {
        // given
        String name = "Ä";
        String nameWithoutUmlaute = "ae";
        //when
        String result = stringUtil.mapLowercaseUmlautAndSharpS(name);
        // then
        assertTrue(result.contains(nameWithoutUmlaute));
    }
}