package be.atbash.ee.security.rest.step3.logging;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class LoggingWriterInterceptor implements WriterInterceptor {

    private void logHeaders(Map<String, String> headers) {
        LoggingBean loggingBean = CDI.current().select(LoggingBean.class).get();
        loggingBean.logHeaders(headers);

    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        context.proceed();

        Map<String, String> headers = new HashMap<>();
        MultivaluedMap<String, Object> headersMap = context.getHeaders();
        headersMap.forEach((key, value) ->
                headers.put(key, value.toString())
        );
        logHeaders(headers);

    }
}
