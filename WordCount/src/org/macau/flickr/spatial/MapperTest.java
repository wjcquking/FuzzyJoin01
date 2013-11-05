package org.macau.flickr.spatial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.macau.flickr.util.FlickrSimilarityUtil;
import org.macau.flickr.util.FlickrValue;
import org.macau.spatial.Distance;

public class MapperTest {

	public static boolean isTheSameDay(Date d1, Date d2) {  
	    if (d1 != null && d2 != null) {  
	        long time = d1.getTime();  
	        long time2 = d2.getTime();  
	        long MS_OF_ONE_DAY = 8640000;  
	        long l = time/MS_OF_ONE_DAY;  
	        long l2 = time2/MS_OF_ONE_DAY;  
	        return l == l2;  
	    }  
	    return false;  
	}  
	/**
	 * The Data form:
	 * ID;Y;X;timestamp
	 * 973929974000;48.89899;2.380696;1093113743
	 * 
	 */
	public static void main(String[] args){
		String record = "1093113743;48.89899;2.380696;973929974000";
		long id =Long.parseLong(record.split(";")[0]);
		System.out.println(id);
		
		long MS_OF_ONE_DAY = 86400000;  
		
		double y = Double.parseDouble(record.split(";")[1]);
		double x = Double.parseDouble(record.split(";")[2]);
		
		System.out.println(System.currentTimeMillis());
		System.out.println("1093113743");
		Date date = new Date();

		long a = Long.parseLong("1004167107000");
		System.out.println("a"+ a);
		System.out.println(date.getTime());
		Date tempDate = new Date(Long.parseLong("1004693853000"));
		Date tempDate2 = new Date(Long.parseLong("1004167107000"));
		System.out.println(tempDate);
		System.out.println(MapperTest.isTheSameDay(tempDate,tempDate2));
		FlickrValue f = new FlickrValue();
		f.setTimestamp(a);
		
		//System.out.println(tempDate.getDay());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt;
		try {
			dt = sdf.parse("2005-2-19");
			System.out.println(sdf.format(dt));    //输出结果是：2005-2-19
			Date date2=new Date();
			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String time=df.format(tempDate2);
			System.out.println(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * the range of Paris flickr data
		 * latitude : 48.815101 - 48.902967, width
		 * longitude : 2.223266 - 2.473817,height
		 * 48.857952;2.414631
		 * 
		 */
		System.out.println("Distance of all the point");
		
		/*
		 * the Paris Area
		 * the height is 9.781198378041525
		 * the width is 18.366105904837223
		 * the longest distance is 20.79409087566547
		 * The longest distance 20.79409087566547
		 */
		System.out.println("the height is "+Distance.GreatCircleDistance(FlickrSimilarityUtil.minLat, FlickrSimilarityUtil.minLon, FlickrSimilarityUtil.maxLat, FlickrSimilarityUtil.minLon));
		System.out.println("the width is "+Distance.GreatCircleDistance(FlickrSimilarityUtil.minLat, FlickrSimilarityUtil.minLon, FlickrSimilarityUtil.minLat, FlickrSimilarityUtil.maxLon));
		System.out.println("the longest distance is "+Distance.GreatCircleDistance(FlickrSimilarityUtil.minLat, FlickrSimilarityUtil.minLon, FlickrSimilarityUtil.maxLat, FlickrSimilarityUtil.maxLon));
		
		System.out.println(ReadSpatialDataMapper.tileNumber(48.902967, 2.414631));
		
	}
}
