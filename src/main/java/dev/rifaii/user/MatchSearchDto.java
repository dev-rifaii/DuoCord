package dev.rifaii.user;

import dev.rifaii.activity.Activity;
import lombok.Data;

@Data
public class MatchSearchDto {

    private Long userDiscordId;
    private Short age;
    private Activity activity;

}
