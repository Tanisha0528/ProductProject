package com.SpringBootReactive.MongoDB.React.Products.util;

import com.SpringBootReactive.MongoDB.React.Products.dto.ProductDto;
import com.SpringBootReactive.MongoDB.React.Products.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto entityToDto(Object product)
    {
        ProductDto productDto=new ProductDto();
        //possible ony if both classes have same member variables
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }
    public static Product dtoToEntity(ProductDto productDto)
    {
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product);
        return product;
    }
}
