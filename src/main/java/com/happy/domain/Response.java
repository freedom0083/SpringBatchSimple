package com.happy.domain;


import java.sql.Timestamp;

public class Response {
    private long id;
    private Timestamp insertTime;
    private String returnCode;
    private int ddFlag = 1;
    private String ddFlagBatch = "1";
    private String ddSign = "1";
    private String resultInfo;
    private long sourceRowIdx = 1;
    private int idCategory = 1;
    private int pairedToDdRequest = 1;

    public Response() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public int getDdFlag() {
        return ddFlag;
    }

    public void setDdFlag(int ddFlag) {
        this.ddFlag = ddFlag;
    }

    public String getDdFlagBatch() {
        return ddFlagBatch;
    }

    public void setDdFlagBatch(String ddFlagBatch) {
        this.ddFlagBatch = ddFlagBatch;
    }

    public String getDdSign() {
        return ddSign;
    }

    public void setDdSign(String ddSign) {
        this.ddSign = ddSign;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public long getSourceRowIdx() {
        return sourceRowIdx;
    }

    public void setSourceRowIdx(long sourceRowIdx) {
        this.sourceRowIdx = sourceRowIdx;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getPairedToDdRequest() {
        return pairedToDdRequest;
    }

    public void setPairedToDdRequest(int pairedToDdRequest) {
        this.pairedToDdRequest = pairedToDdRequest;
    }
}
