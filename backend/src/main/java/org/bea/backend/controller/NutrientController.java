package org.bea.backend.controller;

import org.bea.backend.model.Nutrient;
import org.bea.backend.model.Nutrients;
import org.bea.backend.service.NutrientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eyf/nutrients")
public class NutrientController {
    private final NutrientService nutrientService;

    public NutrientController(NutrientService nutrientService) {
        this.nutrientService = nutrientService;
    }
    @GetMapping("/detail/{id}")
    public Nutrients getNutrients(@PathVariable String id) {
        return nutrientService.getNutrientsById(id);
    }
    @PostMapping
    public Nutrients addNutrients(@RequestBody Nutrients nutrients){
        return nutrientService.addNutrients(nutrients);
    }
    @PutMapping("/{id}")
    public Nutrients updateNutrients(@PathVariable String id, @RequestBody Nutrient[] nutrientArray) {
        return nutrientService.updateNutrients(id, nutrientArray);
    }
}
