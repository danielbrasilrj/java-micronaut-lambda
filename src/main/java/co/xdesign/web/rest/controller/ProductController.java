package co.xdesign.web.rest.controller;

import co.xdesign.web.rest.model.Product;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponseFactory;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotBlank;

@Controller("/products")
public class ProductController {

    @Get
    @Operation(operationId = "products",
               summary = "Get a Product",
               description = "Return a Product mock for test purpose")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON,
                 schema = @Schema(type="string")))
    @ApiResponse(responseCode = "200", description = "Get a product successfully")
    @Tag(name = "Product")
    public Product get() {
        Product product = new Product();
        product.setName("Mock Product");
        return product;
    }

    @Get("/by-name")
    @Operation(operationId = "products-by-name",
               summary = "Get product by name",
               description = "Get a product based on the informed product name"
    )
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON,
                 schema = @Schema(type="string"))
    )
    @ApiResponse(responseCode = "200", description = "Found a product successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Name Supplied")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @Tag(name = "Product")
    public MutableHttpResponse<Product> findByName(@NotBlank @QueryValue("name") String name) {
        if (StringUtils.isEmpty(name)) {
            return HttpResponseFactory.INSTANCE.status(HttpStatus.BAD_REQUEST);
        }

        Product product = new Product();
        product.setName(name);
        return HttpResponseFactory.INSTANCE.status(HttpStatus.OK)
                .body(product);
    }
}
