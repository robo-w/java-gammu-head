package rwi.sms.broker.gammu.head.message.sender.gammu;

import com.google.common.collect.ImmutableList;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwi.sms.broker.gammu.head.configuration.GammuSmsdConfiguration;
import rwi.sms.broker.gammu.head.controller.send.ValidatingTextMessageFactory;
import rwi.sms.broker.gammu.head.message.data.PhoneNumber;
import rwi.sms.broker.gammu.head.message.data.TextMessage;
import rwi.sms.broker.gammu.head.message.sender.SendTextMessageResult;
import rwi.sms.broker.gammu.head.message.sender.TextMessageSender;

import javax.annotation.concurrent.NotThreadSafe;
import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@NotThreadSafe
public class GammuTextMessageSender implements TextMessageSender {
    private static final Logger LOG = LoggerFactory.getLogger(GammuTextMessageSender.class);

    private final GammuSmsdConfiguration configuration;

    @Inject
    public GammuTextMessageSender(final GammuSmsdConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SendTextMessageResult sendTextMessage(final PhoneNumber target, final String messageContent) {
        SendTextMessageResult result;

        ProcessBuilder processBuilder = createProcessBuilder(target);

        try {
            Process process = processBuilder.start();
            InputStream standardOutputStream = new BufferedInputStream(process.getInputStream());

            OutputStream standardInput = new BufferedOutputStream(process.getOutputStream());
            standardInput.write(messageContent.getBytes(StandardCharsets.UTF_8));
            standardInput.flush();
            standardInput.close();

            result = waitForFinishedProcess(process, standardOutputStream);
        } catch (IOException e) {
            LOG.error("IO Exception on starting gammu-smsd-inject process. Is gammu SMSD installed?");
            LOG.debug("Underyling IO Exception", e);
            result = SendTextMessageResult.PROVIDER_NOT_FOUND;
        } catch (InterruptedException e) {
            LOG.warn("Waiting for process was interrupted.");
            LOG.debug("Underlying interrupted exception", e);
            result = SendTextMessageResult.COMMUNICATION_ERROR;
        }


        return result;
    }

    private SendTextMessageResult waitForFinishedProcess(
            final Process process,
            final InputStream standardOutputStream) throws IOException, InterruptedException {
        SendTextMessageResult result;

        List<String> standardOutput = IOUtils.readLines(standardOutputStream, StandardCharsets.UTF_8);
        boolean processExited = process.waitFor(10, TimeUnit.SECONDS);
        if (processExited) {
            int returnCode = process.exitValue();
            if (returnCode != 0) {
                LOG.info("Process finished with error #{}.", returnCode);
                result = SendTextMessageResult.COMMUNICATION_ERROR;
            } else {
                result = SendTextMessageResult.SUCCESS;
            }
        } else {
            LOG.warn("Process did not finish in time.");
            process.destroyForcibly();
            result = SendTextMessageResult.COMMAND_TIMED_OUT;
        }

        LOG.debug("Output of gammu-smsd-inject process: {}", standardOutput);
        return result;
    }

    private ProcessBuilder createProcessBuilder(final PhoneNumber target) {
        ImmutableList<String> commandArguments = ImmutableList.of(
                this.configuration.getMessageInjectCommand(),
                "TEXT",
                target.getNumber(),
                "-autolen",
                ValidatingTextMessageFactory.MAXIMUM_MESSAGE_CONTENT_LENGTH + "");
        ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
        processBuilder.redirectErrorStream(true);
        return processBuilder;
    }
}
