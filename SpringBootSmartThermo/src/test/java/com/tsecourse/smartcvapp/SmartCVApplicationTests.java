package com.tsecourse.smartcvapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tsecourse.smartcvapp.services.AppMemService;
import com.tsecourse.smartcvapp.services.TranslatorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartCVApplicationTests {

    @MockBean
    private AppMemService appMem;
    
    @MockBean
    private CVState cvState;
    
	@Autowired
    TranslatorService translator;

	/* ----------------------------- ?????? */

	@Test
	public void testTranslatorServiceCommands() {
		String retStr = null;
		retStr = translator.buildMessage("connect", "3333333");
		assertEquals("$CV-CONNECT-$-3333333\n", retStr);
		retStr = translator.buildMessage("stat", "");
		assertEquals("$CV-STAT?\n", retStr);
		retStr = translator.buildMessage("on", "");
		assertEquals("$CV-ACT-$50$70\n", retStr);
		retStr = translator.buildMessage("off", "");
		assertEquals("$CV-ACT-$0$0\n", retStr);
	}
		
	@Test
	public void testTranslatorService() {
		CVState cvStateNoMock = new CVState();
		String lastData = "#STAT#150#14.88#10.10#1#1525682758#0.00#1525683005";
		translator.processData(cvStateNoMock, lastData);
	    assertEquals(1.5, cvStateNoMock.getPressure(), 0.0);
	    assertEquals(14.88, cvStateNoMock.getTempRoom(), 0.0);
	}
	
	@Test
	public void testTranslatorServiceMock() {
		String lastData = "#STAT#150#14.88#10.10#1#1525682758#0.00#1525683005";
		translator.processData(cvState, lastData);
        Mockito.when(cvState.getPressure()).thenReturn(1.5);
	    assertEquals(1.5, cvState.getPressure(), 0.2);
	}
	
	/* ----------------------------- appmem */
	
	@Test
	public void testWithMockito() {
        Mockito.when(appMem.retrieveData("settemp.txt")).thenReturn("fake data");
        assertThat(appMem.retrieveData("settemp.txt")).isEqualTo("fake data");
	}
	
	/* ----------------------------- spring boot context */

	@Test
	public void contextLoads() {
	}

}
