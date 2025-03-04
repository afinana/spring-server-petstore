package net.petstore.api;

@jakarta.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")

public class NotFoundException extends ApiException {
    private final int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
