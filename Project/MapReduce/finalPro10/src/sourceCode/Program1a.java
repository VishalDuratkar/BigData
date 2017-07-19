package sourceCode;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;
/*
 hadoop jar /home/hduser/workspace/finalPro.jar /user/hive/warehouse/project.db/h1b_final/* /project/project1a
 hadoop fs -cat /project/project1a/*
 
 1 a) Is the number of petitions with Data Engineer job title increasing over time?
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

public class Program1a 
{
	public static class myMapper extends Mapper<LongWritable, Text, IntWritable, LongWritable>
	{
		public void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException
		{
			String[] arr = value.toString().split("\t");
			String jobTitle = arr[4];
			int year = Integer.parseInt(arr[7]);
			if(jobTitle.contains("DATA ENGINEER"));
			context.write(new IntWritable(year),new LongWritable(1));
		}
	}
	
	public static class myReducer extends Reducer<IntWritable, LongWritable, NullWritable, Text> 
	{
		TreeMap<Integer, Long> tmap = new TreeMap<Integer, Long>();
		static long temp = 0;
		public void reduce(IntWritable key, Iterable<LongWritable> value, Context context) throws IOException, InterruptedException
		{
			long sum=0;
			for (LongWritable val : value) 
			{
				sum+= val.get();
			}
			//context.write(key, new IntWritable(sum));
			tmap.put(key.get(), sum);
		}
		
		public void cleanup(Context context) throws IOException, InterruptedException 
		{
			double avg = 0.0, finalAvg = 0.0;
			int count = 0;
			//long temp = 0;	
			
			for(@SuppressWarnings("rawtypes") Map.Entry m:tmap.entrySet())
			{
				int mapKey = (int) m.getKey();
				long mapValue = (long) m.getValue();
				String tempResult = "";
				
				if(temp != 0)
				{		
					avg = (double)(mapValue - temp) * 100 /temp;
					tempResult = String.format("%.2f", avg);
					temp = mapValue;
					finalAvg+=avg;					
					context.write(NullWritable.get(), new Text("" + mapKey + "\t" + mapValue + "\t" + tempResult + " %"));		
				}
				else
				{
					String tempString  = "Year" + "\t" + "Total_P" + "\t" + "Growth(%)" + "\n" + mapKey + "\t" + mapValue;
					temp = mapValue;
					context.write(NullWritable.get(), new Text(tempString));		
				}
				count++;
			}
			String tempResult = "\n\n Average Growth -> " + String.format("%.2f", finalAvg/count) + " %";
			context.write(NullWritable.get(), new Text(tempResult));		
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(Program1a.class);
		job.setMapperClass(myMapper.class);
		job.setReducerClass(myReducer.class);
				
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(LongWritable.class);
	
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true)? 0: 1);
		
	}
}
