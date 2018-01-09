package com.happy.domain;

import java.util.Date;
import java.util.List;

public class Response {
    private long id;
    private Date insertTime;
    private String contractNbr;
    private long batchId;
    private long businessId;
    private String returnCode;
    private int ddFlag;
    private String ddFlagBatch;
    private String gcType;
    private long amount;
    private String ddReqSn;
    private String dealInfo;
    private String payerBankCode;
    private String payerBankReginCode;
    private String payerBankName;
    private String payerAccType;
    private String payerAccCompanyType;
    private String payerAccNo;
    private String payerAccName;
    private String payerIdCardType;
    private String payerIdCardNo;
    private Date requestDate;
    private String currencyType;
    private String ddSign;
    private String transSn;
    private String remark;
    private String sysRefCode;
    private String resultInfo;
    private long sourceRowIdx;
    private String accBankCode;
    private int idCategory;
    private int pairedToDdRequest;
    private long idDdReq;

    public Response() {
    }

    public Response(String payerAccName, String payerBankName) {
        this.payerAccName = payerAccName;
        this.payerBankName = payerBankName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getContractNbr() {
        return contractNbr;
    }

    public void setContractNbr(String contractNbr) {
        this.contractNbr = contractNbr;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
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

    public String getGcType() {
        return gcType;
    }

    public void setGcType(String gcType) {
        this.gcType = gcType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDdReqSn() {
        return ddReqSn;
    }

    public void setDdReqSn(String ddReqSn) {
        this.ddReqSn = ddReqSn;
    }

    public String getDealInfo() {
        return dealInfo;
    }

    public void setDealInfo(String dealInfo) {
        this.dealInfo = dealInfo;
    }

    public String getPayerBankCode() {
        return payerBankCode;
    }

    public void setPayerBankCode(String payerBankCode) {
        this.payerBankCode = payerBankCode;
    }

    public String getPayerBankReginCode() {
        return payerBankReginCode;
    }

    public void setPayerBankReginCode(String payerBankReginCode) {
        this.payerBankReginCode = payerBankReginCode;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerAccType() {
        return payerAccType;
    }

    public void setPayerAccType(String payerAccType) {
        this.payerAccType = payerAccType;
    }

    public String getPayerAccCompanyType() {
        return payerAccCompanyType;
    }

    public void setPayerAccCompanyType(String payerAccCompanyType) {
        this.payerAccCompanyType = payerAccCompanyType;
    }

    public String getPayerAccNo() {
        return payerAccNo;
    }

    public void setPayerAccNo(String payerAccNo) {
        this.payerAccNo = payerAccNo;
    }

    public String getPayerAccName() {
        return payerAccName;
    }

    public void setPayerAccName(String payerAccName) {
        this.payerAccName = payerAccName;
    }

    public String getPayerIdCardType() {
        return payerIdCardType;
    }

    public void setPayerIdCardType(String payerIdCardType) {
        this.payerIdCardType = payerIdCardType;
    }

    public String getPayerIdCardNo() {
        return payerIdCardNo;
    }

    public void setPayerIdCardNo(String payerIdCardNo) {
        this.payerIdCardNo = payerIdCardNo;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getDdSign() {
        return ddSign;
    }

    public void setDdSign(String ddSign) {
        this.ddSign = ddSign;
    }

    public String getTransSn() {
        return transSn;
    }

    public void setTransSn(String transSn) {
        this.transSn = transSn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSysRefCode() {
        return sysRefCode;
    }

    public void setSysRefCode(String sysRefCode) {
        this.sysRefCode = sysRefCode;
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

    public String getAccBankCode() {
        return accBankCode;
    }

    public void setAccBankCode(String accBankCode) {
        this.accBankCode = accBankCode;
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

    public long getIdDdReq() {
        return idDdReq;
    }

    public void setIdDdReq(long idDdReq) {
        this.idDdReq = idDdReq;
    }
}
