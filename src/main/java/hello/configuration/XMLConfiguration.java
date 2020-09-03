package hello.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = { "classpath:applicationContext.xml" })
public class XMLConfiguration {
}
