/*
Q:- WAP in java to add StudentDetails to the file using DataStream. 
Name - String, Age - int, Fee - float, Gender - Char 
Ans:-
 */
 
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.Serializable; 
 
class Student
{ 
public String name; 
public int age; 
public float fee; 
public char gender; 
public Student(String name, int age, float fee, char gender) { 
super(); 
this.name = name; 
this.age = age; 
this.fee = fee; 
this.gender = gender; 
} 
public String getName() { 
return name; 
} 
public int getAge() { 
return age; 
} 
public float getFee() { 
return fee; 
} 
public char getGender() { 
return gender; 
} 
} 
public class JavaApplication12
{ 
public static void main(String[] args) 
{ 
DataOutputStream doobj = null; 
DataInputStream dioobj= null; 
try 
{ 
doobj = new DataOutputStream(new FileOutputStream("D:\\StudentObjDataStream.txt")); 
Student st1 = new Student("Vishal",26,25000F,'M'); 
Student st2 = new Student("Sujata",25,21000F,'F'); 
doobj.writeUTF(st1.getName()); 
doobj.writeChar('\t'); 
doobj.writeInt(st1.getAge()); 
doobj.writeChar('\t'); 
doobj.writeFloat(st1.getFee()); 
doobj.writeChar('\t'); 
doobj.writeChar(st1.getGender()); 
doobj.writeChar('\t'); 
doobj.writeUTF(st2.getName()); 
doobj.writeChar('\t'); 
doobj.writeInt(st2.getAge()); 
doobj.writeChar('\t'); 
doobj.writeFloat(st2.getFee()); 
doobj.writeChar('\t'); 
doobj.writeChar(st2.getGender()); 
doobj.writeChar('\t'); 
} 
catch(Exception e) 
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
finally
{ 
if(doobj!= null) 
try
{ 
doobj.close(); 
} catch (IOException e)
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
} 
try
{ 
dioobj = new DataInputStream(new FileInputStream("D:\\StudentObjDataStream.txt")); 
int studentcount=0; 
while(dioobj.available() > 0) 
{ 
String tempname = dioobj.readUTF(); 
dioobj.readChar(); 
int tempage = dioobj.readInt(); 
dioobj.readChar(); 
float tempfee = dioobj.readFloat(); 
dioobj.readChar(); 
char tempgender = dioobj.readChar(); 
dioobj.readChar(); 
System.out.println("Student " + (++studentcount) + " Name - " + tempname + ", Age - " + tempage + ", Fee - " + tempfee + ", Gender - " + tempgender); 
} 
}
catch (Exception e)
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
finally
{ 
if(dioobj!= null) 
try
{ 
dioobj.close(); 
} catch (IOException e)
{ 
System.out.println("Exception occured - " + e.getMessage()); 
} 
} 
} 
} 