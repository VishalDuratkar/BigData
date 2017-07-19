import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.Tool;
//mapreduce5
public class MyPartitioner extends Configured implements Tool
{
	/*********************************MAPPER*******************************************/
	public static class MapClass extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context)
		{
			try
			{
				String[] str = value.toString().split(",");
				String gender = str[3];
				context.write(new Text(gender), new Text(value));
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	/**********************************REDUCER******************************************/
	public static class ReducerClass extends Reducer<Text, Text, Text, IntWritable>
	{
		public int max = -1;
		private Text outputKey =  new Text();
		public void reduce(Text Key, Iterable<Text> values, Context context) throws IOException, InterruptedException
		{
			max = -1;
			for (Text val : values)
			{
				String[] str = val.toString().split(",");
				if(Integer.parseInt(str[4]) > max)
				{
					max = Integer.parseInt(str[4]);
					String mykey = str[3] + "," + str[1] + "," + str[2] ;
					outputKey.set(mykey);
				}
			}
			context.write(outputKey, new IntWritable(max));
		}
	}
	/*********************************PARTITIONER*******************************************/
	public static class CaderPartitioner extends Partitioner<Text, Text>
	{
		@Override
		public int getPartition(Text key, Text value, int numReduceTask) 
		{
			String[] str = value.toString().split(",");
			int age = Integer.parseInt(str[2]);
			
			if(age <= 20)
				return 0;
			else if (age > 20 && age <= 30)
				return 1;
			else
				return 2;
		}
	}
	

	@Override
	public int run(String[] arg0) throws Exception 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	/*********************************MAIN*******************************************/
	public static void main(String[] args) throws Exception 
	  {
		    Configuration conf = new Configuration();
		    conf.set("mapreduce.output.textoutputformat.separator",",");
		    Job job = Job.getInstance(conf, "STD Calls");
		    job.setJarByClass(MyPartitioner.class);
		    job.setMapperClass(MapClass.class);
		    job.setPartitionerClass(CaderPartitioner.class);
		    job.setReducerClass(ReducerClass.class);
		    
		    job.setNumReduceTasks(3);
		    job.setMapOutputKeyClass(Text.class);
		    job.setMapOutputValueClass(Text.class);
		    
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(IntWritable.class);
		    
		    job.setInputFormatClass(TextInputFormat.class);
		    job.setOutputFormatClass(TextOutputFormat.class);
		    
		    FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
		  }
}
