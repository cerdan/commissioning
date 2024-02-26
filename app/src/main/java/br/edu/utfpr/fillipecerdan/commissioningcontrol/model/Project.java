package br.edu.utfpr.fillipecerdan.commissioningcontrol.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Project implements Externalizable, Comparable<Project> {
    private long id;
    private String code = "";
    private String name = "";
    private String customerName = "";
    private String location = "";
    private int startYear;
    private List<Equipment> equipmentList;

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

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
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

    public static final Comparator<Project> BY_YEAR = (o1,o2) -> o1.getStartYear()-o2.getStartYear();

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
                Objects.equals(getStartYear(), project.getStartYear()) &&
                Objects.equals(getEquipmentList(), project.getEquipmentList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode(), getName(), getCustomerName(), getLocation(), getStartYear(), getEquipmentList());
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
        startYear = in.readInt();
    }
}
