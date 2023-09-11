package dev.rifaii.user;

import dev.rifaii.activity.Activity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    @NotNull
    private Long discordId;
    @NotBlank
    private String discordName;
    @NotBlank
    private String discordHandle;
    @NotBlank
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
//    private OffsetDateTime entryTime;

}
