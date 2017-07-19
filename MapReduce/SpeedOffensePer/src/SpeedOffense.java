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

//find speed offense %   mapReduce4
public class SpeedOffense 
{	
	public static class MyMapper extends Mapper<LongWritable,Text,Text,IntWritable>
	{
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException
		{
			String arr[]=value.toString().split(",");
			context.write(new Text(arr[0]),new IntWritable(Integer.parseInt(arr[1])) );
		}
	}
	public static class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable>
	{
		public void reduce(Text key,Iterable<IntWritable> value,Context context) throws IOException, InterruptedException{
			
			int off=0,c=0,per=0;
			for(IntWritable d:value)
			{
				c++;
				if(d.get()>65)
					off++;
			}
			per=(off*100/c);
			context.write(key, new IntWritable(per));
			
		}
	}
	  public static void main(String[] args) throws Exception 
	  {
		  Configuration obj = new Configuration();
			Job job = Job.getInstance(obj, "offence percent.....");
			job.setJarByClass(SpeedOffense.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			// job.setNumReduceTasks(2);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);

			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			FileSystem.get(obj).delete(new Path(args[1]), true);
			System.exit(job.waitForCompletion(true) ? 0 : 1);

		  }
}