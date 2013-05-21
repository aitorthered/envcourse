package scrumcourse.env;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CourseTest {

	@Test public void createSimpleClass() throws Exception {
		Course course = new Course("maths");
		assertEquals("maths", course.getName());
	}

	// Needs env.college environment property with college name string
	// This test needs to be run with -Denv.college=Standford
	@Test public void collegeName() throws Exception {
		Course course = new Course("maths");
		assertEquals("Standford", course.getCollege());
	}

	// A Short course has length less than 2 hours
	@Test public void shortCourse() throws Exception {
		Course course = new Course("maths", new TimerMockup(1L*60*60*1000*1000*1000 + 2L*60*1000*1000*1000));
		course.start();
		course.end();
		assertTrue(course.isShort());
		assertTrue(course.getDurationSeconds() > 0);
		assertTrue(course.getDurationSeconds() < 2L*60*60*1000*1000*1000);
	}

	// A long course has length greater than 2 hours
	@Test public void longCourse() throws Exception {
		Course course = new Course("maths", new TimerMockup(2L*60*60*1000*1000*1000 + 2L*60*1000*1000*1000));
		course.start();
		course.end();
		System.out.println(course.getDurationSeconds());
		assertTrue(course.isLong());
	}

	void sleepSeconds(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
		}
	}
	
	private static class TimerMockup implements Timer{
		private boolean first=true;
		private long extraDuration = 0L;
		
		TimerMockup(long duration){
			this.extraDuration = duration;
		}
		@Override
		public long nanoTime() {
			if(first){
				first = false;
				return System.nanoTime();
			}
			else{
				return (System.nanoTime() + this.extraDuration);
			}
		}
		
	}
}
