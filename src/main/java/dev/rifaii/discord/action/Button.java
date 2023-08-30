package dev.rifaii.discord.action;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;

import java.security.InvalidParameterException;
import java.util.Arrays;

@Getter
public enum Button {

    SKIP("dc-skip", "Skip"),
    EXIT("dc-exit", "Exit");

    private final String id;

    private final String label;


    Button(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public static boolean isDuoCordButton(String id) {
        return Arrays.stream(Button.values())
                .anyMatch(button -> button.getId().equals(id));
    }

    public static Button getById(String id) {
        return Arrays.stream(Button.values())
                .filter(button -> button.getId().equals(id))
                .findFirst()
                .orElseThrow(InvalidParameterException::new);
    }

    public static net.dv8tion.jda.api.interactions.components.buttons.Button buildExitButton() {
        return new ButtonImpl(EXIT.getId(), EXIT.getLabel(), ButtonStyle.DANGER, false, null);
    }

    public static net.dv8tion.jda.api.interactions.components.buttons.Button buildSkipButton() {
        return new ButtonImpl(SKIP.getId(), SKIP.getLabel(), ButtonStyle.SECONDARY, false, null);
    }

}
