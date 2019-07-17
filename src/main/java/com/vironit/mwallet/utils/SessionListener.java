package com.vironit.mwallet.utils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * by implementation of HttpSessionListener interface
 * and adding in ServletContainerConfig
 * we are able to manage each just created session in application
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(30); // setting inactivity timeout
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}