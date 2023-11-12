package dev.rifaii.discord.action;

import dev.rifaii.user.UserQueueService;

import java.util.ArrayList;
import java.util.List;

public class ActionHandlerSupplier {

    private final List<ActionHandler> definedHandlers;

    public ActionHandlerSupplier(UserQueueService userQueueService) {
        this.definedHandlers = new ArrayList<>();
        this.definedHandlers.add(new SkipButtonHandler(userQueueService));
        this.definedHandlers.add(new ExitButtonHandler(userQueueService));
    }

    public ActionHandler getHandlerForButton(Button button) {
        return definedHandlers.stream()
                .filter(handler -> handler.supportedButton().equals(button))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
