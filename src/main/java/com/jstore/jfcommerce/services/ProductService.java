package com.jstore.jfcommerce.services;

import com.jstore.jfcommerce.dto.ProductDTO;
import com.jstore.jfcommerce.entities.Product;
import com.jstore.jfcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        var result =  repository.findById(id).get();
        return new ProductDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result =  repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert (ProductDTO dto) {
        ProductDTO productDTO = new ProductDTO();
        Product entity = productDTO.convertToEntity(dto);
        repository.save(entity);
        return dto;
    }

    @Transactional
    public ProductDTO update (Long id, ProductDTO dto) {
        var entity =  repository.getReferenceById(id);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
