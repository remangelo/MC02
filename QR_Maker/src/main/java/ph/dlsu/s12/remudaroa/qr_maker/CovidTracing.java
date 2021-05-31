package ph.dlsu.s12.remudaroa.qr_maker;

public class CovidTracing {

    private String name = "";
    private String address = "";
    private String contactNum = "";
    private String age = "";
    private String location = "";
    private String dateTime = "";

    public CovidTracing() {
    }

    public CovidTracing(String name, String address, String contactNum, String age, String location, String dateTime) {
        this.name = name;
        this.address = address;
        this.contactNum = contactNum;
        this.age = age;
        this.location = location;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
