package com.fac.seguridad.api.rest.ws.configuration.app;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Franco Antonio Chamas.
 * clase que representa un WebApplicationInitializer, analogo al web.xml
 *
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	private static Logger logger = LoggerFactory. getLogger(AppInitializer.class);
	
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfiguration.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override
    protected Filter[] getServletFilters() {
    	Filter [] singleton = { new CORSFilter()};
    	return singleton;
    }
    
	public void onStartup(ServletContext container) throws ServletException {
		logger.debug("Inicializando el aplicativo.");
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfiguration.class);
		
		container.setInitParameter("log4jConfigLocation", "\\WEB-INF\\classes\\log4j.properties");
		
		ctx.setServletContext(container);

		ServletRegistration.Dynamic servlet = container.addServlet(
				"dispatcher", new DispatcherServlet(ctx));

		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
 
}