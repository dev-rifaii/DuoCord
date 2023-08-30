package dev.rifaii.user;

import dev.rifaii.activity.Activity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

@Data
@Accessors(chain = true)
public class User {

    @NotNull
    private Long discordId;
    @NotNull
    private String discordName;
    @NotNull
    private String discordHandle;
    @NotNull
    private String discordUsername;
    @Min(value = 12, message = "Minimum age is 12")
    @Max(value = 120, message = "Maximum age is 120")
    private Short age;
    @NotBlank(message = "About me can't be blank")
    private String aboutTheUser;
    private String discordAvatarUrl;
    @NotNull
    private Activity activity;
    private List<String> labels;
    private Instant entryTime;

}
