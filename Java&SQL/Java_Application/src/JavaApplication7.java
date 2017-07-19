/*
Q:- WAP in javato eliminate duplicate key in hashmap as user define objects
Ans-
 
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
 
class Employee1
{ 
 private int id; 
 private String name; 
 private int sal; 
 private int age; 
 
 public Employee1() { 
 }  
 public Employee1(int id, String name, int sal, int age) { 
  this.id = id; 
  this.name = name; 
  this.sal = sal; 
  this.age = age; 
 } 
 public void displyDetails() 
 { 
  System. out.println("Employee ID - " + id + ", Employee Name - " + name + " , Age - " + age + ", Salary - " + sal); 
 } 
 @Override 
 public String toString() 
 { 
  return "Employee ID - " + id + ", Employee Name - " + name + " , Age - " + age + ", Salary - " + sal + "\n"; 
 } 
 @Override 
 public int hashCode() { 
  int hash = 7; 
  hash = 83 * hash + this.id; 
  hash = 83 * hash + Objects.hashCode(this.name); 
  hash = 83 * hash + this.sal; 
  hash = 83 * hash + this.age; 
  return hash; 
 } 
 @Override 
 public boolean equals(Object obj) { 
  if (this == obj) { 
   return true; 
  } 
  if (obj == null) { 
   return false; 
  } 
  if (getClass() != obj.getClass()) { 
   return false; 
  } 
  final Employee1 other = (Employee1) obj; 
  if (this.id != other.id) { 
   return false; 
  } 
  if (this.sal != other.sal) { 
   return false; 
  } 
  if (this.age != other.age) { 
   return false; 
  } 
  if (!Objects.equals(this.name, other.name)) { 
   return false; 
  } 
  return true; 
 } 
} 
public class JavaApplication7
{  
 public static void main(String[] args)
 {  
  HashMap<Employee1,Integer> hashmap = new HashMap<Employee1, Integer>(); 
  hashmap.put(new Employee1(100,"Vishal", 20000,25),1); 
  hashmap.put(new Employee1(101,"Mehul", 27000, 24),2); 
  hashmap.put(new Employee1(107,"Vivek", 21000, 30),3); 
  hashmap.put( new Employee1(109,"Shyam", 30000, 28),4); 
 
  System.err.println("Hashmap Before duplicate Key"); 
  for(Map.Entry m:hashmap.entrySet()) 
  { 
   System.out.print("Key - " + m.getKey()+ ", Value - " +m.getValue() );
   System. out.println(); 
  } 
  hashmap.put(new Employee1(100,"Vishal", 20000,25),11); 
  System.out.println("Hashmap after adding duplicate Key"); 
  for(Map.Entry m:hashmap.entrySet()) 
  { 
   System.out.print("Key - " + m.getKey()+ ", Value - " +m.getValue() ); 
   System. out.println(); 
  }
 } 
} 