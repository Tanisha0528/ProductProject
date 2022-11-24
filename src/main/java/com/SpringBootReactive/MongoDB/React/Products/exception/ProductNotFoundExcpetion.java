package com.SpringBootReactive.MongoDB.React.Products.exception;

public class ProductNotFoundExcpetion extends RuntimeException{
    public ProductNotFoundExcpetion(String id) {
        super("could not find the product with id: "+id);
    }
}
