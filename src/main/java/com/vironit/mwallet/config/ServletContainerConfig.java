package com.vironit.mwallet.config;

import com.vironit.mwallet.utils.SessionListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;

/**
 * Filters are not defined in this class
 * (by overriding method getServletFilters())
 * because Spring Security defines its own filters
 * which are finally implied to http requests.
 * <p>
 * Filter for adding UTF-8 encoding defined in
 * SecurityConfig class in configure method.
 */
public class ServletContainerConfig
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Load persistence and spring security configuration
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, PersistenceConfig.class};
    }

    // Load spring web configuration
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

    /**
     * To apply session management on Startup
     * https://dzone.com/articles/spring-java-configuration
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        super.onStartup(servletContext);

        servletContext.addListener(new SessionListener());

        // Prevent using URL Parameters for Session Tracking
        // https://www.baeldung.com/spring-security-session
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));

        // Secure Session Cookie
        // httpOnly: if true then browser script won’t be able to access the cookie
        // secure: if true then the cookie will be sent only over HTTPS connection
        // https://www.baeldung.com/spring-security-session
        servletContext.getSessionCookieConfig().setHttpOnly(true);
        servletContext.getSessionCookieConfig().setSecure(false);

    }
}
