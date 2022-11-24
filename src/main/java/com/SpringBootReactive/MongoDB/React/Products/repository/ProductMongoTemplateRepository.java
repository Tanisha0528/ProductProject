package com.SpringBootReactive.MongoDB.React.Products.repository;

import com.SpringBootReactive.MongoDB.React.Products.dto.ProductDto;
import com.SpringBootReactive.MongoDB.React.Products.entity.Product;
import com.SpringBootReactive.MongoDB.React.Products.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.*;

@Component
@RequiredArgsConstructor
public class ProductMongoTemplateRepository {

    private final ReactiveMongoTemplate template;


    public Flux<ProductDto> findAllByName(String name) {

        Flux<Product> productFlux= template
                .find(query(where("name").is(name)), Product.class);
        return
                productFlux.map(AppUtils::entityToDto);

    }
    public Flux<ProductDto> findAllByCategory(String category) {
        var reg = ".*" + category + ".*";
        Flux<Product> productFlux=template
                .find(query(where("category").regex(reg)), Product.class);
        return
                productFlux.map(AppUtils::entityToDto);

    }

    public Flux<ProductDto> saveAll(List<ProductDto> data) {

        List<Product> products=data.stream().map(AppUtils::dtoToEntity).collect(Collectors.toList());
        Flux<Product> productDtoFlux= Flux.fromIterable(products).flatMap(template::save);
        return productDtoFlux.map(AppUtils::entityToDto);


    }

}
