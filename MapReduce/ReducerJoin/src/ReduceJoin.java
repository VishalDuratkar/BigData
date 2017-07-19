import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;


public class ReduceJoin 
{
	public static class CustsMapper extends Mapper<LongWritable, Text , Text, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String record = value.toString();
			String[] parts = record.split(",");
			context.write(new Text(parts[0]), new Text("custs\t" + parts[1]));
		}
	}
	
	public static class TnxMapper extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String record = value.toString();
			String[] parts = record.split(",");
			context.write(new Text(parts[2]), new Text("txns\t" + parts[3]));
		}
	}
	
	public static class ReduceJoinClass extends Reducer<Text, Text, Text, Text>
	{
		public void reduce(Text key, Iterable<Text> value, Context context) throws IOException, InterruptedException
		{
			String name="";
			float total=(float) 0.0;
			int count = 0;
			for(Text text:value)
			{
				String[] parts = text.toString().split("\t");
				if(parts[0].equals("txns"))
				{
					total+=Float.parseFloat(parts[1]);
					count+=1;
				}
				else if (parts[0].equals("custs"))
				{
					name=parts[1].toString();
				}
			}
			context.write(new Text(name), new Text(String.format("%d\t%f", count, total)));
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException
	{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"Reduce Side Join ");
		
		job.setJarByClass(ReduceJoin.class);
		job.setReducerClass(ReduceJoinClass.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		MultipleInputs.addInputPath(job, new Path(args[0]),TextInputFormat.class, CustsMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, TnxMapper.class);
		
		FileOutputFormat.setOutputPath(job,  new Path(args[2]));
		System.exit(job.waitForCompletion(true) ? 0 : 1 );
	}
	
}
