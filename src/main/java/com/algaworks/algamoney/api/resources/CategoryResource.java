package com.algaworks.algamoney.api.resources;

import com.algaworks.algamoney.api.models.Category;
import com.algaworks.algamoney.api.repositories.CategoryRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> save(@RequestBody Category category, HttpServletResponse response) {
        Category categorySave = categoryRepository.save(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categorySave.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categorySave);
    }
}
