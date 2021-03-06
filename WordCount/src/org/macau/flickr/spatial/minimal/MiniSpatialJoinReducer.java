package org.macau.flickr.spatial.minimal;

/**
 * This is the second step
 * Precondition: All the data is sorted in one dimension
 * Process: Spatial distance join need two dimension in the distance of threshold.
 * Use one dimension to sort and 
 * then sort the another dimension 
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.macau.flickr.util.FlickrSimilarityUtil;
import org.macau.flickr.util.FlickrValue;

public class MiniSpatialJoinReducer extends
	Reducer<IntWritable, FlickrValue, Text, Text>{

	private final Text text = new Text();
	private final Map<Integer,ArrayList<FlickrValue>> map = new HashMap<Integer,ArrayList<FlickrValue>>();
	//private final ArrayList<FlickrValue> records = new ArrayList<FlickrValue>();
	
	public void reduce(IntWritable key, Iterable<FlickrValue> values,
			Context context) throws IOException, InterruptedException{
		
		int count = 0;
		
		for(FlickrValue value:values){
			count++;
			/*We need new a FlickrValue, if not, the values in Map.getTag() will become the same
			 * this may because the address of the value is the same, when change a value, all the values
			 * in the Map.getTag will become the same
			 * 
			 * */
			FlickrValue fv = new FlickrValue(value);
			
			if(map.containsKey(value.getTag())){
				/*
				 * The same function of the map.get(value.getTag()).add(fv);
				 * ArrayList<FlickrValue> recordList = map.get(value.getTag());
				 * recordList.add(fv);
				 * 
				 * the recordList is the same to the map.get(), they are in the same address
				 * when you change one, you change another one.
				 * 
				 */
				map.get(value.getTag()).add(fv);

			}else{

				ArrayList<FlickrValue> recordList = new ArrayList<FlickrValue>();
				recordList.add(fv);
				map.put(new Integer(value.getTag()),recordList);
				
			}
		}
		

		
		Iterator it = map.entrySet().iterator();
		
		
		while(it.hasNext()){
			Map.Entry<Integer,ArrayList<FlickrValue>> m = (Map.Entry<Integer,ArrayList<FlickrValue>>)it.next();
			m.getKey();
			ArrayList<FlickrValue> records = m.getValue();
			
			
			
//			brute force
			for (int i = 0; i < records.size(); i++) {
				FlickrValue rec1 = records.get(i);
			    for (int j = i + 1; j < records.size(); j++) {
			    	FlickrValue rec2 = records.get(j);
			    	long ridA = rec1.getId();
		            long ridB = rec2.getId();
			    	if(FlickrSimilarityUtil.SpatialSimilarity(rec1, rec2)){
			    		
//			            if (ridA < ridB) {
//			                long rid = ridA;
//			                ridA = ridB;
//			                ridB = rid;
//			            }

			            text.set(ridA + "%" + ridB);
			            context.write(text, new Text(""));
			    	}
			    	
			    }
			}
		}
		
		//System.out.println("key is " + key + "record size is " + records.size());
		
		
	}
	
}
