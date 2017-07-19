package sourcecode;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;


//Find the module name which is generating maximum error logs
public class ErrorModule 
{
	public static class  MapClass extends Mapper<LongWritable,Text,Text,Text> 
	{
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException
		{
			try
			{
				String[] str=value.toString().split(" ");
			    if(str[3].contains("[ERROR]"))
			    	context.write(new Text(str[2]),new Text("1"));
			}
			catch(Exception e)
			{	System.out.print(e.getMessage());}
		}
	}
	public static class  ReduceClass extends Reducer<Text,Text,Text,LongWritable>
	{
		String module;
		long max=0, mtotal;
		public void reduce(Text key,Iterable<Text> value,Context context) throws IOException, InterruptedException
		{
			  long count=0;
			  for(Text val:value)
				  count++;  
			  if(count > max)
			  {
				  max = count;
				  mtotal = count;
				  module = key.toString();
			  }
		}
		protected void cleanup(Context context) throws IOException,InterruptedException
		{ 
			context.write(new Text(module), new LongWritable(mtotal));
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException 
	{
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(ErrorModule.class);
			job.setMapperClass(MapClass.class);
			job.setReducerClass(ReduceClass.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(LongWritable.class);
			
			FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
