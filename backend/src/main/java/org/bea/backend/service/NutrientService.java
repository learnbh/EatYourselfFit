package org.bea.backend.service;

import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.mapper.NutrientMapper;
import org.bea.backend.model.Nutrient;
import org.bea.backend.model.Nutrients;
import org.bea.backend.repository.NutrientsRepository;
import org.springframework.stereotype.Service;

@Service
public class NutrientService {
    private final NutrientsRepository nutrientsRepository;
    private final NutrientMapper nutrientMapper;

    public NutrientService(NutrientsRepository nutrientsRepository, NutrientMapper nutrientMapper) {
        this.nutrientsRepository = nutrientsRepository;
        this.nutrientMapper = nutrientMapper;
    }

    public Nutrients getNutrientsById(String id) {
        return nutrientsRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Nutrient nicht gefunden. Id existiert nicht."));
    }

    public Nutrients addNutrients(Nutrients nutrients){
        nutrientsRepository.save(nutrients);
        return nutrients;
    }

    public Nutrients updateNutrients(String id, Nutrient[] nutrientArray){
        Nutrients currentNutrients = getNutrientsById(id);
        Nutrients updatedNutrients = nutrientMapper.updateNutrients(currentNutrients, nutrientArray);
        nutrientsRepository.save(updatedNutrients);
        return updatedNutrients;
    }
}
