package rwi.sms.broker.gammu.head.startup;

public interface WebServerStarter extends AutoCloseable {
    void startWebserver();
}
