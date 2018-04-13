package be.atbash.ee.security.rest.step3.logging;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Provider()
public class ClientLoggingFilter implements ClientRequestFilter, ClientResponseFilter, WriterInterceptor {

    private static final String ENTITY_STREAM_PROPERTY = "EntityLoggingFilter.entityStream";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final int maxEntitySize = 1024 * 8;

    private void log(String text) {

        LoggingBean loggingBean = CDI.current().select(LoggingBean.class).get();
        loggingBean.log(text);
    }

    private void logEndpoint(String path) {
        LoggingBean loggingBean = CDI.current().select(LoggingBean.class).get();
        loggingBean.logPath(path);

    }

    private void logHeaders(Map<String, String> headers) {
        LoggingBean loggingBean = CDI.current().select(LoggingBean.class).get();
        loggingBean.logHeaders(headers);

    }

    private InputStream logInboundEntity(final StringBuilder b, InputStream stream, final Charset charset) throws IOException {
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        stream.mark(maxEntitySize + 1);
        final byte[] entity = new byte[maxEntitySize + 1];
        final int entitySize = stream.read(entity);
        b.append(new String(entity, 0, Math.min(entitySize, maxEntitySize), charset));
        if (entitySize > maxEntitySize) {
            b.append("...more...");
        }
        b.append('\n');
        stream.reset();
        return stream;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        logEndpoint(requestContext.getUri().getPath());

        Map<String, String> headers = new HashMap<>();
        MultivaluedMap<String, Object> headersMap = requestContext.getHeaders();
        headersMap.forEach((key, value) ->
                headers.put(key, value.toString())
        );
        logHeaders(headers);

        if (requestContext.hasEntity()) {
            final OutputStream stream = new LoggingStream(requestContext.getEntityStream());
            requestContext.setEntityStream(stream);
            requestContext.setProperty(ENTITY_STREAM_PROPERTY, stream);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext,
                       ClientResponseContext responseContext) throws IOException {
        final StringBuilder sb = new StringBuilder();
        if (responseContext.hasEntity()) {
            responseContext.setEntityStream(logInboundEntity(sb, responseContext.getEntityStream(),
                    DEFAULT_CHARSET));
            log("Status " + responseContext.getStatus() + " : " + sb.toString());
        } else {
            log("Status " + responseContext.getStatus() + " : ");
        }

    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        final LoggingStream stream = (LoggingStream) context.getProperty(ENTITY_STREAM_PROPERTY);
        context.proceed();
        if (stream != null) {
            log(stream.getStringBuilder(DEFAULT_CHARSET).toString());
        }
    }

    private class LoggingStream extends FilterOutputStream {

        private final StringBuilder sb = new StringBuilder();
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        LoggingStream(OutputStream out) {
            super(out);
        }

        StringBuilder getStringBuilder(Charset charset) {
            // write entity to the builder
            final byte[] entity = baos.toByteArray();

            sb.append(new String(entity, 0, entity.length, charset));
            if (entity.length > maxEntitySize) {
                sb.append("...more...");
            }
            sb.append('\n');

            return sb;
        }

        @Override
        public void write(final int i) throws IOException {
            if (baos.size() <= maxEntitySize) {
                baos.write(i);
            }
            out.write(i);
        }
    }
}
