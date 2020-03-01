package edu.cad.filters;

import edu.cad.configuration.properties.AllowedOriginConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessControlHeaderAppendingFilter implements Filter {
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ORIGIN = "Origin";

    private final AllowedOriginConfigurationProperties properties;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;
        String origin = httpServletRequest.getHeader(ORIGIN);
        if (properties.getUrls().contains(origin)) {
            var response = (HttpServletResponse) servletResponse;
            response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", properties.getHeaders()));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
