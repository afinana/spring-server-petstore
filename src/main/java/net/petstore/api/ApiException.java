package net.petstore.api;

@jakarta.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-05-06T17:38:50.285Z")

public class ApiException extends Exception{
    private final int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
