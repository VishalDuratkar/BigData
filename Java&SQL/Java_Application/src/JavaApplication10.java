/*
Q:- WAP in java to accept employee details bu using treeset. Enter the data in any manner but display the data
by arranging as per employeeID using camparator interface. 
 
Ans:- /* 
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
 
class Employee3
{ 
 private int id; 
 private String name; 
 private int sal; 
 private int age; 
 
 public int getId() { 
  return id; 
 } 
 
 public Employee3() { 
 }  
 public Employee3(int id, String name, int sal, int age) { 
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

class EmployeeIDComp implements Comparator<Employee3> 
{ 
 @Override 
 public int compare(Employee3 o, Employee3 s) 
 { 
  if(o.getId()==s.getId()) 
   return 0; 
  else if(o.getId()> s.getId()) 
   return 1; 
  else
   return -1; 
 } 
} 
public class JavaApplication10
{  
 public static void main(String[] args)
 {  
  TreeSet<Employee3> treesetEx = new TreeSet<Employee3>(new EmployeeIDComp()); 
  Employee3 emp1= new Employee3(); 
  Employee3 emp2= new Employee3(); 
  Employee3 emp3= new Employee3(); 
  Employee3 emp4= new Employee3(); 
  Employee3 emp5= new Employee3(); 
  emp1.acceptDetails(); 
  emp2.acceptDetails(); 
  emp3.acceptDetails(); 
  emp4.acceptDetails(); 
  emp5.acceptDetails(); 
  treesetEx.add(emp1); 
  treesetEx.add(emp2); 
  treesetEx.add(emp3); 
  treesetEx.add(emp4); 
  treesetEx.add(emp5); 
  System.out.println("Tree set data in ascending order: ");
  Iterator iterator= treesetEx.iterator(); 
  while (iterator.hasNext()) 
  { 
   System.out.println(iterator.next().toString() + "\n"); 
  } 
 } 
} 