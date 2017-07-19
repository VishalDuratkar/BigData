/* 
Q:- WAP in java to perform below operation realated to hashmap. 
1. Store mployee details by using employee class. 
2. Traverse collection object 
3. Delete employee details.  
4. Update employee details. 
Ans:-
 

* To change this license header, choose License Headers in Project Properties. 
* To change this template file, choose Tools | Templates 
* and open the template in the editor. 
*/ 
 
//hashmap for removing duplicate keys 

 
import java.util.Scanner; 
import java.util.*; 
 
/** 
* 
* @author DUVISHAL 
*/ 
 
class Employee2
{ 
 private int id; 
 private String name; 
 private int sal; 
 private int age; 
 
 public Employee2() { 
 }  
 public Employee2(int id, String name, int sal, int age) { 
  this.id = id; 
  this.name = name; 
  this.sal = sal; 
  this.age = age; 
 } 
 public void acceptDetails() 
 { 
  Scanner sc= new Scanner(System.in); 
  System.out.print("Enter employee ID - "); 
  id= sc.nextInt(); 
  System.out.println(); 
  System.out.print("Enter the Name of Employee - "); 
  name = sc.next(); 
  System.out.println(); 
  System.out.print("Enter the Salary - "); 
  sal=sc.nextInt(); 
  System.out.println(); 
  System.out.print("Enter the age of employee - )");
  age = sc.nextInt(); 
  System.out.println(); 
 } 
 public void updateAge() 
 { 
  System.out.print("Enter the new age of the employee - "); 
  this.age= new Scanner(System.in).nextInt(); 
  System.out.println(); 
 } 
 public void displyDetails() 
 { 
  System.out.println("Employee ID - " + id + ", Employee Name - " + name + " , Age - " + age + ", Salary - " + sal); 
 } 

 @Override 
 public String toString() 
 { 
  return "Employee ID - " + id + ", Employee Name - " + name + " , Age - " + age + ", Salary - " + sal; 
 } 
} 
public class JavaApplication9
{  

 public static void main(String[] args)
 {  
  int temp; 
  HashMap<Integer, Employee2> hashmap = new HashMap<Integer, Employee2>(); 
  hashmap.put(100, new Employee2(100,"Vishal", 20000,25)); 
  hashmap.put(101, new Employee2(101,"Mehul", 27000, 24)); 
  hashmap.put(107, new Employee2(107,"Vivek", 21000, 30)); 
  hashmap.put( 109, new Employee2(109,"Shyam", 30000, 28)); 
 
  System.out.println("Hashmap contains below employee data....."); 
 
  for(Map.Entry m:hashmap.entrySet()) 
  { 
   System.out.print("Key - " + m.getKey()+ ", Value - " +m.getValue() );
   System.out.println(); 
  } 
  System.out.print("Enter the employee id to remove - "); 
  temp = new Scanner(System.in).nextInt(); 
  hashmap.remove(temp); 
  System.out.println("Hashmap after removing emplpyee....."); 
 
  for(Map.Entry m:hashmap.entrySet()) 
  { 
   System.out.print("Key - " + m.getKey()+ ", Value - " +m.getValue() );
   System.out.println(); 
  } 
  
  System.out.print("Enter the id of employee to change the age -"); 
  temp = new Scanner(System.in).nextInt(); 
  Employee2 tempEmp = hashmap.get(temp); 
  tempEmp.updateAge(); 
  hashmap.put(temp, tempEmp); 
  System.out.println("Hashmap after emplpyee age update....."); 
 
  for(Map.Entry m:hashmap.entrySet()) 
  { 
   System.out.print("Key - " + m.getKey()+ ", Value - " +m.getValue() );
   System.out.println(); 
  } 
 } 
} 
