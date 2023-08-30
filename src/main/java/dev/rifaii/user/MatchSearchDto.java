package dev.rifaii.user;

import dev.rifaii.activity.Activity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class MatchSearchDto {

    private Long userDiscordId;
    private Short age;
    private Activity activity;
    private Instant timestamp;

}
