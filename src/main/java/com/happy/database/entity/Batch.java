package com.happy.database.entity;

import java.util.Date;

public class Batch {
    private Long batchId;

    private String fileName;

    private String clientIp;

    private String clientMachineName;

    private Date cdate;

    private Long recordsCount;

    private String note;

    private String gcType;

    private Short bussnessType;

    private Short filesStatus;

    private Short filesType;

    private Short ddType;

    private Date edate;

    private String statusChanges;

    private Long idBatch;

    private Date sendTime;

    private Integer idCategory;

    private String ddCategory;

    private Long idBatchFromHomer;

    public enum FileStatus {
        WAITTOSEND((short)1),
        MANUAL((short)2),
        SENT((short)3),
        ERROR((short)4),
        PROCESSING((short)5),
        RECEIVED((short)6),
        PROCESSED((short)7);

        private short fileStatus;

        FileStatus(short fileStatus) {
            this.fileStatus = fileStatus;
        }

        public short getFileStatus() {
            return this.fileStatus;
        }
    }

    public enum BusinessType {
        EBU((short)1),
        NINENINEBILL((short)2),
        PSBC((short)3),
        CCB((short)4),
        ABC((short)5),
        CCBDLB((short)6),
        BAOFU((short)7);

        private short businessType;

        BusinessType(short businessType) {
            this.businessType = businessType;
        }

        public short getBusinessType() {
            return businessType;
        }
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public String getClientMachineName() {
        return clientMachineName;
    }

    public void setClientMachineName(String clientMachineName) {
        this.clientMachineName = clientMachineName == null ? null : clientMachineName.trim();
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Long getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Long recordsCount) {
        this.recordsCount = recordsCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getGcType() {
        return gcType;
    }

    public void setGcType(String gcType) {
        this.gcType = gcType == null ? null : gcType.trim();
    }

    public Short getBussnessType() {
        return bussnessType;
    }

    public void setBussnessType(Short bussnessType) {
        this.bussnessType = bussnessType;
    }

    public Short getFilesStatus() {
        return filesStatus;
    }

    public void setFilesStatus(Short filesStatus) {
        this.filesStatus = filesStatus;
    }

    public Short getFilesType() {
        return filesType;
    }

    public void setFilesType(Short filesType) {
        this.filesType = filesType;
    }

    public Short getDdType() {
        return ddType;
    }

    public void setDdType(Short ddType) {
        this.ddType = ddType;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getStatusChanges() {
        return statusChanges;
    }

    public void setStatusChanges(String statusChanges) {
        this.statusChanges = statusChanges == null ? null : statusChanges.trim();
    }

    public Long getIdBatch() {
        return idBatch;
    }

    public void setIdBatch(Long idBatch) {
        this.idBatch = idBatch;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getDdCategory() {
        return ddCategory;
    }

    public void setDdCategory(String ddCategory) {
        this.ddCategory = ddCategory == null ? null : ddCategory.trim();
    }

    public Long getIdBatchFromHomer() {
        return idBatchFromHomer;
    }

    public void setIdBatchFromHomer(Long idBatchFromHomer) {
        this.idBatchFromHomer = idBatchFromHomer;
    }
}
