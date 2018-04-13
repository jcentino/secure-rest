package be.atbash.ee.security.rest.step3.logging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@ApplicationScoped
@Named
public class LoggingBean {

    private List<String> logText = new ArrayList<>();
    private String path;
    private Map<String, String> headers;

    public void log(String text) {
        logText.add(text);
    }

    public void logPath(String path) {

        this.path = path;
    }

    public void logHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getRequest() {
        return logText.get(0);
    }

    public String getResponse() {
        return logText.get(1);
    }

    public String getPath() {
        return path;
    }

    public List<String> getHeaderKeys() {
        return new ArrayList<>(headers.keySet());
    }

    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    public void clearLogging() {
        logText.clear();
        path = null;
        headers.clear();
    }

}
