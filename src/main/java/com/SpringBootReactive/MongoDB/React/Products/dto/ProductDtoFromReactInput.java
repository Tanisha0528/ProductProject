package com.SpringBootReactive.MongoDB.React.Products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoFromReactInput {

    private String name;
    private String category;
    private String qty;
    private String price;
    private List<String> listOfPinCodes;
}
