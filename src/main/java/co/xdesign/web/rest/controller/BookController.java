package co.xdesign.web.rest.controller;

import co.xdesign.web.rest.model.Book;
import co.xdesign.web.rest.model.BookSaved;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponseFactory;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Controller("/books")
public class BookController {

    @Get
    @Operation(operationId = "get-book",
               summary = "Get a Book",
               description = "Return a Book mock for test purpose")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON,
                 schema = @Schema(type="string")))
    @ApiResponse(responseCode = "200", description = "Get a book successfully")
    @Tag(name = "Book")
    public Book getBook() {
        var book = new Book();
        book.setName("Mock Book");
        return book;
    }

    @Post
    @Operation(operationId = "save-book",
               summary = "Save a Book",
               description = "Save a book and return the book saved"
    )
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON,
                 schema = @Schema(type="string"))
    )
    @ApiResponse(responseCode = "200", description = "Saved the book successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Name Supplied")
    @Tag(name = "Book")
    public MutableHttpResponse<BookSaved> saveBook(@Valid @NotBlank @Body Book book) {
        if(StringUtils.isEmpty(book.getName())) {
            return HttpResponseFactory.INSTANCE.status(HttpStatus.BAD_REQUEST);
        }

        var bookSaved = new BookSaved();
        bookSaved.setName(book.getName());
        bookSaved.setIsbn(UUID.randomUUID().toString());
        return HttpResponseFactory.INSTANCE.status(HttpStatus.OK)
                .body(bookSaved);
    }
}