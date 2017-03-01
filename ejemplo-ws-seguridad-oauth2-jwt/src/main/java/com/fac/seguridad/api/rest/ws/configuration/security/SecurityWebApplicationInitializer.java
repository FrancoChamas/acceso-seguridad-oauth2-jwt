package com.fac.seguridad.api.rest.ws.configuration.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Franco Antonio Chamás. Inicializador de del contexto de seguridad,
 *         registra el springSecurityFilterChain
 * 
 *         <filter> <filter-name>springSecurityFilterChain</filter-name>
 *         <filter-class>org.springframework.web.filter.DelegatingFilterProxy
 *         </filter-class> </filter>
 * 
 *         <filter-mapping> <filter-name>springSecurityFilterChain</filter-name>
 *         <url-pattern>/*</url-pattern> </filter-mapping>
 * 
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
