/* 
 * Q:- WAP to maintain employee details using arraylist. 
-> Accept minimum 5 employee details. 
-> Display in proper order 
-> Display name of employee having highest salary 
-> Display details in the order of Salay. 
 
Ans:- 

?* To change this license header, choose License Headers in Project Properties. 
?* To change this template file, choose Tools | Templates 
?* and open the template in the editor. 
?*/ 
 
import java.util.Scanner; 
import java.util.*; 
 
/** 
?* 
?* @author DUVISHAL 
?*/ 
 
class Employee implements Comparable<Employee> 
{ 
 private int id; 
 private String name; 
 private int sal; 
 private int age; 
 
 public Employee() { 
 }  
 public Employee(int id, String name, int sal, int age) { 
  this.id = id; 
  this.name = name; 
  this.sal = sal; 
  this.age = age; 
 } 
 public int getId() { 
  return id; 
 } 
 
 public String getName() { 
  return name; 
 } 
 
 public int getSal() { 
  return sal; 
 } 
 
 public int getAge() { 
  return age; 
 } 

 public void acceptDetails() 
 { 
  Scanner sc= new Scanner(System.in); 
  System.err.print("Enter employee ID - "); 
  id= sc.nextInt(); 
  System.out.println(); 
  System.err.print("Enter the Name of Employee - "); 
  name = sc.next(); 
  System.out.println(); 
  System.err.print("Enter the Salary - "); 
  sal=sc.nextInt(); 
  System.out.println(); 
  System.out.print("Enter the age of employee - )");
  age = sc.nextInt(); 
  System.out.println(); 
 } 

 public void displyDetails() 
 { 
  System.err.println("Employee ID - " + id + ", ?Employee Name - " + name + " , Age - " + age + ", Salary - " + sal); 
 } 

 @Override 
 public int compareTo(Employee o) { 
 return this.getSal()- o.getSal(); 
 }
 @Override 
 public String toString() 
 { 
  return "Employee ID - " + id + ", ?Employee Name - " + name + " , Age - " + age + ", Salary - " + sal + "\n"; 
 } 
} 
public class JavaApplication6
{  
 public static void main(String[] args)
 {  
  ArrayList<Employee> arraylist = new ArrayList<Employee>();
  int index=0, tempSal=0; 
  Employee emp1= new Employee(); 
  Employee emp2= new Employee(); 
  Employee emp3= new Employee(); 
  Employee emp4= new Employee(); 
  Employee emp5= new Employee(); 
  emp1.acceptDetails(); 
  emp2.acceptDetails(); 
  emp3.acceptDetails(); 
  emp4.acceptDetails(); 
  emp5.acceptDetails(); 
   
  arraylist.add(emp1); 
  arraylist.add(emp2); 
  arraylist.add(emp3); 
  arraylist.add(emp4); 
  arraylist.add(emp5);
 
  // first way to find out max. sal employee 
 
  for(int i=0; i <arraylist.size(); i++) 
  { if(arraylist.get(i).getSal() > tempSal) 
    index = i; 
  } 
 
  System.err.println("Employee with Highest Sal -> " + arraylist.get(index)); 
  System.err.println("----------- Before Sorting -----------\n" + arraylist); 
  Collections.sort(arraylist); 
  System.err.println("----------- After Sorting -----------\n" + arraylist); 
 
  // second way to find out max. sal employee 
 
  System.err.println("Employee with Highest Sal -> " + arraylist.get(arraylist.size()-1)); 
  System.err.println("Employee with Lowet Sal -> " + arraylist.get(0));   
 } 
} 