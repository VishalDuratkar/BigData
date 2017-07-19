package sourceCode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;
/*
 hadoop jar /home/hduser/workspace/finalPro.jar /user/hive/warehouse/project.db/h1b_final/* /project/project1b
 hadoop fs -cat /project/project1b/*
 
 Find the average Prevailing Wage for each Job for each Year 
 (take part time and full time separate). 
 Arrange the output in descending order.
 
 Total count should be greater than 1000
 1731														(0) s_no
 CERTIFIED													(1) case_status
 BRAMBLES USA INC.											(2) employer_name
 GENERAL AND OPERATIONS MANAGERS							(3) soc_name
 SENIOR DIRECTOR, DATA PLATFORM ENGINEERING AND ANALYTICS	(4) job_title
 Y															(5) full_time_position
 197475														(6) prevailing_wage
 2016														(7) year
 MILPITAS, CALIFORNIA										(8) worksite
 -121.8995741												(9) longitute
 37.4323341													(10)latitute
 */
public class Program8 
{
	public static class myMapper extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
		{
			String[] arr = value.toString().split("\t");
			String keyText = arr[7].trim() + "\t" + arr[4].trim();   //year and Job Title combined key
			String valueText = arr[6].trim() + "," + arr[5].trim(); // Combination of wage and full time/part time
			context.write(new Text(keyText), new Text(valueText));
		}
	}
	
	public static class myReducer extends Reducer<Text, Text, NullWritable, Text>
	{

		Map<String, Double> map11 = new HashMap<String, Double>();
		Map<String, Double> map12 = new HashMap<String, Double>();
		Map<String, Double> map13 = new HashMap<String, Double>();
		Map<String, Double> map14 = new HashMap<String, Double>();
		Map<String, Double> map15 = new HashMap<String, Double>();
		Map<String, Double> map16 = new HashMap<String, Double>();
		
		Map<String, String> partTime = new HashMap<String, String>();
		Map<String, String> fullTime = new HashMap<String, String>();
		public void reduce(Text key, Iterable<Text> value, Context context)
		{
			String keyText = key.toString();
			for (Text val : value) 
			{
				String[] arr1 = val.toString().split(",");
				if(arr1[1].equals("Y") || arr1[1].equals("y"))
				{
					if(fullTime.containsKey(keyText))
					{
						String temp = (String)fullTime.get(keyText);
						temp = temp + "," + arr1[0];
						fullTime.put(keyText, temp);
					}
					else 
					{	fullTime.put(keyText, arr1[6]);		}
				}
				else if(arr1[1].equals("N") || arr1[1].equals("n"))
				{
					if(partTime.containsKey(keyText))
					{
						String temp = (String)partTime.get(keyText);
						temp = temp + "," + arr1[0];
						partTime.put(keyText, temp);
					}
					else 
					{	partTime.put(keyText, arr1[6]);		}
				}
			}
		}
		
		public void cleanup(Context context) throws IOException, InterruptedException 
		{
			for(Map.Entry m:fullTime.entrySet())
			{
				String[] temp = m.getKey().toString().split("\t");
				String[] temp1 = m.getValue().toString().split(",");
				double avgWage = calAverage(temp1);
				if(temp[0].equals("2011"))
				{	map11.put(temp[1], avgWage);	}
				else if(temp[0].equals("2012"))
				{	map12.put(temp[1], avgWage);	}
				else if(temp[0].equals("2013"))
				{	map13.put(temp[1], avgWage);	}
				else if(temp[0].equals("2014"))
				{	map14.put(temp[1], avgWage);	}
				else if(temp[0].equals("2015"))
				{	map15.put(temp[1], avgWage);	}
				else if(temp[0].equals("2016"))
				{	map16.put(temp[1], avgWage);	}
			}
			
		}
		public double calAverage(String[] wageArr)
		{
			int count = 0;
			double sum = 0;
			for (String val : wageArr) 
			{
				sum += Double.parseDouble(val);
				count++;
			}
			return sum/count;
		}
		
	}
}
