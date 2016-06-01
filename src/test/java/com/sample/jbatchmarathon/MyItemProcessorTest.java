/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.jbatchmarathon;

import com.sample.jbatchmarathon.domain.OutputData;
import com.sample.jbatchmarathon.domain.Race;
import com.sample.jbatchmarathon.domain.Runner;
import com.sample.jbatchmarathon.util.CalendarUtil;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsInstanceOf.*;

/**
 *
 * @author akira_abe
 */
public class MyItemProcessorTest {

    public MyItemProcessorTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * ItemProcessorのテストメソッドです。
     */
    @Test
    public void testProcessSuccess() {

        MyItemProcessor proc = new MyItemProcessor();

        // 入力のオブジェクトを生成します。
        Runner run1 = new Runner();
        run1.setId(1001);
        run1.setName("akira_abe");
        run1.setBirthDay(CalendarUtil.toDate("1968-1-5"));
        Race race11 = new Race();
        race11.setName("河口湖マラソン");
        race11.setDate(CalendarUtil.toDate("2006-11-23"));
        race11.setDistance(42.195);
        race11.setResult("04h30m00s");
        Race race12 = new Race();
        race12.setName("東京マラソン");
        race12.setDate(CalendarUtil.toDate("2007-2-10"));
        race12.setDistance(42.195);
        race12.setResult("05h00m00s");
        List<Race> races1 = Arrays.asList(race11, race12);
        run1.setRaces(races1);

        try {
            Object actual1 = proc.processItem(run1);
            
            assertThat(actual1, is(instanceOf(OutputData.class)));
            OutputData actualOutput1 = (OutputData) actual1;
            assertThat(actualOutput1.toString(), is(run1.createOutputData().toString()));

        } catch (Exception ex) {
            Logger.getLogger(MyItemProcessorTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //TODO:現状Processが特に処理をしていないので、何らかの仕様を実装したら、検証をする。
    }
}
