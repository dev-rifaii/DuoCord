package dev.rifaii.activity;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static dev.rifaii.activity.ActivityType.*;

@Getter
public enum Activity {

    OVERWATCH("Overwatch", PLAY),
    VALORANT("Valorant", PLAY),
    CSGO("Counter Strike: Global Offensive", PLAY),
    LEAGUE_OF_LEGENDS("League of Legends", PLAY),

    MOVIE("Movie", WATCH),
    SERIES("Series", WATCH),
    YOUTUBE("Youtube", WATCH),

    MUSIC("Music", LISTEN);

    public static final List<Activity> PLAYABLE_ACTIVITIES = Arrays.stream(Activity.values()).filter(activity -> activity.getActivityType().equals(PLAY)).toList();
    public static final List<Activity> WATCHABLE_ACTIVITIES = Arrays.stream(Activity.values()).filter(activity -> activity.getActivityType().equals(WATCH)).toList();
    public static final List<Activity> LISTENABLE_ACTIVITIES = Arrays.stream(Activity.values()).filter(activity -> activity.getActivityType().equals(LISTEN)).toList();

    private final String titleName;

    private final ActivityType activityType;

    Activity(String titleName, ActivityType activityType) {
        this.titleName = titleName;
        this.activityType = activityType;
    }

    public static Activity getByTitleName(String titleName) {
        return Arrays.stream(Activity.values())
                .filter(activity -> activity.getTitleName().equalsIgnoreCase(titleName))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
