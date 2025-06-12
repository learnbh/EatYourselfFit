package org.bea.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bea.backend.enums.NutrientType;
import org.bea.backend.model.Nutrient;
import org.bea.backend.model.Nutrients;
import org.bea.backend.repository.NutrientsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NutrientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NutrientsRepository mockNutrientsRepository;

    ObjectMapper mapper = new ObjectMapper();

    Nutrient nutrient = new Nutrient("name",NutrientType.AMINOACID, 100.0, "g");
    Nutrients nutrients = new Nutrients("milk", nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient);

    @Test
    void getNutrients_shouldThrowIdNotFoundException_whenIdIsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/nutrients/detail/"+nutrients.id()))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error").value("Error: Nutrient nicht gefunden. Id existiert nicht."));
    }
    @Test
    void getNutrients_shouldReturn_IngredientMilkForMilkId() throws Exception {
        mockNutrientsRepository.save(nutrients);
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/nutrients/detail/"+nutrients.id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(nutrients)));
    }

    @Test
    void addNutrients_shouldAddAndReturn_IngredientMilk() throws Exception {
        System.out.println(mapper.writeValueAsString(nutrients));
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/nutrients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(nutrients)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(nutrients)));
    }
}