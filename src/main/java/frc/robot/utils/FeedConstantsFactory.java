package frc.robot.utils;

import frc.robot.subsystems.feed.FeedConstants;

public class FeedConstantsFactory {
    public static FeedConstants createConstants() {
        return new FeedConstants("Robot/Feed");
    }
}
