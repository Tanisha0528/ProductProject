package com.SpringBootReactive.MongoDB.React.Products.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/*
Entities may be part of a business domain. Thus, they can implement behavior
and be applied to different use cases within the domain.
*/


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Products")
public class Product{

    @Id
    private String id;
    private String name;
    private String category;
    private int qty;
    private double price;
    private List<Integer> listOfPinCodes;


}
