package br.edu.utfpr.fillipecerdan.commissioningcontrol.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Comparator;
import java.util.Objects;

@Entity
public class Project implements Externalizable, Comparable<Project> {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String code = "";
    @NonNull
    private String name = "";
    @NonNull
    private String customerName = "";
    @NonNull
    private String location = "";
    @NonNull
    private int startYear;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", customerName='" + customerName + '\'' +
                ", location='" + location + '\'' +
                ", startYear=" + startYear +
                '}';
    }

    @Override
    public int compareTo(Project o) {
        return BY_CODE.compare(this,o);
    }


    public static final Comparator<Project> BY_CODE = (o1, o2) -> o1.getCode().compareTo(o2.getCode());

    public static final Comparator<Project> BY_LOCATION = (o1,o2) -> o1.getLocation().compareTo(o2.getLocation());

    public static final Comparator<Project> BY_YEAR = (o1,o2) -> o2.getStartYear()-o1.getStartYear();

    public static final Comparator<Project> BY_CUSTOMER = (o1,o2) -> o1.getCustomerName().compareTo(o2.getCustomerName());

    public static final Comparator<Project> CUSTOMER_LOCATION = chainCompare(BY_CUSTOMER,BY_LOCATION);

    public static final Comparator<Project> chainCompare(Comparator<? super Project> first,
                                                         Comparator<? super Project> second){
        return (Comparator<Project>) (o1, o2) -> {
            int res = first.compare(o1,o2);
            return (res!=0) ? res : second.compare(o1,o2);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return getId() == project.getId() &&
                Objects.equals(getCode(), project.getCode()) &&
                Objects.equals(getName(), project.getName()) &&
                Objects.equals(getCustomerName(), project.getCustomerName()) &&
                Objects.equals(getLocation(), project.getLocation()) &&
                Objects.equals(getStartYear(), project.getStartYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode(), getName(), getCustomerName(), getLocation(), getStartYear());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(id);
        out.writeUTF((code!=null)?code:"");
        out.writeUTF((name!=null)?name:"");
        out.writeUTF((customerName!=null)?customerName:"");
        out.writeUTF((location!=null)?location:"");
        out.writeInt(startYear);
    }

    @Override
    public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
        this.id = in.readLong();
        this.code = in.readUTF();
        this.name = in.readUTF();
        this.customerName = in.readUTF();
        this.location = in.readUTF();
        this.startYear = in.readInt();
    }
}
