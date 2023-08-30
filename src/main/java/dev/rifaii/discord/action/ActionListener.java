package dev.rifaii.discord.action;

import dev.rifaii.user.UserQueueService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
public class ActionListener extends ListenerAdapter {

    private final ActionHandlerSupplier actionHandlerSupplier;


    public ActionListener(UserQueueService userQueueService) {
        this.actionHandlerSupplier = new ActionHandlerSupplier(userQueueService);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        if (!Button.isDuoCordButton(buttonId)) {
            log.warn(String.format("Unknown button triggered this listener with id: %s", buttonId));
            return;
        }

        actionHandlerSupplier.getHandlerForButton(Button.getById(buttonId))
                .handle(event);
    }
}
