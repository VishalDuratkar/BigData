import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Mapperjoin 
{
	public static class mymapper extends Mapper<LongWritable, Text, Text, Text>
	{
		private Map<String, String > abMap = new HashMap<String, String>();
		private Map<String, String > abMap1 = new HashMap<String, String>();
		private Text outputKey = new Text();
		private Text outputValue = new Text();
		
		protected void setup (Context context) throws IOException, InterruptedException
		{
			super.setup(context);
			URI[] files = context.getCacheFiles();
			Path p = new Path((files[0]));
			Path p1 = new Path((files[1]));
			
			if(p.getName().equals("salary.txt"))
			{
				BufferedReader reader = new BufferedReader(new FileReader(p.toString()));
				String line = reader.readLine();
				while(line != null)
				{
					String[] tokens = line.split(",");
					String emp_id = tokens[0];
					String emp_sal = tokens[1];
					abMap.put(emp_id, emp_sal);
					line = reader.readLine();					
				}
				reader.close();
			}
			if(p1.getName().equals("desig.txt"))
			{
				BufferedReader reader = new BufferedReader(new FileReader(p.toString()));
				String line = reader.readLine();
				while(line != null)
				{
					String[] tokens = line.split(",");
					String emp_id = tokens[0];
					String emp_desig = tokens[1];
					abMap1.put(emp_id, emp_desig);
					line = reader.readLine();					
				}
				reader.close();
			}
			if(abMap.isEmpty())
				throw new IOException("Error: Unable to load Salary file");
			if(abMap1.isEmpty())
				throw new IOException("Error: Unable to load Designation file");
		}
		
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String row = value.toString();
			String[] tokens = row.split(",");
			String emp_id = tokens[0];
			String salary = abMap.get(emp_id);
			String desig = abMap1.get(emp_id);
			String Sal_Desig = salary + "," + desig;
			outputKey.set(row);
			outputValue.set(Sal_Desig);
		}
		
		public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException
		{
			Configuration conf = new Configuration();
			conf.set("mapreduce.output.textoutputformat.seperatot", ",");
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(Mapperjoin.class);
			job.setJobName("Map side join");
			job.setMapperClass(mymapper.class);
			job.addCacheFile(new Path("salary.txt").toUri());
			job.addCacheFile(new Path("desig.txt").toUri());
			job.setNumReduceTasks(0);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
	}
}
