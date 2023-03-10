package com.algaworks.algamoney.api.resources;

import com.algaworks.algamoney.api.event.ResouceCreatedEvent;
import com.algaworks.algamoney.api.models.Category;
import com.algaworks.algamoney.api.repositories.CategoryRepository;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/categories")
    @PreAuthorize("hasAutority('ROLE_FIND_CATEGORY') and hasAuthority('SCOPE_read')")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    @PreAuthorize("hasAutority('ROLE_INSERT_CATEGORY') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Category> save(@Valid @RequestBody Category category, HttpServletResponse response) {
        Category categorySave = categoryRepository.save(category);
        publisher.publishEvent(new ResouceCreatedEvent(this, response, categorySave.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
    }

    @GetMapping("/categories/{id}")
    @PreAuthorize("hasAutority('ROLE_FIND_CATEGORY') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.isPresent() ? ResponseEntity.ok(category.get()) : ResponseEntity.notFound().build();
    }
}
