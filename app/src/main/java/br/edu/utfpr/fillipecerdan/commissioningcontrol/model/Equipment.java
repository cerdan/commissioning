package br.edu.utfpr.fillipecerdan.commissioningcontrol.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Entity(indices = {@Index(value = {"tag"})},
        foreignKeys = @ForeignKey(entity = Project.class,
                                    parentColumns = "id",
                                    childColumns = "projectId"))
public class Equipment implements Externalizable, Comparable<Equipment> {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(index = true)
    private long projectId;
    private String desc = "";
    @NonNull
    private String tag = "";
    private String comment = "";
    @NonNull
    private EquipmentType type;
    @NonNull
    private EquipmentStatus status;
    private Boolean acceptedOutOfSpec;
    @NonNull
    private Date lastChange;

    public Equipment() {

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Equipment(String tag, EquipmentType type, EquipmentStatus status, String comment, Boolean acceptedOutOfSpecification, Date lastChange) {
        this.desc = "";
        this.tag = tag;
        this.comment = comment;
        this.type = type;
        this.status = status;
        this.acceptedOutOfSpec = acceptedOutOfSpecification;
        this.lastChange = lastChange;
    }

    public Equipment(Equipment equipment) {
        this.desc = equipment.desc;
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
        Equipment equipment = (Equipment) o;
        return getId() == equipment.getId() &&
                Objects.equals(getDesc(), equipment.getDesc()) &&
                Objects.equals(getTag(), equipment.getTag()) &&
                Objects.equals(getComment(), equipment.getComment()) &&
                getType() == equipment.getType() &&
                getStatus() == equipment.getStatus() &&
                Objects.equals(getAcceptedOutOfSpec(), equipment.getAcceptedOutOfSpec()) &&
                Objects.equals(getLastChange(), equipment.getLastChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getDesc(),
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
        return String.format("id=%s, tag = %s, type = %s, status = %s, acceptedOoS = %s, lastChange = %s",
                id, tag, type, status, acceptedOutOfSpec, lastChange);
    }

    @Override
    public int compareTo(Equipment o) {
        return compareByAndThenBy(BY_TYPE,BY_TAG).compare(this,o);
    }

    public static final Comparator<Equipment> compareByAndThenBy(Comparator<? super Equipment> first,
                                                                 Comparator<? super Equipment> second){
        return (Comparator<Equipment>) (o1, o2) -> {
            int res = first.compare(o1,o2);
            return (res!=0) ? res : second.compare(o1,o2);
        };
    }

    public static final Comparator<Equipment> BY_STATUS_NOK = new Comparator<Equipment>() {
        @Override
        public int compare(Equipment o1, Equipment o2) {
            if (!o1.getStatus().equals(o2.getStatus())) {
                if(o1.getStatus() == EquipmentStatus.NOK) return -1;
                else if (o2.getStatus() == EquipmentStatus.NOK) return 1;
                else return compareByAndThenBy(BY_TYPE,BY_TAG).compare(o1,o2);
            }
            return compareByAndThenBy(BY_TYPE,BY_TAG).compare(o1,o2);
        }
    };

    public static final Comparator<Equipment> BY_STATUS_OK = new Comparator<Equipment>() {
        @Override
        public int compare(Equipment o1, Equipment o2) {
            if (!o1.getStatus().equals(o2.getStatus())) {
                if(o1.getStatus() == EquipmentStatus.OK) return -1;
                else if (o2.getStatus() == EquipmentStatus.OK) return 1;
                else return compareByAndThenBy(BY_TYPE,BY_TAG).compare(o1,o2);
            }
            return compareByAndThenBy(BY_TYPE,BY_TAG).compare(o1,o2);
        }
    };

    public static final Comparator<Equipment> BY_TAG = new Comparator<Equipment>() {
        @Override
        public int compare(Equipment o1, Equipment o2) {
            return o1.getTag().compareTo(o2.getTag());
        }
    };

    public static final Comparator<Equipment> BY_TYPE = new Comparator<Equipment>() {
        @Override
        public int compare(Equipment o1, Equipment o2) {
            return o1.getType().compareTo(o2.getType());
        }

    };

    public static final Comparator<Equipment> BY_LAST_CHANGE = new Comparator<Equipment>() {
        @Override
        public int compare(Equipment o1, Equipment o2) {
            return -1 * o1.getLastChange().compareTo(o2.getLastChange()); // Reverse order (most recent first)
        }

    };
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(id);
        out.writeUTF((desc!=null)?desc:"");
        out.writeUTF((tag!=null)?tag:"");
        out.writeUTF((comment!=null)?comment:"");
        out.writeInt(type.ordinal());
        out.writeInt(status.ordinal());
        out.writeBoolean(acceptedOutOfSpec);
        out.writeObject(lastChange);

    }

    @Override
    public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
        id = in.readLong();
        desc = in.readUTF();
        tag = in.readUTF();
        comment = in.readUTF();
        type = EquipmentType.values()[in.readInt()];
        status = EquipmentStatus.values()[in.readInt()];
        acceptedOutOfSpec = in.readBoolean();
        lastChange = (Date) in.readObject();
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

}
