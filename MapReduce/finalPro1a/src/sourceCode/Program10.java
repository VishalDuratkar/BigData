package sourceCode;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;

/*
hadoop jar /home/hduser/workspace/finalPro.jar /user/hive/warehouse/project.db/h1b_final/* /project/project10
hadoop fs -cat /project/project10/*

Which are the  job positions along with the number of petitions 
which have the success rate more than 70%  in petitions 
(total petitions filed more than 1000)?
SUCCESS RATE % = (Certified + Certified Withdrawn)/Total x 100
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
public class Program10 
{
	public static class myMapper extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
		{
			String[] arr = value.toString().split("\t");
			String job_title = arr[4].trim();
			String case_status = arr[1].trim();
			context.write(new Text(job_title), new Text(case_status));
		}
	}
	
	public static class myReducer extends Reducer<Text, Text, NullWritable, Text>
	{
		Map<String, Double> tmap = new TreeMap<String, Double>();
		
		public void reduce(Text key, Iterable<Text> value, Context context)
		{
			long totalCount = 0, certifiedCount = 0, certifiedWCount = 0;
			for (Text val : value) 
			{
				String temp = val.toString().toUpperCase();
				if(temp.contains("CERTIFIED"))
					certifiedCount++;
				else if (temp.contains("CERTIFIED-WITHDRAWN"))
					certifiedWCount++;
				totalCount++;
			}
			if(totalCount > 1000)
			{
				double successRate = (double)((certifiedCount + certifiedWCount) * 100)/totalCount;
				if(successRate > 70)
					tmap.put(key.toString(), successRate);
			}			
		}
		
		class myClass implements Comparator<String>
		{
			Map<String, Double> tmap;				
			public myClass(Map<String, Double> temp)
			{	this.tmap = temp;	}
			
			public int compare(String arg0, String arg1) 
			{
				// TODO Auto-generated method stub
				if(tmap.get(arg0) >= tmap.get(arg1))
					return -1;
				else 
					return 1;
			}
		}
		public void cleanup(Context context) throws IOException, InterruptedException 
		{
			myClass obj = new myClass(tmap);
			Map<String, Double> tmap1 = new TreeMap<String, Double>(obj);
			tmap1.putAll(tmap);
			for (Map.Entry m : tmap1.entrySet()) 
			{	
				String s_rate = String.format("%.2f", (double)m.getValue());
				context.write(NullWritable.get(), new Text("" + m.getKey().toString() + "\t" + s_rate ));
			}								
		}	
	}
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(Program10.class);
		job.setMapperClass(myMapper.class);
		job.setReducerClass(myReducer.class);
				
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
	
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true)? 0: 1);
		
	}
}
