package com.SpringBootReactive.MongoDB.React.Products.service;

import com.SpringBootReactive.MongoDB.React.Products.dto.ProductDto;
import com.SpringBootReactive.MongoDB.React.Products.entity.Product;
import com.SpringBootReactive.MongoDB.React.Products.repository.ProductMongoTemplateRepository;
import com.SpringBootReactive.MongoDB.React.Products.repository.ProductRepository;
import com.SpringBootReactive.MongoDB.React.Products.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private ProductMongoTemplateRepository repo1;


    public Flux<ProductDto> getAllProducts()
    {
        return repo.findAll().map(AppUtils::entityToDto);
    }
    public Flux<ProductDto> getProductByName(String name)
    {
        return repo1.findAllByName(name);
    }
    public Flux<ProductDto> getProductByCategory(String category)
    {
        return repo1.findAllByCategory(category);
    }
    public Flux<ProductDto> getProductsWithinPriceRange(Double min,Double max)
    {
        return repo.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ResponseEntity<ProductDto>> saveProduct( ProductDto productDto) {
        Product product=new Product(productDto.getId(),productDto.getName(), productDto.getCategory(), productDto.getQty(), productDto.getPrice(), productDto.getListOfPinCodes());
        return repo.save(product)
                .map(AppUtils::entityToDto)
                .map(p -> ResponseEntity.ok(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    public Flux<ProductDto> saveAll(List<ProductDto> data) {
        return repo1.saveAll(data);
    }

    public Mono<ProductDto> getProductById(String id)
    {
        return repo.findById(id).map(AppUtils::entityToDto);

    }


    /*public Mono<ProductDto> updateProductById(Mono<ProductDto> productDto,String id)
    {
        //Mono of Mono therefore flatmap is used
        return repo.findById(id)
                 .flatMap(p->productDto
                         .map(AppUtils::dtoToEntity)
                         .doOnNext(e->e.setId(id)))
                .flatMap(repo::save)
                .map(AppUtils::entityToDto);
    } */
    public Mono<ResponseEntity> updateProductById(Mono<ProductDto> productDto, String id)
    {
        //Mono of Mono therefore flatmap is used
        return repo.findById(id)
                 .flatMap(p->productDto
                         .map(AppUtils::dtoToEntity)
                         .doOnNext(e->e.setId(id)))
                .flatMap(repo::save)
                .map(AppUtils::entityToDto)
              .map(p -> ResponseEntity.status(HttpStatus.OK).body(p))
            .cast(ResponseEntity.class)
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid product id"));
    }
    public Mono<ResponseEntity<Void>> deleteProductById(String id)
    {
        return repo.findById(id)
                .flatMap(p ->
                        repo.delete(p)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
