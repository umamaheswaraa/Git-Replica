package com.imaginea.gr.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class UtilitiesTest {

	@Test
	public void getDateDiff(){
		long time = (long)1380092753*1000;
		System.out.println(time);
		String days = Utilities.dateDiff(System.currentTimeMillis(),time );
		System.out.println("days: "+days);
	}
}
