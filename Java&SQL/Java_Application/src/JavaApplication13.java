/*
 * 
 
Q:- Write a menu driven program in java to accept student details. 
  perform below task. 
  1. Store all data in file. 
  2. Read the details only those student whose age is> 18 from the file and write it to another file. 
 
*/
 
 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Scanner; 
 
class Student2 implements Serializable
{ 
 private int id; 
 private String name; 
 private int age; 
 private double phone; 
 private String feestatus; 
 
 public Student2() { 
 } 
 
 public Student2(int id, String name, int age, double phone, String feestatus) { 
  this.id = id; 
  this.name = name; 
  this.age = age; 
  this.phone = phone; 
  this.feestatus = feestatus; 
 } 
 public void acceptDetails() 
 { 
  Scanner sc = new Scanner(System.in); 
  System.out.print("Enter Student ID - "); 
  this.id = sc.nextInt(); 
  System.out.println(""); 
  System.out.print("Enter Student Name - "); 
  this.name = sc.next(); 
  System.out.println(""); 
  System.out.print("Enter Student Age - "); 
  this.age = sc.nextInt(); 
  System.out.println(""); 
  System.out.print("Enter Student Phone - "); 
  this.phone = sc.nextDouble(); 
  System.out.println(""); 
  System.out.print("Enter FeeStatus - "); 
  this.feestatus = sc.next(); 
  System.out.println(""); 
 } 
 
 public int getAge() { 
  return age; 
 } 
 @Override 
 public String toString() 
 { 
return "Student Details -> Name = " + name + ", ID = " + id + ", Age = " + age + ", Phone = " + phone + ", FeeStatus = " + feestatus; 
 } 
} 
 
public class JavaApplication13
{ 
public static FileOutputStream fobj= null; 
public static ObjectOutputStream oobj= null; 
public static FileInputStream fiobj= null; 
public static ObjectInputStream oiobj= null; 
public static List<Object> objectList; 
public static void main(String[] args) 
{ 
 try 
 { 
  fobj = new FileOutputStream("D:\\studentDetails.txt"); 
  oobj = new ObjectOutputStream(fobj); 
  fiobj = new FileInputStream("D:\\studentDetails.txt"); 
  oiobj= new ObjectInputStream(fiobj); 
  objectList = new ArrayList<Object>(); 
  while(true) 
  { 
   System.out.println("1. Add Student Details.");   
   System.out.println("2. Read Student Details."); 
   System.out.println("3. Exit. "); 
   System.out.print("Ënter your choice = "); 
   int choice = (new Scanner(System.in)).nextInt(); 
   switch(choice) 
   { 
    case 1: addStudent(); 
      break; 
    case 2: readStudent(); 
    readList(); 
    
      break; 
    case 3: exitFunction(); 
    System.exit(0); 
      break; 
    default:System.out.println("Wrong option, please try again..."); 
      break; 
   } 
  } 
 } 
 catch (Exception e)
 { 
  System.out.println("Exception occured1 - " + e.getMessage()); 
 } 
} 
public static void addStudent() 
{ 
 try 
 { 
  readStudent(); 
  Student2 temp = new Student2(); 
  temp.acceptDetails(); 
  objectList.add(temp); 
  for(Object std:objectList) 
   oobj.writeObject(std); 
 } 
 catch (Exception e)
 { 
  System.out.println("Exception occured2 - " + e.getMessage()); 
 } 
 objectList.clear(); 
} 
public static void readStudent() 
{ 
 try
 { 
while(true) 
{ 
objectList.add(oiobj.readObject()); 
} 
}
 catch (Exception e)
 { 
  System.out.println("Exception occured3 - " + e.getMessage()); 
} 
  
} 
public static void exitFunction()
{ 
 try
 { 
 if(oobj!= null) 
 oobj.close(); 
 if(oiobj!= null) 
 oiobj.close(); 
 
 } 
 catch (Exception e)
 { 
 System.out.println("Exception occured4 - " + e.getMessage()); 
 } 
 objectList.clear(); 
 objectList=null; 
} 
public static void readList() 
{ 
 FileOutputStream fobj1; 
 ObjectOutputStream oobj1; 
 try 
 { 
 fobj1= new FileOutputStream("D:\\studentDetails1.txt");; 
 oobj1= new ObjectOutputStream(fobj1); 
for(Object std:objectList) 
{ 
 if(((Student2)std).getAge() > 18) 
 { 
 System.out.println(std.toString()); 
 oobj1.writeObject(std); 
 } 
} 
try
 { 
 if(oobj1!= null) 
 oobj1.close(); 
 }
 catch (Exception e)
 { 
 System.out.println("Exception occured4 - " + e.getMessage()); 
 } 
 } 
 catch (Exception e)
{ 
System.out.println("Exception occured4 - " + e.getMessage()); 
} 
 
 
} 
} 