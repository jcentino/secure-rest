package be.atbash.ee.security.rest.step1.logging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@ApplicationScoped
@Named
public class LoggingBean {

    private List<String> logText = new ArrayList<>();
    private String path;

    public void log(String text) {
        logText.add(text);
    }

    public void logPath(String path) {

        this.path = path;
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

    public void clearLogging() {
        logText.clear();
        path = null;
    }

}
