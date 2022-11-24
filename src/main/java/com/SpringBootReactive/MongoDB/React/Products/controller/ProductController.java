package com.SpringBootReactive.MongoDB.React.Products.controller;

import com.SpringBootReactive.MongoDB.React.Products.dto.ProductDto;
import com.SpringBootReactive.MongoDB.React.Products.exception.ProductNotFoundExcpetion;
import com.SpringBootReactive.MongoDB.React.Products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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
              //  .switchIfEmpty(Mono.error(new ProductAPIException("products not found")));
    }

    @GetMapping("/getProductById/{id}")
    public Mono<ProductDto> getProductById(@PathVariable("id") String id) {

        //return service.getProductById(id)
          //  .switchIfEmpty(Mono.error(new ProductAPIException("Product not found with id " + id)));
        return service.getProductById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundExcpetion(id)));

    }
/*
    public Mono<ResponseEntity<Student>> getStudentById(@PathVariable int studentId){
        return student_repository.findById(studentId)
                .map(student -> ResponseEntity.ok(student))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
*/
    @GetMapping("/getProductByName/{name}")
    public Flux<ProductDto> getProductByName(@PathVariable("name") String name)
    {
        return service.getProductByName(name);
                }
    @GetMapping("/getProductByCategory/{category}")
    public Flux<ProductDto> getProductByCategory(@PathVariable("category") String category) {
        return service.getProductByCategory(category);
    }

    @PostMapping("/getProductsWithinPriceRange")
    public Flux<ProductDto> getProductsWithinPriceRange(@RequestBody Map<Object,String> map)
    {
        String min=map.get("min").toString();
        String max=map.get("max").toString();
        double min1=Double. parseDouble(min);
        double max1=Double. parseDouble(max);
        return service.getProductsWithinPriceRange(min1,max1);
    }

    @PostMapping("/saveProduct")
    public Mono<ResponseEntity<ProductDto>> saveProduct(@RequestBody ProductDto productDto)
    {
        return service.saveProduct(productDto);
    } @PostMapping("/saveAll")
    public Flux<ProductDto> saveAllProduct(@RequestBody List<ProductDto> productDto)
    {

        return service.saveAll(productDto);
    }
    @PutMapping("/updateProduct/{id}")
    public Mono<ResponseEntity> updateProductById(@RequestBody ProductDto productDto,@PathVariable("id") String id)
    {
       /* ProductDto productDto=new ProductDto();
        productDto.setName(productDtoFromReactInput.getName());
        productDto.setCategory(productDtoFromReactInput.getCategory());
        productDto.setQty(Integer.parseInt(productDtoFromReactInput.getQty()));
        productDto.setPrice(Double.parseDouble(productDtoFromReactInput.getPrice()));
        List<Integer> pincodeList=new ArrayList<>();
        List<String> listofStringPincodes=productDtoFromReactInput.getListOfPinCodes();
        for(String s: listofStringPincodes)
        {
            pincodeList.add(Integer.parseInt(s));
        }
        productDto.setListOfPinCodes(pincodeList);*/
        Mono<ProductDto> productDtoMono=Mono.just(productDto);
        return service.updateProductById(productDtoMono,id);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public Mono<ResponseEntity<Void>> deleteProductById(@PathVariable("id") String id)
    {
        return service.deleteProductById(id);
    }
}
