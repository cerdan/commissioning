package br.edu.utfpr.fillipecerdan.comissioningcontrol.model;

import java.util.Date;

public class EquipmentEntity {
    private String name;
    private String tag;
    private String comment;
    private EquipmentType type;
    private EquipmentStatus status;
    private Boolean acceptedOutOfSpecification;
    private Date lastChange;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
