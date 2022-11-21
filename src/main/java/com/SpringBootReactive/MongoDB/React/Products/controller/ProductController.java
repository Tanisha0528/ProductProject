package com.SpringBootReactive.MongoDB.React.Products.controller;

import com.SpringBootReactive.MongoDB.React.Products.dto.ProductDto;
import com.SpringBootReactive.MongoDB.React.Products.exception.ProductAPIException;
import com.SpringBootReactive.MongoDB.React.Products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/getAllProducts")
    public Flux<ProductDto> getAllProducts()
    {
        return service.getAllProducts();
    }

    @GetMapping("/getProductById/{id}")
    public Mono<ProductDto> getProductById(@PathVariable("id") String id)
    {
        return service.getProductById(id)
            .switchIfEmpty(Mono.error(new ProductAPIException("Product not found with id " + id)));
    }

    @GetMapping("/getProductByName/{name}")
    public Flux<ProductDto> getProductByName(@PathVariable("name") String name)
    {
        return service.getProductByName(name);
                }
    @GetMapping("/getProductByCategory/{category}")
    public Flux<ProductDto> getProductByCategory(@PathVariable("category") String category) {
        return service.getProductByCategory(category);
    }

    @GetMapping("/getProductsWithinPriceRange/{min}/{max}")
    public Flux<ProductDto> getProductsWithinPriceRange(@PathVariable("min") String min,@PathVariable("max") String max)
    {
        double min1=Double. parseDouble(min);
        double max1=Double. parseDouble(max);
        return service.getProductsWithinPriceRange(min1,max1);
    }

    @PostMapping("/saveProduct")
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto)
    {

        return service.saveProduct(productDto);
    } @PostMapping("/saveAll")
    public Flux<ProductDto> saveAllProduct(@RequestBody List<ProductDto> productDto)
    {

        return service.saveAll(productDto);
    }
    @PutMapping("/updateProduct/{id}")
    public Mono<ProductDto> updateProductById(@RequestBody Mono<ProductDto> productDto,@PathVariable("id") String id)
    {
        return service.updateProductById(productDto,id);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public Mono<Void> deleteProductById(@PathVariable("id") String id)
    {
        return service.deleteProductById(id);
    }
}
