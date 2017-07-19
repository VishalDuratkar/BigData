/*
 * Q:- Write a program in java to accept customer details and perform below task. 
 1. Store the all data in file customer.txt 
 2.Read the those details those customer whose salary is > 100000 and write it to another file. 
 */
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Scanner; 
 
/** 
* 
* @author DUVISHAL 
*/ 
 
class customer implements Serializable 
{ 
 private int customerID; 
 private String customerName; 
 private int customerAge; 
 private long customerPhone; 
 private long customerSalary; 
 private String customerCity; 
 private String customerCountry; 

 public void acceptDetails() 
 { 
  Scanner sc = new Scanner(System.in); 
  System.out.print("Enter the Customer ID - "); 
  customerID = sc.nextInt(); 
  System.out.println(); 
  System.out.print("Enter the Customer Name - "); 
  customerName = sc.next(); 
  System.out.println(); 
  System.out.print("Enter the Customer Age - "); 
  customerAge = sc.nextInt(); 
  System.out.println(); 
  System.out.print("Enter the Customer Phone - "); 
  customerPhone = sc.nextLong(); 
  System.out.println(); 
  System.out.print("Enter the Customer Salary - "); 
  customerSalary = sc.nextLong(); 
  System.out.println(); 
  System.out.print("Enter the Customer City - "); 
  customerCity = sc.next(); 
  System.out.println(); 
  System.out.print("Enter the Customer Country - "); 
  customerCountry = sc.next(); 
  System.out.println(); 
 } 
 
 @Override 
 public String toString() { 
  return "customer{" + "customerID=" + customerID + ", customerName=" + customerName + ", customerAge=" + customerAge + ", customerPhone=" + customerPhone + ", customerSalary=" + customerSalary + ", customerCity=" + customerCity + ", customerCountry=" + customerCountry + '}'; 
 } 
 
 public long getCustomerSalary() { 
  return customerSalary; 
 } 
} 
public class JavaApplication14
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
  fobj = new FileOutputStream("D:\\CustomerDetails.txt"); 
  oobj = new ObjectOutputStream(fobj); 
  fiobj = new FileInputStream("D:\\CustomerDetails.txt"); 
  oiobj= new ObjectInputStream(fiobj); 
  objectList = new ArrayList<Object>(); 
  while(true) 
  { 
   System.out.println("1. Add Customer Details.");   
   System.out.println("2. Read Customer Details."); 
   System.out.println("3. Exit. "); 
   System.out.print("Ënter your choice = "); 
   int choice = (new Scanner(System.in)).nextInt(); 
   switch(choice) 
   { 
    case 1: addCustomer(); 
      break; 
    case 2: readCustomer(); 
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
public static void addCustomer() 
{ 
 try 
 { 
  readCustomer(); 
  customer temp = new customer(); 
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
public static void readCustomer() 
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
   fobj1= new FileOutputStream("D:\\Customer.txt");; 
   oobj1= new ObjectOutputStream(fobj1); 
   for(Object cst:objectList) 
   { 
    if(((customer)cst).getCustomerSalary()> 100000) 
    { 
    System.out.println(cst.toString()); 
    oobj1.writeObject(cst); 
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