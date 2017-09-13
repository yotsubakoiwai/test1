package util;

import java.time.Duration;
import java.time.Instant;

/**
 * @author Mike
 *
 * Uses JAVA8 time services
 */
public class ElapsedTime {
	
	Instant startTime;
	Instant endTime;

	public void start() {
		startTime = Instant.now();
	}
	
	public void stop() {
		endTime = Instant.now();

	}
	
	public long getElapsedTimeNano() {
		verifyState();
		return Duration.between(startTime, endTime).toNanos();
	}
	
	public long getElapsedTimeMilliseconds() {
		verifyState();
		return Duration.between(startTime, endTime).toMillis();
	}
	
	public long getElapsedTimeSeconds() {
		verifyState();
		return Duration.between(startTime, endTime).getSeconds();
	}
	
	private void verifyState() {
		if (startTime == null || endTime == null) {
			throw new IllegalStateException("Both start() and stop() methods must be invoked before getting elapsed time");
		}
	}
}
