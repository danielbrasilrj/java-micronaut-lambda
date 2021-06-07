package co.xdesign;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Micronaut Sample API",
                version = "0.1",
                description = "Micronaut Sample API"))
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

