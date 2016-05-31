package com.sample.jbatchmarathon;

import com.sample.jbatchmarathon.domain.OutputData;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import javax.batch.api.chunk.ItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 *
 * ファイルのWrite処理です。
 *
 * @author akira_abe
 */
@Named
@Dependent
public class MyItemWriter implements ItemWriter {

    @Inject
    private JobContext jobContext;

//    @Inject
//    private EntityManager em;

    private BufferedWriter bw;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        System.out.println("ItemWriter open.");
        String fn = jobContext.getProperties().getProperty("output_file");
        bw = Files.newBufferedWriter(Paths.get(fn), StandardCharsets.UTF_8);

        // DBからの値の削除です。
        // int deleteted = em.createQuery(
        //       "Delete from Test t")
        //       .executeUpdate();
        // System.out.println(deleteted + " row(s) has deleted.");
    }

    @Override
    public void close() throws Exception {
        System.out.println("ItemWriter close.");
        bw.close();
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.println("ItemWriter writeItem : " + items);
        // jBatchから受け取ったオブジェクトを種類別に振り分けて処理します。
        // write to file1
        int i = 0;
        for (Iterator<Object> it = items.iterator(); it.hasNext();) {
            OutputData data = (OutputData) it.next();

            bw.write(data.toString());
            bw.newLine();

            // DBへの永続化を試しています。（nameというフィールドは長すぎるとエラーになるのでsubstringしています。）
            // Test entity = new Test();
            // entity.setId(i++); //TODO: 複数回Writerに制御が来るとキー重複するので本来は自動生成キーにするべき。
            // entity.setName(vo.toString().substring(0, 255));
            // em.persist(entity);
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        System.out.println("ItemWriter checkpoint.");
        return null;
    }
}
