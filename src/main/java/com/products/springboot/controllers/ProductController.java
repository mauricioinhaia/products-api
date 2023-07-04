package com.products.springboot.controllers;

import com.products.springboot.dtos.ProductRecordDto;
import com.products.springboot.models.ProductModel;
import com.products.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {
    ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct (@RequestBody @Valid ProductRecordDto productRecordDto){
        var productModel = new  ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts () {
        List<ProductModel> productList = productRepository.findAll();
        if (!productList.isEmpty()){
            for(ProductModel product : productList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct (@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);
        if (productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        productModelOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct (@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);
        if (productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        var productModel = productModelOptional.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct (@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);
        if (productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        productRepository.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product Deleted");
    }

}
