package com.ocsoares.advancedcrudspringboot.main.config;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.MessageAndStatusCodeResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI().info(
                        new Info().title("COLOCAR TÍTULO....").version("1.0.0").description("COLOCAR DESCRIÇÃO..."))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", this.securityScheme()));
    }

    // Padronizando as Responses para NÃO ter que ficar REPETINDO no Código de cada Documentação de Rota!!!
    // OBS: Mas isso SETA EM TODAS as Rotas Documentadas!!!!
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        ResolvedSchema errorResponseSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(MessageAndStatusCodeResponse.class));
        Content content = new Content().addMediaType("application/json",
                new MediaType().schema(errorResponseSchema.schema)
        );
        return openApi -> openApi.getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperations()
                        .forEach(operation -> operation.getResponses()
                                .addApiResponse("400", new ApiResponse().description("Bad Request"))
                                .addApiResponse("500",
                                        new ApiResponse().description("Internal Server Error").content(content)
                                )));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
    }
}
