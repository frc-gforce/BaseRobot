package frc.robot.subsystems.feed;

public record FeedConstants(
        String logPath,
        String speedLogPath
) {
    public FeedConstants(String logPath) {
        this(
                logPath,
                logPath + "/Speed"
        );
    }
}
