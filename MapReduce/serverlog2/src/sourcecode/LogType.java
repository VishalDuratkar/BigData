package sourcecode;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;

//Count the number of logs of each type like trace log 7, error logs 12 and so on.
public class LogType 
{
	public static class  MapClass extends Mapper<LongWritable,Text,Text,IntWritable> 
	{
		public void map(LongWritable key,Text value, Context context) throws IOException, InterruptedException
		{
		      String[] lineparts=value.toString().split(" ");
		      String type=lineparts[3];
			  context.write(new Text(type),new IntWritable(1));
		}
	}
	public static class  ReduceClass extends Reducer<Text,Text,Text,IntWritable>
	{
		Map<String, Integer> tmap=new TreeMap<String, Integer>();
		public void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException
		{
			int count=0;
			for(IntWritable data:value)
				count++;
			tmap.put(key.toString(), count);
		}
		protected void cleanup(Context context) throws IOException,InterruptedException
		{ 
			for (Map.Entry m : tmap.entrySet()) 
				context.write(new Text(m.getKey().toString()), new IntWritable((Integer)(m.getValue())));
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException 
	{
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(LogType.class);
			job.setMapperClass(MapClass.class);
			job.setReducerClass(ReduceClass.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
