package com.sample.jbatchmarathon;

import com.sample.jbatchmarathon.domain.OutputData;
import com.sample.jbatchmarathon.domain.Runner;
import java.util.Arrays;
import java.util.List;
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
 * MyItemWriterのテストクラスです。
 * <pre>
 * WriterはJobContextに依存するため、モック（JMockit）を利用しています。
 * </pre>
 *
 * @author akira_abe
 */
@RunWith(JMockit.class)
public class MyItemWriterTest {

    // Testeeが依存するJobContextをモック化します。
    @Mocked
    private JobContext jobContext;

    public MyItemWriterTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * MyItemWriterのテストメソッドです。
     */
    @Test
    public void testWrite() {

        // Testeeをインスタンス化します。
        MyItemWriter writer = new MyItemWriter();

        // Mockが返却する値を設定します。
        final String fileName = "c:/temp/test_output.txt";
        
        // Mockの振る舞いを記述します。
        new NonStrictExpectations() {
            {
                jobContext.getProperties().getProperty("output_file");
                result = fileName;
            }
        };
        // privateフィールドへモックを差し込みます。
        setField(writer, jobContext);
        
        // ファイルをOpenします。
        try {
            writer.open("100");
        } catch (Exception ex) {
            Logger.getLogger(MyItemWriterTest.class.getName()).log(Level.SEVERE, "★★★ file open エラーが発生しました。", ex);
        }

        // ファイルをWriteします。
        try {
            // 試作品とりあえず大雑把な処理です。
            OutputData data = new OutputData();
            data.setContents("test");
            List<OutputData> items = Arrays.asList(data);
            
            writer.writeItems((List<Object>) (Object) items);

        } catch (Exception ex) {
            Logger.getLogger(MyItemWriterTest.class.getName()).log(Level.SEVERE, "★★★ writeエラーが発生しました。", ex);
        }

        // ファイルをCloseします。
        try {
            writer.close();
        } catch (Exception ex) {
            Logger.getLogger(MyItemWriterTest.class.getName()).log(Level.SEVERE, "★★★ file close エラーが発生しました。", ex);
        }
        
    }
}
