package rwi.sms.broker.gammu.head.configuration;

import java.util.Objects;

public class WebServerConfiguration {

    private final int serverPort;
    private final String authenticationToken;

    public WebServerConfiguration(final int serverPort, final String authenticationToken) {
        this.serverPort = serverPort;
        this.authenticationToken = Objects.requireNonNull(authenticationToken);
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }
}
