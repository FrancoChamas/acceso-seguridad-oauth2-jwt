package com.fac.seguridad.api.rest.ws.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import com.fac.seguridad.api.rest.tocken.oauth2.accesoseguridadoauth2jwt.configuracion.ConfigWebSecurity;
import com.fac.seguridad.api.rest.tocken.oauth2.accesoseguridadoauth2jwt.manager.AccesoAuthenticationManager;
import com.fac.seguridad.api.rest.tocken.oauth2.accesoseguridadoauth2jwt.provider.DefaultAccesoAuthenticationProvider;

/**
 * @author Franco Antonio Chmas. 
 * clase analoga al xml de configuracion de spring security applicationContext-security.xml
 * esta clase se toma como centro de configuracion de spring security y configuracion del 
 * autorizador.
 *
 */
@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter implements ConfigWebSecurity {
	//TODO debería salir de algun archivo de configuracion
	private final String signingKeyJwt = "123";
	private final String RESOURCE_ID = "my_rest_api";
	
	@Autowired
	@Qualifier("defaultAccesoAuthenticationProvider")
	private DefaultAccesoAuthenticationProvider authenticationProvider;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.anonymous().disable()
	  	.authorizeRequests()
	  	.antMatchers("/oauth/token").permitAll();
    }
	
    @Bean(name = "defaultAccesoAuthenticationProvider")
    @Primary
    public DefaultAccesoAuthenticationProvider defaultAccesoAuthenticationProvider() {
    	return new DefaultAccesoAuthenticationProvider();
    }
    
    @Bean(name = "accesoAuthenticationManager")
    @Primary
    public AccesoAuthenticationManager authenticationManager() {
    	return new AccesoAuthenticationManager(authenticationProvider);
    }

	@Override
	public void configureAuthorizationServer (ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("my-trusted-client")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write", "trust").secret("secret")
				.accessTokenValiditySeconds(920).
				refreshTokenValiditySeconds(600);
		
	}

	@Override
	public String getSigningKey() {
		return this.signingKeyJwt;
	}
	
	@Override
	public String getResourceId() {
		return RESOURCE_ID;
	}

	@Override
	public void configureResourceServer(HttpSecurity http) throws Exception {
		http.anonymous().disable().requestMatchers().antMatchers("/user/**").and().authorizeRequests()
		.antMatchers("/user/**").access("hasRole('ADMIN')").and().exceptionHandling()
		.accessDeniedHandler(new OAuth2AccessDeniedHandler());
		
	}

}
