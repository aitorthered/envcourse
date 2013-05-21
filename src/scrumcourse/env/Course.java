package scrumcourse.env;

public class Course {

	private static final long NANOS_PER_SECONDS = 1000000000L;

	private static final long SECONDS_PER_HOUR = 3600;

	private final String name;

	private long startTime, endTime;

	private long durationSeconds;
	
	private Timer myTimer;
	private EnvProperties myProperties;

	public Course(String name, Timer myNewTimer, EnvProperties myProperties) {
		this.name = name;
		this.myTimer = myNewTimer;
		this.myProperties = myProperties;
	}
	public Course(String name, Timer myNewTimer) {
		this(name, myNewTimer, new EnvPropertiesImpl());
	}
	public Course(String name, EnvProperties myProperties) {
		this(name, new TimerImpl(), myProperties );
	}

	public Course(String name) {
		this(name, new TimerImpl(), new EnvPropertiesImpl());
	}

	public String getName() {
		return name;
	}

	public String getCollege() {
		return myProperties.getProperty("env.college");
	}

	public void start() {
		startTime = this.myTimer.nanoTime();
	}

	public void end() {
		endTime = this.myTimer.nanoTime();
		durationSeconds = (endTime - startTime) / NANOS_PER_SECONDS;
	}

	public boolean isShort() {
		return durationSeconds < 2 * SECONDS_PER_HOUR;
	}

	public boolean isLong() {
		return !isShort();
	}

	public long getDurationSeconds() {
		return durationSeconds;
	}
	
	private static class TimerImpl implements Timer{

		@Override
		public long nanoTime() {
			return System.nanoTime();
		}
		
	}

	private static class EnvPropertiesImpl implements EnvProperties{

		@Override
		public String getProperty(String property) {
			return System.getProperty(property);
		}

		
	}
}
