package com.SpringBootReactive.MongoDB.React.Products;

import com.SpringBootReactive.MongoDB.React.Products.controller.ProductController;
import com.SpringBootReactive.MongoDB.React.Products.dto.ProductDto;
import com.SpringBootReactive.MongoDB.React.Products.entity.Product;
import com.SpringBootReactive.MongoDB.React.Products.service.ProductService;
import com.SpringBootReactive.MongoDB.React.Products.util.AppUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@WebFluxTest(ProductController.class)
public class ProductsApplicationTests {

	@Autowired
	private WebTestClient client;

	//we are mocking service layer which means the check goes only till the controller of the java package
	//not to the service its like placing a copy of service class over here

	@MockBean
	private ProductService service;

	@Test
	public void getAllProductsTest()
	{
		List<Integer> pincode = new ArrayList<Integer>();

		// Initialize an ArrayList with add()
		pincode.add(1234);
		pincode.add(12345);
		pincode.add(123456);



		Flux<ProductDto> productDtoFlux=Flux.just(
				new ProductDto("1","iphone","Mobile",1,100000,pincode),
				new ProductDto("2","samsung","Mobile",1,10000,pincode)


		);

		//we want productDtoFlux to be returned
		when(service.getAllProducts()).thenReturn(productDtoFlux);

		Flux<ProductDto> responseBody=client.get().uri("/products/getAllProducts")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		//here we check whether the response body contains products we returned using
		//thenReturn,match them using expectNext
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("1","iphone","Mobile",1,100000,pincode))
				.expectNext(new ProductDto("2","samsung","Mobile",1,10000,pincode))
				.verifyComplete();
	}


	@Test
	public void getProductByIdTest()
	{
		List<Integer> pincode = new ArrayList<Integer>();

		// Initialize an ArrayList with add()
		pincode.add(1234);
		pincode.add(12345);
		pincode.add(123456);

		Mono<ProductDto> productDtoMono=Mono.just(new ProductDto("1","samsung","Mobile",1,10000,pincode));

				//for testing purpose we are saying when i pass any() id then the mentioned productDtoMono
		//should be returned
		when(service.getProductById(any())).thenReturn(productDtoMono);

		//so we can pass any id
		Flux<ProductDto> responseBody=client.get().uri("/products/getProductById/1")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		//just verifying one field
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getName().equals("samsung"))
				.verifyComplete();
	}


	@Test
	public void getProductByNameTest()
	{
		List<Integer> pincode = new ArrayList<Integer>();

		// Initialize an ArrayList with add()
		pincode.add(1234);
		pincode.add(12345);
		pincode.add(123456);

		Flux<ProductDto> productDtoFlux=Flux.just(new ProductDto("1","samsung","Mobile",1,10000,pincode));

				//for testing purpose we are saying when i pass any() id then the mentioned productDtoMono
		//should be returned
		when(service.getProductByName("samsung")).thenReturn(productDtoFlux);

		//so we can pass any id
		Flux<ProductDto> responseBody=client.get().uri("/products/getProductByName/samsung")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		//just verifying one field
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getCategory().equals("Mobile"))
				.verifyComplete();
	}

	@Test
	public void getProductByCatgeoryTest()
	{
		List<Integer> pincode = new ArrayList<Integer>();

		// Initialize an ArrayList with add()
		pincode.add(1234);
		pincode.add(12345);
		pincode.add(123456);

		Flux<ProductDto> productDtoFlux=Flux.just(new ProductDto("1","samsung","Mobile",1,10000,pincode),

				new ProductDto("1","iphone","Mobile",1,80000,pincode));

				//for testing purpose we are saying when i pass any() id then the mentioned productDtoMono
		//should be returned
		when(service.getProductByCategory("Mobile")).thenReturn(productDtoFlux);

		//so we can pass any id
		Flux<ProductDto> responseBody=client.get().uri("/products/getProductByCategory/Mobile")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		//just verifying one field
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getName().equals("samsung"))
				.expectNextMatches(p->p.getName().equals("iphone"))
				.verifyComplete();
	}
	/*
	@Test
	public void getProductByPriceRangeTest()
	{
		List<Integer> pincode = new ArrayList<Integer>();

		// Initialize an ArrayList with add()
		pincode.add(1234);
		pincode.add(12345);
		pincode.add(123456);

		Flux<ProductDto> productDtoFlux=Flux.just(new ProductDto("1","samsung","Mobile",1,10000,pincode),
				new ProductDto("1","samsung","tv",1,100000,pincode),
				new ProductDto("1","iphone","tv",1,80000,pincode));

				//for testing purpose we are saying when i pass any() id then the mentioned productDtoMono
		//should be returned
		when(service.getProductsWithinPriceRange(10000.0,90000.0)).thenReturn(productDtoFlux);
		Map<Object,String> map=new HashMap<>();
		map.put(new Object("min"="10000","max"="90000"));
		//so we can pass any id
		Flux<ProductDto> responseBody=client.get().uri("/products/getProductsWithinPriceRange/")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		//just verifying one field
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getName().equals("samsung"))
				.expectNextMatches(p->p.getName().equals("iphone"))
				.verifyComplete();
	}
*/

	@Test
	public void deleteProductTest() {
		given(service.deleteProductById(any())).willReturn(Mono.empty());
		client.delete().uri("/products/deleteProduct/1")
				.exchange()
				.expectStatus()
				.isOk();

	}
	@Test
	public void addProductsTest()
	{
		List<Integer> pincode = new ArrayList<Integer>();

		// Initialize an ArrayList with add()
		pincode.add(1234);
		pincode.add(12345);
		pincode.add(123456);


		ProductDto productDto=new ProductDto("1","samsung","Mobile",1,10000,pincode);
		when(service.saveProduct(productDto)).thenReturn(Mono.just(new ResponseEntity<>(HttpStatus.OK)));

		client.post().uri("/products/saveProduct")
				.body(Mono.just(productDto), ProductDto.class)
				.exchange()
				.expectStatus()
				.isOk();
	}
	@Test
	public void updateProductTest()
	{List<Integer> pincode = new ArrayList<Integer>();

		// Initialize an ArrayList with add()
		pincode.add(1234);
		pincode.add(12345);
		pincode.add(123456);
		Mono<ProductDto> productDtoMono=Mono.just(new ProductDto("1","samsung","Mobile",1,10000,pincode));

		when(service.updateProductById(productDtoMono,"1")).thenReturn(Mono.just(new ResponseEntity<>(HttpStatus.OK)));

		client.put().uri("/products/updateProduct/1")
				.body(Mono.just(productDtoMono), ProductDto.class)
				.exchange()
				.expectStatus()
				.isOk();
	}
}
