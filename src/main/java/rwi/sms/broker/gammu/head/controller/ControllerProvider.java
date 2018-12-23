package rwi.sms.broker.gammu.head.controller;

import com.google.common.collect.ImmutableList;
import rwi.sms.broker.gammu.head.controller.send.SendTextMessageController;
import rwi.sms.broker.gammu.head.controller.status.WelcomeController;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;

public class ControllerProvider implements Provider<List<SparkController>> {
    private final WelcomeController welcomeController;
    private final SendTextMessageController sendTextMessageController;

    @Inject
    public ControllerProvider(
            final WelcomeController welcomeController,
            final SendTextMessageController sendTextMessageController) {
        this.welcomeController = welcomeController;
        this.sendTextMessageController = sendTextMessageController;
    }

    @Override
    public List<SparkController> get() {
        return ImmutableList.of(
                this.welcomeController,
                this.sendTextMessageController
        );
    }
}
