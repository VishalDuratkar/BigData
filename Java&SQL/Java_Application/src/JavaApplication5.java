/* 
Q:- WAP in java to create userdefined exceeption which allow to perform bank transaction 
1. If user try to withdraw amount > balance must throw isufficeint fund exception. 
2. Before withdrwa, ask for valid pin, if not valid then throw invalid pin exception. 
3. Ask foe PAN details if deposit > 20000, if not valid then throw invalid transaction. 
 

?* To change this license header, choose License Headers in Project Properties. 
?* To change this template file, choose Tools | Templates 
?* and open the template in the editor. 
?*/ 

 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
 
/** 
?* 
?* @author DUVISHAL 
?*/ 
 
class bankException extends Exception 
{ 
 
 public bankException(String s) { 
  super(s); 
 } 
} 
 
class banking 
{ 
 private String name; 
 private float amount; 
 private int pin; 
 private String PAN; 
 
 public banking(String name, float amount, int pin, String PAN) { 
  this.name = name; 
  this.amount = amount; 
  this.pin = pin; 
  this.PAN = PAN; 
 } 
 
 public String getName() { 
  return name; 
 } 
 
 public void setName(String name) { 
  this.name = name; 
 } 
 
 public float getAmount() { 
  return amount; 
 } 
 
 public void setAmount(float amount) { 
  this.amount = amount; 
 } 
 
 public int getPin() { 
  return pin; 
 } 
 
 public void setPin(int pin) { 
  this.pin = pin; 
 } 
 
 public String getPAN() { 
  return PAN; 
 } 
 
 public void setPAN(String PAN) { 
  this.PAN = PAN; 
 } 

 public void deposit(float amt) throws bankException,IOException 
 { 
  if(amt > 20000) 
  { 
   System.out.println("Please provide PAN number"); 
   String panDetails = (new BufferedReader(new InputStreamReader(System.in))).readLine(); 
   if(panDetails.equals(this.getPAN())) 
    this.amount+=amt; 
   else 
    throw new bankException("PAN Details are not matching. Transaction declined."); 
  } 
  else 
  { 
   this.amount+=amt; 
   System.err.println(amt + " has been added to your account and new balance is - " + this.amount); 
  } 
 } 
 
 public void BalanceDisplay() 
 { 
  System.err.println("Your Balance is - " + this.getAmount()); 
 } 
 public void withdrawl(float amt) throws bankException, IOException 
 { 
  System.err.println("Please enter your PIN"); 
  int pindetails = Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine()); 
  if(this.pin == pindetails) 
  { 
   if((this.amount - amt ) > 0) 
   { 
    this.amount -=amt; 
    System.err.println(amt + " has been withdrawn and new balance is " + this.amount); 
   } 
   else 
    throw new bankException("Transaction failed...Insufficeint fund"); 
  } 
  else 
   throw new bankException("Transaction declined. Incorrect PIN."); 
 } 
} 
 
 
public class JavaApplication5 { 
 public static void main(String[] args) throws IOException { 
  banking customer1= new banking("Vishal", 0, 1111, "ABCDEFG"); 
  customer1.BalanceDisplay(); 
  try
  { 
   customer1.deposit((float) 20000.0); 
  }
  catch (bankException e)
  { 
   System.err.println("Exception occured - " + e.getMessage()); 
  } 
 
  try
  { 
   customer1.withdrawl((float) 20000.0); 
  }
  catch (bankException e)
  { 
   System.err.println("Exception occured - " + e.getMessage()); 
  } 
 } 

} 