package co.xdesign.web.rest.model;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;

@Introspected
public class Book {

    @NonNull
    @NotBlank
    private String name;

    public Book() {
        name = null;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
