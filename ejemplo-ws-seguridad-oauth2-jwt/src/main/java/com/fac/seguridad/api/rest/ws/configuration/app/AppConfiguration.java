package com.fac.seguridad.api.rest.ws.configuration.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Franco Antonio chamas
 * clase con la anotacion @EnableWebMvc, analogo al application-servlet-xml o contexto
 * para configurar el InternalResourceViewResolver es necesario extender de extends WebMvcConfigurerAdapter	
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.fac.seguridad.api.rest.*")
public class AppConfiguration {
	

}