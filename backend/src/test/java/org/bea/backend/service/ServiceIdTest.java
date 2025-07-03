package org.bea.backend.service;

import org.bea.backend.utils.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ServiceIdTest {

    StringUtil mockStringUtil;

    ServiceId serviceId;

    Integer uuidLength;

    @BeforeEach
    void setUp() {
        mockStringUtil = Mockito.mock(StringUtil.class);
        serviceId = new ServiceId(mockStringUtil);
        uuidLength = 10;
    }

    @Test
    void generateId_ShouldReturnUUIDAsString() {
        // when
        String expectedId = serviceId.generateId();
        assertNotNull(expectedId);
        assertTrue(expectedId.matches("^[a-f0-9-]{36}$"));
    }

    @Test
    void generateUUIDWithLength() {
        // when
        String expectedId = serviceId.generateUUIDWithLength(uuidLength);
        assertEquals(10, expectedId.length());
        assertTrue(expectedId.matches("^[a-zA-Z0-9_-]{10}$"));
    }

    @Test
    void generateSlug_shouldReturnSlugWithGivenName() {
        // given
        String name = "Hering in Weißweinsoße";
        String expectedInSlug = "hering-in-weissweinsosse";
        // when
        Mockito.when(mockStringUtil.mapLowercaseUmlautAndSharpS(name)).thenReturn(expectedInSlug);
        String actualSlug = serviceId.generateSlug(name);
        // then
        assertTrue(actualSlug.contains(expectedInSlug));
        // verify
        Mockito.verify(mockStringUtil, Mockito.times(1))
                .mapLowercaseUmlautAndSharpS(name);
    }

}