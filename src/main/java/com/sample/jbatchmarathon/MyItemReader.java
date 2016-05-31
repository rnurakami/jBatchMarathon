package com.sample.jbatchmarathon;

import com.sample.jbatchmarathon.domain.Race;
import com.sample.jbatchmarathon.domain.Runner;
import com.sample.jbatchmarathon.util.CalendarUtil;
import java.io.BufferedReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *ファイルのRead処理です。
 * <pre>
 * 責務としては、マルチレイアウトファイルがJavaで扱いづらいので
 * Processor以降が意識しなくても済むようにこのレイヤで吸収します。
 * </pre>
 * 
 * @author akira_abe
 */
@Named
@Dependent
public class MyItemReader implements javax.batch.api.chunk.ItemReader {

    @Inject
    private JobContext jobContext;

    private BufferedReader br;

    /**
     * 処理用のステート情報
     */
    private boolean firstRead = true;
    private boolean hasNext = true;
    private String prevLine;
    private String line;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        
        System.out.println("ItemReader open.");
        String fileName = jobContext.getProperties().getProperty("input_file");
        br = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    @Override
    public void close() throws Exception {
        System.out.println("ItemReader close.");
        br.close();
    }

    /**
     *
     * ランナー単位のデータを取得します。 データを読み切った場合には、hasNextにfalseをセットします。
     *
     * @return INPUTファイルをランナーID単位に区切ったオブジェクト
     * @throws java.lang.Exception
     */
    @Override
    public Object readItem() throws Exception {

        Runner runner = new Runner();
        if (firstRead) {
            //System.out.println("firstRead");
            line = br.readLine();
            firstRead = false;
        }

        while (true) {
            prevLine = line;

            line = br.readLine();
            runner = parse(prevLine, runner);

            if (line == null) {
                hasNext = false;
            }

            // TODO: substring(0,4)ではあまりにも意味不明なので時間があればどうにかします。
            if (null == line || !prevLine.substring(0, 4).equals(line.substring(0, 4))) {
                break;
            }
        }

        return runner;

    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        System.out.println("ItemReader checkpoint.");
        return null;
    }

    /**
     * コンポーネントに応じた適切なオブジェクトにマッピングします。
     *
     * @param line ファイルの1レコード分のString形式のオブジェクト
     * @return ファイルをランナーIDに区切ったオブジェクト
     */
    private Runner parse(String line, Runner runner) {

        if (line == null) {
            return null;
        }
        String[] values = line.split(",");
                
        if("Runner".equals(values[1])) {
            runner.setId(Integer.parseInt(values[0]));
            runner.setName(values[2]);
            runner.setBirthDay(CalendarUtil.toDate(values[3]));
        } else {
            Race race = new Race();
            race.setName(values[2]);
            race.setDate(CalendarUtil.toDate(values[3]));
            race.setDistance(Double.parseDouble(values[4]));
            race.setResult(values[5]);
            runner.add(race);
        }
        
        return runner;
    }

}
