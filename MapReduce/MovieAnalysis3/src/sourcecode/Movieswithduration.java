package sourcecode;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;

//Find the number of movies with duration more than 1.5 hours

public class Movieswithduration 
{
	public static class  MapClass extends Mapper<LongWritable,Text,Text,Text> 
	{
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException
		{
			try
			{
				String[] str=value.toString().split(",");
				if(!str[4].isEmpty())
				{
					Integer seconds=Integer.parseInt(str[4]);
	    		    if(seconds > 4500)		
	                {
	        	     Text outkey=new Text("1");
	        	     String str1=str[1];
	        	     context.write(new Text(outkey), new Text(str1));
	                }
	            }
			}catch(Exception e)
            {
            	System.out.println(e.getMessage());
            }
		}
	}
	public static class  ReduceClass extends Reducer<Text,Text,NullWritable,IntWritable>
	{
		public IntWritable result =new IntWritable();
		public void reduce(Text key,Iterable<Text> value,Context context) throws IOException, InterruptedException
		{
			int count=0;
			for(Text val:value)
				count++;
			result.set(count);
			context.write(null,result);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException 
	{
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(Movieswithduration.class);
			job.setMapperClass(MapClass.class);
			job.setReducerClass(ReduceClass.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(IntWritable.class);
			
			FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
