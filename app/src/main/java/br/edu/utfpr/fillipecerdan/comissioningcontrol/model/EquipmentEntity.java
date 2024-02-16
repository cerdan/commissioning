package br.edu.utfpr.fillipecerdan.comissioningcontrol.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EquipmentEntity implements Serializable {
    private String desc;
    private String tag;
    private String comment;
    private EquipmentType type;
    private EquipmentStatus status;
    private Boolean acceptedOutOfSpec;
    private Date lastChange;

    public EquipmentEntity() {

    }

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

    public Boolean getAcceptedOutOfSpec() {
        return acceptedOutOfSpec;
    }

    public void setAcceptedOutOfSpec(Boolean acceptedOutOfSpec) {
        this.acceptedOutOfSpec = acceptedOutOfSpec;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange() {
        this.lastChange = new Date();
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public EquipmentEntity(String tag, EquipmentType type, EquipmentStatus status, String comment, Boolean acceptedOutOfSpecification, Date lastChange) {
        this.tag = tag;
        this.comment = comment;
        this.type = type;
        this.status = status;
        this.acceptedOutOfSpec = acceptedOutOfSpecification;
        this.lastChange = lastChange;
    }

    public EquipmentEntity(EquipmentEntity equipment) {
        this.tag = equipment.tag;
        this.comment = equipment.comment;
        this.type = equipment.type;
        this.status = equipment.status;
        this.acceptedOutOfSpec = equipment.acceptedOutOfSpec;
        this.lastChange = equipment.lastChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentEntity equipment = (EquipmentEntity) o;
        return Objects.equals(getDesc(), equipment.getDesc()) &&
                Objects.equals(getTag(), equipment.getTag()) &&
                Objects.equals(getComment(), equipment.getComment()) &&
                getType() == equipment.getType() &&
                getStatus() == equipment.getStatus() &&
                Objects.equals(getAcceptedOutOfSpec(), equipment.getAcceptedOutOfSpec()) &&
                Objects.equals(getLastChange(), equipment.getLastChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDesc(),
                getTag(),
                getComment(),
                getType(),
                getStatus(),
                getAcceptedOutOfSpec(),
                getLastChange());
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("tag = %s, type = %s, status = %s, acceptedOoS = %s, lastChange = %s"
                , tag, type, status, acceptedOutOfSpec, lastChange);
    }

}
