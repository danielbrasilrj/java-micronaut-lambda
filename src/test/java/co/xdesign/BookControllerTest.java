package co.xdesign;
import co.xdesign.web.rest.model.Book;
import co.xdesign.web.rest.model.BookSaved;
import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import org.junit.jupiter.api.Test;

class BookControllerTest {

    private static MicronautLambdaHandler handler;
    private static final Context lambdaContext = new MockLambdaContext();
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setupSpec() {
        try {
            handler = new MicronautLambdaHandler();
            objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);

        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanupSpec() {
        handler.getApplicationContext().close();
    }

    @Test
    void testGetBook() throws JsonProcessingException {
        AwsProxyRequest request = new AwsProxyRequestBuilder("/books", HttpMethod.GET.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        Assertions.assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
        Book book = objectMapper.readValue(response.getBody(), Book.class);
        Assertions.assertEquals("Mock Book", book.getName());
    }

    @Test
    void testSaveBook() throws JsonProcessingException {
        Book book = new Book();
        book.setName("Building Microservices");
        String json = objectMapper.writeValueAsString(book);
        AwsProxyRequest request = new AwsProxyRequestBuilder("/books", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(json)
                .build();
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        Assertions.assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
        BookSaved bookSaved = objectMapper.readValue(response.getBody(), BookSaved.class);
        Assertions.assertEquals(bookSaved.getName(), book.getName());
        Assertions.assertNotNull(bookSaved.getIsbn());
    }
}
