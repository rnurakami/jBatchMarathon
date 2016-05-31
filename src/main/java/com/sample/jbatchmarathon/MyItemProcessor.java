package com.sample.jbatchmarathon;

import com.sample.jbatchmarathon.domain.Runner;
import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * jBatchを用いる主処理です。
 * 
 * @author akira_abe
 */
@Named
@Dependent
public class MyItemProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object obj) throws Exception {
        Runner runner = (Runner) obj;
        System.out.println("**** Processor processItem : " + runner);
        return runner.createOutputData();
    }
}
