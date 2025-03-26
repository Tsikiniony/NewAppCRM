package crm.com.CRM.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;

@Configuration
public class JacksonConfig {

    private static class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {
        public CustomLocalDateDeserializer() {
            super(LocalDate.class);
        }

        @Override
        public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String dateStr = parser.getText();
            try {
                // Essayer de parser comme LocalDateTime
                if (dateStr.contains("T")) {
                    return LocalDateTime.parse(dateStr.replace(".000000Z", ""), 
                            DateTimeFormatter.ISO_DATE_TIME).toLocalDate();
                }
                // Essayer de parser comme date et heure standard
                if (dateStr.contains(" ")) {
                    return LocalDateTime.parse(dateStr, 
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
                }
                // Essayer de parser comme date simple
                return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException e) {
                // En cas d'erreur, extraire juste la partie date
                try {
                    return LocalDate.parse(dateStr.split("[T ]")[0]);
                } catch (Exception ex) {
                    throw new IOException("Cannot parse date: " + dateStr);
                }
            }
        }
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());
        
        return Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);
        
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        
        return builder
            .requestFactory(() -> factory)
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(10))
            .additionalMessageConverters(
                new StringHttpMessageConverter(StandardCharsets.UTF_8),
                converter
            )
            .build();
    }
}