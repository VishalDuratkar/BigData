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
 hadoop jar /home/hduser/workspace/finalPro.jar /user/hive/warehouse/project.db/h1b_final/* /project/project1b
 hadoop fs -cat /project/project1b/*
 
 1 b. Find top 5 job titles who are having highest avg growth in applications.
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

public class Program1b 
{
	public static class myMapper extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		public void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException
		{
			String[] arr = value.toString().split("\t");
			int year =  Integer.parseInt(arr[7]);
			context.write(new Text(arr[4]), new IntWritable(year));
		}
	}
	
	public static class myReducer extends Reducer<Text, IntWritable, NullWritable, Text> 
	{
		TreeMap<Double, String> tmap = new TreeMap<Double, String>();
		
		public void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException
		{
			String rkey = key.toString();
			long year11 = 0, year12 = 0, year13 = 0, year14 = 0, year15 =0, year16 =0;
			double cyc1 = 0.0, cyc2 = 0.0, cyc3 = 0.0, cyc4 = 0.0, cyc5 = 0.0, finavg = 0.0;
			for (IntWritable val : value) 
			{
				if(val.get() == 2011)
					year11++;
				else if(val.get() == 2012)
					year12++; 
				else if(val.get() == 2013)
					year13++; 
				else if(val.get() == 2014)
					year14++; 
				else if(val.get() == 2015)
					year15++;
				else if(val.get() == 2016)
					year16++; 
			}
			
			cyc1  = (year11!=0 ? ((year12 - year11) * 100 /year11) : 0);
			cyc2  = (year12!=0 ? ((year13 - year12) * 100 /year12) : 0);
			cyc3  = (year13!=0 ? ((year14 - year13) * 100 /year13) : 0);
			cyc4  = (year14!=0 ? ((year15 - year14) * 100 /year14) : 0);
			cyc5  = (year15!=0 ? ((year16 - year15) * 100 /year15) : 0);
			
			finavg = (cyc1 + cyc2 + cyc3 + cyc4 + cyc5)/5;
			String temp = rkey + "\t" + "Cycle Growth -> ( " + cyc1 + "," + cyc2 + "," + cyc3 + "," + cyc4 + "," + cyc5 + ") \t" + "Average Growth -> " + finavg;
			if((year11 + year12 + year13 + year14 + year15 + year16) > 1000)
				tmap.put(finavg, temp);
			/*This condition is to exclude records like below
			 * 2011	1, 	2013	2, 2015	1, 2014	2, 2016	249,
			 * BUSINESS ANALYST 2	Cycle Growth -> ( -100.0,0.0,0.0,-50.0,24800.0) Average Growth -> 4930.0
			*/
			
			if(tmap.size() > 5)
				tmap.remove(tmap.firstKey());
				
		}
		
		public void cleanup(Context context) throws IOException, InterruptedException 
		{
			for( String m:tmap.descendingMap().values())
			{
				context.write(NullWritable.get(), new Text(m));
			}
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(Program1b.class);
		job.setMapperClass(myMapper.class);
		job.setReducerClass(myReducer.class);
				
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
	
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true)? 0: 1);
		
	}
}
