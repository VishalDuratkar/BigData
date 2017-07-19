/*
 * Q:- WAP to add employee details to file as an object. 
 */

 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
 
 
class Employee4 implements Serializable 
{ 
 private double phone; 
 private String name; 
 private String address; 
 private int age; 
 
 public Employee4(double phone, String name, String address, int age) { 
super(); 
this.phone = phone; 
this.name = name; 
this.address = address; 
this.age = age; 
} 
 @Override 
 public String toString() 
 { 
  return "Employee Name - " + name + ", ?Employee Address - " + address + " , Employee Age - " + age + ", Employee Phone - " + phone; 
 } 
} 
public class JavaApplication11
{ 
public static void main(String[] args)
{ 
FileOutputStream fobj= null; 
ObjectOutputStream oobj= null; 
FileInputStream fiobj= null; 
ObjectInputStream oiobj= null; 
try
{ 
fobj = new FileOutputStream("D:\\employeeObjFile.txt"); 
oobj = new ObjectOutputStream(fobj); 
Employee4 obj1 = new Employee4(9999999999d, "Vishal", "Pune", 26); 
Employee4 obj2 = new Employee4(8888888888d, "Sanket", "Nagpur", 25); 
Employee4 obj3 = new Employee4(7777777777d, "Sanket", "Mumbai", 24); 
oobj.writeObject(obj1); 
oobj.writeObject(obj2); 
oobj.writeObject(obj3); 
}
catch (Exception e)
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
finally
{ 
if(oobj!= null) 
try
{ 
oobj.close(); 
} catch (IOException e)
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
} 
List<Object> objectList = new ArrayList<Object>(); 
try
{ 
fiobj = new FileInputStream("D:\\employeeObjFile.txt"); 
oiobj= new ObjectInputStream(fiobj); 
while(true) 
{ 
objectList.add(oiobj.readObject()); 
} 
}
catch (Exception e)
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
finally
{ 
if(oiobj!= null) 
try
{ 
oiobj.close(); 
} catch (IOException e)
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
} 
for(Object emp:objectList) 
{ 
System.out.println(emp.toString()); 
} 
} 
} 