package br.edu.utfpr.fillipecerdan.comissioningcontrol.model;

import java.util.Date;

public class EquipmentEntity {
    private String desc;
    private String tag;
    private String comment;
    private EquipmentType type;
    private EquipmentStatus status;
    private Boolean acceptedOutOfSpecification;
    private Date lastChange;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public Boolean getAcceptedOutOfSpecification() {
        return acceptedOutOfSpecification;
    }

    public void setAcceptedOutOfSpecification(Boolean acceptedOutOfSpecification) {
        this.acceptedOutOfSpecification = acceptedOutOfSpecification;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange() {
        this.lastChange = new Date();
    }

    public EquipmentEntity(String tag, EquipmentType type, EquipmentStatus status, String comment, Boolean acceptedOutOfSpecification, Date lastChange) {
        this.tag = tag;
        this.comment = comment;
        this.type = type;
        this.status = status;
        this.acceptedOutOfSpecification = acceptedOutOfSpecification;
        this.lastChange = lastChange;
    }
}
