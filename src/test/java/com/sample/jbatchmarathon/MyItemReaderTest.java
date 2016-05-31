package com.sample.jbatchmarathon;

import com.sample.jbatchmarathon.domain.Runner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.runtime.context.JobContext;
import static mockit.Deencapsulation.setField;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.integration.junit4.JMockit;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;


/**
 * MyItemReaderのテストクラスです。
 * <pre>
 * ReaderはJobContextに依存するため、モック（JMockit）を利用しています。
 * </pre>
 *
 * @author akira_abe
 */
@RunWith(JMockit.class)
public class MyItemReaderTest {

    // Testeeが依存するJobContextをモック化します。
    @Mocked
    private JobContext jobContext;

    public MyItemReaderTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * MyItemReaderのテストメソッドです。
     */
    @Test
    public void testRead() {

        // Testeeをインスタンス化します。
        MyItemReader reader = new MyItemReader();

        // Mockが返却する値を設定します。
        final String fileName = "c:/temp/input.csv";
        
        // Mockの振る舞いを記述します。
        new NonStrictExpectations() {
            {
                jobContext.getProperties().getProperty("input_file");
                result = fileName;
            }
        };
        // privateフィールドへモックを差し込みます。
        setField(reader, jobContext);
        
        // ファイルをOpenします。
        try {
            reader.open("100");
        } catch (Exception ex) {
            Logger.getLogger(MyItemReaderTest.class.getName()).log(Level.SEVERE, "★★★ file open エラーが発生しました。", ex);
        }

        // ファイルをReadします。
        try {
            // 試作品なので大雑把にassertしています。
            Runner run1 = (Runner) reader.readItem();
            assertThat(run1.getName(),is("akira_abe"));
            assertThat(run1.getRaces().size(),is(2));
            System.out.println(run1);
            
            Runner run2 = (Runner) reader.readItem();
            assertThat(run2.getName(),is("Haile Gebrselassie"));
            assertThat(run2.getRaces().get(0).getResult(),is("02h04m26s"));
            System.out.println(run2);
            
            Runner run3 = (Runner) reader.readItem();
            assertThat(run3, nullValue());
            System.out.println(run3);

        } catch (Exception ex) {
            Logger.getLogger(MyItemReaderTest.class.getName()).log(Level.SEVERE, "★★★ readエラーが発生しました。", ex);
        }

        // ファイルをCloseします。
        try {
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(MyItemReaderTest.class.getName()).log(Level.SEVERE, "★★★ file close エラーが発生しました。", ex);
        }
        
    }
}
