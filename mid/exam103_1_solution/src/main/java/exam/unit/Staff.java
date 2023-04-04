
package exam.unit;

import exam.base.OrgUnit;


public class Staff {
    private int id;
    private String Firstname;
    private String Lastname;
    private OrgUnit affiliation;

    public Staff(int id, String Firstname, String Lastname, OrgUnit affiliation) {
        this.id = id;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.affiliation = affiliation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public OrgUnit getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(OrgUnit affiliation) {
        this.affiliation = affiliation;
    }
    
}
