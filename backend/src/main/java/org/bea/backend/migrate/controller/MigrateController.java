package org.bea.backend.migrate.controller;

import org.bea.backend.migrate.service.MigrateSlugService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eyf/job/migrate")
public class MigrateController {

    private final MigrateSlugService migrateSlugService;

    public MigrateController(MigrateSlugService migrateSlugService) {
        this.migrateSlugService = migrateSlugService;
    }

    @GetMapping
    public String status() {
        return "OK";
    }

    @GetMapping("/slugs")
    public boolean migrateSlugs() {
        System.out.println("migrateRecipeAddSlug");
        if (!migrateSlugService.migrateRecipeAddSlug()) return false;
        return migrateSlugService.migrateIngredientAddSlug();
    }
}
