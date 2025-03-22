package br.com.alurafood.payments.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info=@Info(title="API OF PAYMENT",
        description="Microsservice of payment",
        version="v1",
        license=@License(name="MIT", url="http://localhost")))
public class SwaggerConfig {
}
