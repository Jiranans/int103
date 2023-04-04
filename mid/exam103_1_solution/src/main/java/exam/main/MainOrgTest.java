
package exam.main;

import exam.base.Group;
import exam.unit.Department;
import exam.unit.School;
import exam.base.OrgUnit;
import exam.unit.Staff;


public class MainOrgTest {
    public static void main(String[] args) {
       //testSchool();
       testStaff();
       //testDepartmentGroup();
    }
    
  static void testSchool(){
      
   School s1 = new School(12,"KP");
   System.out.println("code: "+ s1.getCode()+" Name: "+ s1.getName() +" Balance: " + s1.getBalance()+" log: " + s1.getLog());
   s1.earn(5.2);
   s1.spend(1.2);
   s1.setName("SIT");
   System.out.println("code: "+ s1.getCode()+" Name: "+ s1.getName() +" Balance: " + s1.getBalance()+" log: " + s1.getLog());
   s1.clearLog();
   System.out.println("code: "+ s1.getCode()+" Name: "+ s1.getName() +" Balance: " + s1.getBalance()+" log: " + s1.getLog());
   }
   static void testDepartment() {
   
   }
   static void testSchoolAsOrgUnit() {
   
   }
  static  void testDepartmentAsLoggable() {
  
  }
  static  void testStaff(){
 OrgUnit org1 = new School(12,"KP");
  Staff sf = new Staff(1 , "Lodchong" , "Lorenzo" ,org1);
  Staff s = new Staff(100, "Adam", "Family", new School(1, "SIT"));
  System.out.format("id[%d] name[%s] [%s] affiliation[%s]%n  " , sf.getId() , sf.getFirstname(),sf.getLastname(),sf.getAffiliation().getName());
  sf.setId(5);
  sf.setFirstname("Jiranan");
  sf.setLastname("Lodchong");
  sf.setAffiliation(new School(10,"SIT"));
  System.out.format("id[%d] name[%s] [%s] affiliation[%d][%s]%n  " , sf.getId() , sf.getFirstname(),sf.getLastname(),sf.getAffiliation().getCode(),sf.getAffiliation().getName());
  } 
   
  static  void testDepartmentGroup(){
   /*var school1 = new School(2 ,"KMUTT");
   var school2 = new School(5 ,"KMUTT");
   var Dept1 = new Department(255 , "Lodchong" ,school1 );
   var Dept2 = new Department(255 , "Lodchong2" ,school2 );
   var Dept3 = new Department(255 , "Lodchong3" ,school1 );
   var group = new Group<Department>(5);
   System.out.println("append: " + group.append(Dept1));
  // group.append(Dept1);
    for (int i = 0; i < 10; i++) {
        System.out.println(group.);
      }
   System.out.println("find: " + group.find(Dept3));
   System.out.println("get: " + group.get(2));
   System.out.println("remove: " + group.remove(Dept2));*/
   
   Group<Department> dg = new Group<>(10);
   School s = new School(1,"SIT");
   Department d11 = new Department(11 , "Dept One" , s);
   Department d22 = new Department(22 , "Dept Two" , s);
   Department d33 = new Department(33 , "Dept Three" , s);
   Department d44 = new Department(44 , "Dept Four" , s);
   Department d55 = new Department(55 , "Dept Five" , s);
   
   System.out.println("Name: " + d11.getName() + " append: " + dg.append(d11));
   System.out.println("Name: " + d22.getName() + " append: " + dg.append(d22));
   System.out.println("Name: " + d33.getName() + " append: " + dg.append(d33));
   System.out.println("Name: " + d11.getName() + " append: " + dg.append(d11));
   System.out.println("Name: " + d44.getName() + " append: " + dg.append(d44));
   
   System.out.println("Find: " + d11.getName() + " " + dg.find(d11));
      System.out.println("Revove: " + d11.getName() + " "  + dg.remove(d11));
   System.out.println("Find: " + d11.getName()+ " " + dg.find(d11));
   System.out.println("Find: " + d33.getName()+ " " + dg.find(d33));
   for (int i = 0; i < dg.getSize(); i++) {
       Department d = dg.get(i);
        System.out.format("[%d] Department code[%d] name[%s]%n" , i , d.getCode() , d.getName());
      }   
   }
}
