package com.stemapp;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jamie on 27-3-2016.
 */
public class ClientFilter implements Filter {

    public static final String[] allowedExtensions =
    {
        // Basic files
        "html", "css", "js", "map", "json",

        // Images
        "png", "jpg", "gif", "svg",

        // Fonts
        "eot", "ttf", "woff", "woff2"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(shouldRedirect(request.getRequestURI())) {
            response.sendRedirect("/");
        }

        else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean shouldRedirect(String uri) {
        return !uri.equals("")
                && !uri.equals("/")
                && !uri.startsWith("/api")
                && !isAllowedExtension(uri);
    }

    private boolean isAllowedExtension(String uri) {
        for(String extension : allowedExtensions) {
            if(uri.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void destroy() {

    }
}
