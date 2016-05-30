package com.sample.jbatchmarathon;

import com.sample.jbatchmarathon.domain.Runner;
import java.io.BufferedReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

/**
 *
 * @author akira_abe
 */
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
        String dirName = jobContext.getProperties().getProperty("dir");
        String fileName = jobContext.getProperties().getProperty("input_file");
        br = Files.newBufferedReader(Paths.get(dirName, fileName), StandardCharsets.UTF_8);
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

            // TODO:判定条件は正しくはトレーラーレコードが来たらとするべきか？
            if (null == line || !prevLine.substring(4, 7).equals(line.substring(4, 7))) {
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
        Runner vo = new Runner();
        return vo;
    }

}
