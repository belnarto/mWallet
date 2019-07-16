package com.vironit.mwallet.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

/**
 * Filters are not defined in this class
 * (by overriding method getServletFilters())
 * because Spring Security defines its own filters
 * which are finally implied to http requests.
 * <p>
 * Filter for adding UTF-8 encoding defined in
 * SecurityConfig class in configure method.
 */
public class AppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * By overriding this method we set that
     * Spring MVC has to produce NoHandlerFoundException
     * when no handlers to request found.
     */
    @Override
    public void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}
