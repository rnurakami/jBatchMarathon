package com.sample.jbatchmarathon.domain;

/**
 * 出力データを保持します。
 * 
 * @author akira_abe
 */
public class OutputData {
    
    /**
     * アウトプットデータのコンテンツです。
     */
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "OutputData{" + "contents=" + contents + '}';
    }
}
