package com.jstore.jfcommerce.controller;

import com.jstore.jfcommerce.dto.ProductDTO;
import com.jstore.jfcommerce.repository.ProductRepository;
import com.jstore.jfcommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findProductById (@PathVariable Long id){
        var response = service.findById(id);
         return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll (Pageable pageable){
        var response = service.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> postProduct (@RequestBody ProductDTO dto) {
       dto = service.insert(dto);
       URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
               .buildAndExpand(dto.getId()).toUri();
       return ResponseEntity.created(uri).body(dto);

    }


}
