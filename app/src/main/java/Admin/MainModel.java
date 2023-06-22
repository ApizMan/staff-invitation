package Admin;

public class MainModel {
    String companyname, contactnumber, email, eventdate, eventdescription, eventlocation, eventname, eventtime;

    MainModel ()
    {

    }

    public MainModel(String companyname, String contactnumber, String email, String eventdate, String eventdescription, String eventlocation, String eventname, String eventtime) {
        this.companyname = companyname;
        this.contactnumber = contactnumber;
        this.email = email;
        this.eventdate = eventdate;
        this.eventdescription = eventdescription;
        this.eventlocation = eventlocation;
        this.eventname = eventname;
        this.eventtime = eventtime;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }

    public String getEventlocation() {
        return eventlocation;
    }

    public void setEventlocation(String eventlocation) {
        this.eventlocation = eventlocation;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }
}
