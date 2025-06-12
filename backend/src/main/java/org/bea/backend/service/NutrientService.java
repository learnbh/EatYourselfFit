package org.bea.backend.service;

import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.model.Nutrients;
import org.bea.backend.repository.NutrientsRepository;
import org.springframework.stereotype.Service;

@Service
public class NutrientService {
    private final NutrientsRepository nutrientsRepository;

    public NutrientService(NutrientsRepository nutrientsRepository) {
        this.nutrientsRepository = nutrientsRepository;
    }

    public Nutrients getNutrientById(String id) {
        return nutrientsRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Nutrient nicht gefunden. Id existiert nicht."));
    }

    public Nutrients addNutrients(Nutrients nutrients){
        nutrientsRepository.save(nutrients);
        return nutrients;
    }
}
