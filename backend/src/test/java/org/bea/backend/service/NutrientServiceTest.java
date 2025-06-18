package org.bea.backend.service;

import org.bea.backend.enums.NutrientType;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.mapper.NutrientMapper;
import org.bea.backend.model.Nutrient;
import org.bea.backend.model.Nutrients;
import org.bea.backend.repository.NutrientsRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class NutrientServiceTest {

    private NutrientsRepository mockNutrientsRepository;
    private NutrientMapper mockNutrientMapper;
    private NutrientService nutrientService;

    Nutrients nutrients;
    Nutrient nutrient;

    @BeforeEach
    void setUp() {
        mockNutrientsRepository = Mockito.mock(NutrientsRepository.class);
        mockNutrientMapper = Mockito.mock(NutrientMapper.class);
        nutrientService = new NutrientService(mockNutrientsRepository, mockNutrientMapper);



        nutrients = new Nutrients("milk", nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient, nutrient);
        nutrient = new Nutrient("name", NutrientType.AMINOACID, 100.0, "g");
    }

    @Test
    void getNutrientsById_shouldThrowIdNotFoundException_whenIdIsNotFound() {
        assertThrows(IdNotFoundException.class, () -> nutrientService.getNutrientsById(nutrients.id()));
    }

    @Test
    void getNutrientsById_shouldReturn_IngredientMilkForMilkId() {
        // given
        mockNutrientsRepository.save(nutrients);
        // when
        Mockito.when(mockNutrientsRepository.findById(nutrients.id()))
                .thenReturn(Optional.of(nutrients));
        // then
        assertEquals(nutrients, nutrientService.getNutrientsById(nutrients.id()));
        Mockito.verify(mockNutrientsRepository, Mockito.times(1)).findById(nutrients.id());
    }

    @Test
    void addNutrients_shouldAddAndReturn_IngredientMilk() {
        // when
        Mockito.when(mockNutrientsRepository.save(nutrients))
                .thenReturn(nutrients);
        //then
        assertEquals(nutrients, nutrientService.addNutrients(nutrients));
        Mockito.verify(mockNutrientsRepository, Mockito.times(1)).save(nutrients);
    }

    @Test
    void updateNutrients_shouldReturnUpdatedNutrients() {
        // given
        Nutrients nutrients = Instancio.of(Nutrients.class)
                .set(field(Nutrients::id), "nutrientId")
                .set(field(Nutrients::energyKcal),  new Nutrient("Energie", NutrientType.MACRONUTRIENT,3.0, "kcal"))
                .create();
        Nutrient change = new Nutrient("Energie", NutrientType.MACRONUTRIENT,3.14, "kcal");
        Nutrient[] changeArr = {change};
        Nutrients expected = nutrients.withEnergyKcal(change);
        // when
        Mockito.when(mockNutrientsRepository.findById(nutrients.id())).thenReturn(Optional.of(nutrients));
        Mockito.when(mockNutrientMapper.updateNutrients(nutrients, changeArr)).thenReturn(expected);
        Mockito.when(mockNutrientsRepository.save(expected)).thenReturn(expected);
        Nutrients actual = nutrientService.updateNutrients(nutrients.id(), changeArr);
        // then
        assertEquals(expected, actual);
        // verify
        Mockito.verify(mockNutrientsRepository, Mockito.times(1)).findById(nutrients.id());
        Mockito.verify(mockNutrientsRepository, Mockito.times(1)).save(expected);
        Mockito.verify(mockNutrientMapper, Mockito.times(1)).updateNutrients(nutrients, changeArr);
    }
}