package frc.robot;

public enum PathPlannerAutos {
        EXAMPLE_AUTO("Example Auto");

        private final String name;

        PathPlannerAutos(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
}
