package com.SpringBootReactive.MongoDB.React.Products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*DTOs are used only to transfer data from one process or context to another.
  As such, they are without behavior - except for very basic and usually standardised
  storage and retrieval functions*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private String name;
    private String category;
    private int qty;
    private double price;
    private List<Integer> listOfPinCodes;



}
