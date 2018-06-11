

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

import javax.swing.*;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class Kmeans {
	
	// JDBC driver name and database URL
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	 static final String DB_URL = "jdbc:mysql://localhost/";  // :3306
	 // Database credentials
	 static final String USER = "root";
	 static final String PASS = "root"; // insert the password to SQL server
	 
	 static double[][] NORManalogVlist= new double [20][18];
	 static double[][] NORMmeasurlist = new double [200][18];
	 static double[][] RandCenter = new double[4][18];
	 static double [][] centroids = new double [4][18];
	 static double [][] cluster1 = new double [200][18];
	 static double [][] cluster2 = new double [200][18];
	 static double [][] cluster3 = new double [200][18];
	 static double [][] cluster4 = new double [200][18];
	 static double dist1,dist2,dist3,dist4;
	 static int index1,index2,index3,index4;
     static ArrayList<Integer> cbelongs = new ArrayList<>();
     
     static double maxVol, minVol,maxAng,minAng;


	public static void main(String[] args) {
		
		
		try {
			GetData();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for (int i = 0 ; i < 200 ; i++){ 
			cbelongs.add(0); 
			} 
		
	    Initialization();
	  	K_clusters();
	
	  	System.out.println(index1);
		
	  	System.out.println(index2);
	  	
	  	System.out.println(index3);
	  	
	  	System.out.println(index4); 
	  	System.out.println("-----------------");
	  	
	  	
	  	for (int i = 0; i < index1; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster1[i][j] = cluster1[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster1[i][j] = cluster1[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	for (int i = 0; i < index2; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster2[i][j] = cluster2[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster2[i][j] = cluster2[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	for (int i = 0; i < index3; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster3[i][j] = cluster3[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster3[i][j] = cluster3[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	for (int i = 0; i < index4; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster4[i][j] = cluster4[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster4[i][j] = cluster4[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	
	  	
	  	for (int i = 0; i < index1; i++){
	  		for (int j = 0; j < 18; j++){
				
				System.out.print(cluster1[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	System.out.println("-----------------");
	  	for (int i = 0; i < index2; i++){
			for (int j = 0; j < 18; j++){
				System.out.print(cluster2[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	System.out.println("-----------------");
	  	for (int i = 0; i < index3; i++){
			for (int j = 0; j < 18; j++){
				System.out.print(cluster3[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	System.out.println("-----------------");
	  	for (int i = 0; i < index4; i++){
			for (int j = 0; j < 18; j++){
				System.out.print(cluster4[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	
	  	System.out.println("-----------------");
	  	
	  	
	  	
	  	
	  	
	  	// System.out.println(centroids);
	  	
	  	// KNN algorithm
	  	
		double cluster1 = 0;
		double cluster2 = 0;
		double cluster3 = 0;
		double cluster4 = 0;	
	  	
		
		ArrayList<Integer> testcbelongs = new ArrayList<>();
	
		
		ArrayList<Double[]> distanceTable = new ArrayList<Double[]>(); 
		
		double testdistance;
		
		for (int i = 0 ; i < 200 ; i ++){ 
			distanceTable.add(new Double[3]);
		} // initialize the ArrayList
		
		
		for (int i = 0 ; i < 20 ; i ++){ 
		testcbelongs.add(0);} // initialize the ArrayList
		
		
		for (int i = 0 ; i < 20 ; i++){
	
			int knn = 5;
			
			for (int j = 0; j < 200 ; j++){
				testdistance = 0;		
				for (int m = 0; m < 18 ; m++){ // calculate distance between test value and measurement values
					testdistance += testdistance + Math.pow((NORManalogVlist[i][m]-NORMmeasurlist[j][m]), 2);	       	
				}
	            testdistance = Math.sqrt(testdistance);
				
				
				distanceTable.get(j)[1]=testdistance; // add distance to table
				distanceTable.get(j)[2]=(double)cbelongs.get(j);// add cluster the measurement belongs in to table 
						
			}
			
			double[]clusters = new double[5];
			Quicksort sorter = new Quicksort(); 
			sorter.sort(distanceTable); // sort measurements by distance to test value 
			
			while(true) {
				
				clusters = getclusters(distanceTable,knn);	 
				
				if (clusters[4] == 1.0) {//if flag raised add an extra neighbor 
					knn++; 
					continue;
				}  
		 
				cluster1 = clusters[0];
				cluster2 = clusters[1];
			    cluster3 = clusters[2];
			    cluster4 = clusters[3];
					
			    if (cluster1 > cluster2 && cluster1 > cluster3 && cluster1 > cluster4) { // if most of k closest measurements belong in cluster 1 ->
			       // 	testobs1.add(testtimes.get(i).intValue()); // -> sort test measurement in cluster 1
			       	System.out.println("Test measurement: "+(i+1)+ " belongs to cluster: 1");
			       	testcbelongs.set(i , 1);
			    }
				
			    if (cluster2 > cluster1 && cluster2 > cluster3 && cluster2 > cluster4) {
			      	// testobs2.add(testtimes.get(i).intValue());
			      	System.out.println("Test measurement: "+(i+1)+ " belongs to cluster: 2");
			      	testcbelongs.set(i , 2);
			    }
				    
			    if (cluster3 > cluster1 && cluster3 > cluster2 && cluster3 > cluster4) {
			      	// testobs3.add(testtimes.get(i).intValue());
			      	System.out.println("Test measurement: "+(i+1)+" belongs to cluster: 3 ");
			      	testcbelongs.set(i , 3);
			    }
				    
			    if (cluster4 > cluster1 && cluster4 > cluster2 && cluster4 > cluster3) {
			      	// testobs4.add(testtimes.get(i).intValue());
			      	System.out.println("Test measurement: "+(i+1) +" belongs to cluster: 4 ");
			      	testcbelongs.set(i , 4);
			    }
			    break;
			}
		}
		
		
		// System.out.println(testcbelongs);
		
		
		
		
		
						
	
	}

	public static void main(String[] args) {
		
		
		try {
			GetData();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for (int i = 0 ; i < 200 ; i++){ 
			cbelongs.add(0); 
			} 
		
	    Initialization();
	  	K_clusters();
	 
	  	System.out.println(index1);
		
	  	System.out.println(index2);
	  	
	  	System.out.println(index3);
	  	
	  	System.out.println(index4); 
	  	System.out.println("-----------------");
	  	
	  	
	  	for (int i = 0; i < index1; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster1[i][j] = cluster1[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster1[i][j] = cluster1[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	for (int i = 0; i < index2; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster2[i][j] = cluster2[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster2[i][j] = cluster2[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	for (int i = 0; i < index3; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster3[i][j] = cluster3[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster3[i][j] = cluster3[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	for (int i = 0; i < index4; i++){
	  		for (int j = 0;j < 18; j+=2){
	  			cluster4[i][j] = cluster4[i][j]*(maxVol-minVol) + minVol;
	  		}
	  		for (int j = 1; j < 18;j+=2){
	  			cluster4[i][j] = cluster4[i][j]*(maxAng-minAng) + minAng;
	  		}
	  	}
	  	
	  	
	  	for (int i = 0; i < index1; i++){
	  		for (int j = 0; j < 18; j++){
				
				System.out.print(cluster1[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	System.out.println("-----------------");
	  	for (int i = 0; i < index2; i++){
			for (int j = 0; j < 18; j++){
				System.out.print(cluster2[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	System.out.println("-----------------");
	  	for (int i = 0; i < index3; i++){
			for (int j = 0; j < 18; j++){
				System.out.print(cluster3[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	System.out.println("-----------------");
	  	for (int i = 0; i < index4; i++){
			for (int j = 0; j < 18; j++){
				System.out.print(cluster4[i][j] + "\t");
			}
			System.out.print("\n");
			
		} 
	  	
	  	System.out.println("-----------------");
	  	
	  	
	  	
	  	
	  	
	  	// System.out.println(centroids);
	  	
	  	// KNN algorithm
	  	
		double cluster1 = 0;
		double cluster2 = 0;
		double cluster3 = 0;
		double cluster4 = 0;	
	  	
		
		ArrayList<Integer> testcbelongs = new ArrayList<>();

		
		ArrayList<Double[]> distanceTable = new ArrayList<Double[]>(); 
		
		double testdistance;
		
		for (int i = 0 ; i < 200 ; i ++){ 
			distanceTable.add(new Double[3]);
		} // initialize the ArrayList
		
		
		for (int i = 0 ; i < 20 ; i ++){ 
		testcbelongs.add(0);} // initialize the ArrayList
		
		
		for (int i = 0 ; i < 20 ; i++){

			int knn = 5;
			
			for (int j = 0; j < 200 ; j++){
				testdistance = 0;		
				for (int m = 0; m < 18 ; m++){ // calculate distance between test value and measurement values
					testdistance += testdistance + Math.pow((NORManalogVlist[i][m]-NORMmeasurlist[j][m]), 2);	       	
				}
                testdistance = Math.sqrt(testdistance);
				
				
				distanceTable.get(j)[1]=testdistance; // add distance to table
				distanceTable.get(j)[2]=(double)cbelongs.get(j);// add cluster the measurement belongs in to table 
						
			}
			
			double[]clusters = new double[5];
			Quicksort sorter = new Quicksort(); 
			sorter.sort(distanceTable); // sort measurements by distance to test value 
			
			while(true) {
				
				clusters = getclusters(distanceTable,knn);	 
				
				if (clusters[4] == 1.0) {//if flag raised add an extra neighbor 
					knn++; 
					continue;
				}  
		 
				cluster1 = clusters[0];
				cluster2 = clusters[1];
			    cluster3 = clusters[2];
			    cluster4 = clusters[3];
					
			    if (cluster1 > cluster2 && cluster1 > cluster3 && cluster1 > cluster4) { // if most of k closest measurements belong in cluster 1 ->
			       // 	testobs1.add(testtimes.get(i).intValue()); // -> sort test measurement in cluster 1
			       	System.out.println("Test measurement: "+(i+1)+ " belongs to cluster: 1");
			       	testcbelongs.set(i , 1);
			    }
				
			    if (cluster2 > cluster1 && cluster2 > cluster3 && cluster2 > cluster4) {
			      	// testobs2.add(testtimes.get(i).intValue());
			      	System.out.println("Test measurement: "+(i+1)+ " belongs to cluster: 2");
			      	testcbelongs.set(i , 2);
			    }
				    
			    if (cluster3 > cluster1 && cluster3 > cluster2 && cluster3 > cluster4) {
			      	// testobs3.add(testtimes.get(i).intValue());
			      	System.out.println("Test measurement: "+(i+1)+" belongs to cluster: 3 ");
			      	testcbelongs.set(i , 3);
			    }
				    
			    if (cluster4 > cluster1 && cluster4 > cluster2 && cluster4 > cluster3) {
			      	// testobs4.add(testtimes.get(i).intValue());
			      	System.out.println("Test measurement: "+(i+1) +" belongs to cluster: 4 ");
			      	testcbelongs.set(i , 4);
			    }
			    break;
			}
		}
		
		
		// System.out.println(testcbelongs);
		
		
		
		
		
						

	}
	
	public static void GetData() throws ClassNotFoundException, SQLException{
	
		Connection conn = null;
		
		 String sql;
		 // Register JDBC driver
		 Class.forName(JDBC_DRIVER);
		 
		 // Open a connection
		 conn = DriverManager.getConnection(DB_URL, USER, PASS);
		 
		 Statement stm;
		 stm = conn.createStatement();
		 sql = "USE subtables";
		 stm.executeUpdate(sql);
		 
		 sql = "SELECT * FROM substations;";
		 ResultSet rst;
		 rst = stm.executeQuery(sql);
		 
		 
		 // sublist can be removed
		 ArrayList<substations> subList = new ArrayList<substations>();
		 while (rst.next()) {
		    	substations sub = new substations(rst.getString("rdfid"), rst.getString("name"), rst.getString("region_id"));
		        subList.add(sub);
		        // System.out.println(sub.GetrdfID());
		        
		    }
		
		// Get value from measurements table 
		// Order by time from 1 to 200
		// Limit 0,10000£º 0 is offset, 10000 is total number of rows
		
		sql = "SELECT * FROM measurements ORDER BY time LIMIT 0,10000;";
		
		rst = stm.executeQuery(sql);  // resultset: imagine the table
		ArrayList<measurement> measureList = new ArrayList<measurement>();
		
		
		double[] pointlist= new double [3600];
		double[][] measurlist= new double [200][18];
		int count = 0; // index for we use rst.next(), we need to provide an extra pointer
		while (rst.next()) { // next means the pointer
			// measurement class constructor 
			// check the type of each attribute from sql: we know the time and value are double, others are varchar
	    	measurement meas = new measurement(rst.getString("rdfid"), rst.getString("name"), rst.getDouble("time"), rst.getDouble("value"), rst.getString("sub_rdfid"));
	        measureList.add(meas);
	        // System.out.println(meas.GetrdfID());
	        pointlist[count]=meas.GetValue(); // the function in the class 
	        count++;
		    }
		// change from the 3600*1 to 200*18
		for(int i=0;i<200;i++){
			for(int j=0;j<18 ;j++){
				measurlist[i][j]=pointlist[i*18+j];
				// System.out.print(measurlist[i][j] + " ");
			}
			// System.out.print("\n");
		}
		
		
		// same as the measurement
		sql = "SELECT * FROM analog_values ORDER BY time LIMIT 0,10000;";
	    rst = stm.executeQuery(sql);
	    ArrayList<measurement> analogValueList = new ArrayList<measurement>();
	    
	    int countValues=0;
		double[] analogVals= new double [360];
		double[][] analogVlist= new double [20][18];
	    
	    while (rst.next()) {
	    	measurement anV = new measurement(rst.getString("rdfid"), rst.getString("name"), rst.getDouble("time"), rst.getDouble("value"), rst.getString("sub_rdfid"));
	        analogValueList.add(anV);
	        analogVals[countValues]=anV.GetValue();
			countValues++;
	        // System.out.println(anV.GetrdfID());
	    }
	    
		for(int i=0;i<20;i++){
			for(int j=0;j<18 ;j++){
				analogVlist[i][j]=analogVals[i*18+j];
				// System.out.print(analogVlist[i][j] + " ");
			}
			// System.out.print("\n");
		}
	    
	    
	    conn.close();
	    
	    // begin the normalization: change the value to 0~1, because the degree is from -180~180, voltage is from 0~1
	    // so the degree mainly influence the update.
	    // the idea is to find the maximum and minimum first
	    
	    
	    // maximum/minimum magnitude value
	    for(int k=0; k<200; k++){
	    	
	    	// choose the initial value randomly
	    	
			maxVol=measurlist[k][0];
			minVol=measurlist[k][0];
			
			for (int i=0; i < 18; i+=2) {
				if (measurlist[k][i] > maxVol) {
		    	maxVol = measurlist[k][i];
		    }
				if (measurlist[k][i] < minVol) {
		    	minVol = measurlist[k][i];
		    }
		}
			for (int i=0; i<18; i+=2) {
				NORMmeasurlist[k][i]=(measurlist[k][i]-minVol)/(maxVol-minVol);
				// System.out.print(NORMmeasurlist[k][i]);
			}
			// System.out.print("\n");
		}
		// Maximum/Minimum Angle Value
		for(int k=0; k<200; k++){
			
			maxAng=measurlist[k][1];	
			minAng=measurlist[k][1];
			for (int i=1; i<18; i+=2) {
			    if (measurlist[k][i] > maxAng) {
			    	maxAng = measurlist[k][i];
			    }
			    if (measurlist[k][i] < minAng) {
			    	minAng = measurlist[k][i];
			    }
			}
				for (int i=1; i<18; i+=2) {
					NORMmeasurlist[k][i]=(measurlist[k][i]-minAng)/(maxAng-minAng);
					// System.out.print(NORMmeasurlist[k][i] + " ");
					}
			// System.out.print("\n");
		}
		
		/* for (int i = 0; i < 200; i++){
			for (int j = 0; j < 18; j++){
				System.out.print(NORMmeasurlist[i][j] + "\t");
			}
			System.out.print("\n");
			
		} */
		
	    // Maximum/Minimum Voltage Value
					for(int k=0; k<20; k++){ 
					double maxVol=analogVlist[k][0];
					double minVol=analogVlist[k][0];
					for (int i=0; i<18; i+=2) {
					    if (analogVlist[k][i] > maxVol) {
					    	maxVol = analogVlist[k][i];
					    }					
					    if (analogVlist[k][i] < minVol) {
					    	minVol = analogVlist[k][i];
					    }
					}
					for (int i=0; i<18; i+=2) {
					NORManalogVlist[k][i]=(analogVlist[k][i]-minVol)/(maxVol-minVol);
					}
					}
					// Maximum/Minimum Angle Value
					for(int k=0; k<20; k++){
					double maxAng=analogVlist[k][1];	
					double minAng=analogVlist[k][1];
					for (int i=1; i<18; i+=2) {
					    if (analogVlist[k][i] > maxAng) {
					    	maxAng = analogVlist[k][i];
					    }
					    if (analogVlist[k][i] < minAng) {
					    	minAng = analogVlist[k][i];
					    }
					}
					for (int i=1; i<18; i+=2) {
						NORManalogVlist[k][i]=(analogVlist[k][i]-minAng)/(maxAng-minAng);
						}
					}
		
		
		
	}

	public static void Initialization() {
		Random rand = new Random();
		
		int[] index = new int[4]; 
		
		for(int i=0; i<4; i++){
				index[i]=rand.nextInt(200);
		}
		for(int j=0; j<4; j++){
			for(int k=0; k<4; k++){
				if (index[j]==index[k] && k!=j){
					index[k]=rand.nextInt(199-0)+0;
				}
			}
		}
		// System.out.println("\n"+" ** Forgy Method for Initializing ** ");
		// System.out.println("Index of randomly selected points of measurements: "+index[0]+"\t"+index[1]+"\t"+index[2]+"\t"+index[3]+"\n");
		for(int i=0; i<4; i++){
			for(int j=0; j<18; j++){
				centroids[i][j]= NORMmeasurlist[index[i]][j];	
				//System.out.print(RandCenter[i][j] + "\t");
				
			}
			// System.out.print("\n");
		}
					
		// calculate the distance
		
		index1=0; // the number of each cluster
	  	index2=0;
	  	index3=0;
	  	index4=0;
	  	
		for(int i=0; i<200; i++)
		 {
		  	for(int j=0; j<18; j++)
			{
				dist1 += ((centroids[0][j]-NORMmeasurlist[i][j])*(centroids[0][j]-NORMmeasurlist[i][j])); // the distance to the first centroid
				
				dist2 += ((centroids[1][j]-NORMmeasurlist[i][j])*(centroids[1][j]-NORMmeasurlist[i][j]));
				
				dist3 += ((centroids[2][j]-NORMmeasurlist[i][j])*(centroids[2][j]-NORMmeasurlist[i][j]));
				
				dist4 += ((centroids[3][j]-NORMmeasurlist[i][j])*(centroids[3][j]-NORMmeasurlist[i][j]));
			
				
			}
		  	
		  	dist1=Math.sqrt(dist1);	
			dist2=Math.sqrt(dist2);	
			dist3=Math.sqrt(dist3);	
			dist4=Math.sqrt(dist4);	
			
			if(dist1 < dist2)
			{
				if(dist1 < dist3)
				{
					if(dist1 < dist4)
					{
						for(int j=0; j<18; j++)
						{
								cluster1[index1][j]=NORMmeasurlist[i][j]; // store the parameter into the cluster1
								
						}
						index1++;
						cbelongs.set(i,1);
						// System.out.println(i + " " + "belong to cluster 1");
						
					}
					else
					{
						for(int j=0; j<18; j++)
						{
					 		cluster4[index4][j]=NORMmeasurlist[i][j];
							
						}
						index4++;
						cbelongs.set(i,4);
						// System.out.println(i + " " + "belong to cluster 4");
					}
				}
				
				else if(dist3 < dist4)
				{
					for(int j=0; j<18; j++)
					{
				 		cluster3[index3][j]=NORMmeasurlist[i][j];
				 		
					}
					index3++;
					cbelongs.set(i,3);
					// System.out.println(i + " " + "belong to cluster 3");
				 	
				}
				else
				{
					for(int j=0; j<18; j++)
					{
				 		cluster4[index4][j]=NORMmeasurlist[i][j];
				 		
					}
					index4++;
					cbelongs.set(i,4);
					// System.out.println(i + " " + "belong to cluster 4");
				 	
				}
			}
		  	else if(dist2 < dist3)
			{
				if(dist2 < dist4)
				{
					for(int j=0; j<18; j++)
					{
							cluster2[index2][j]=NORMmeasurlist[i][j];
					}
					index2++;
					cbelongs.set(i,2);
					// System.out.println(i + " " + "belong to cluster 2");
					
					
					
				}
				else
				{
					for(int j=0; j<18; j++)
					{
				 		cluster4[index4][j]=NORMmeasurlist[i][j];
				 		
					}
					index4++;
					cbelongs.set(i,4);
					// System.out.println(i + " " + "belong to cluster 4");
				 	
				}
			}
			
			else if(dist3 < dist4)
			{
				for(int j=0; j<18; j++)
				{
			 		cluster3[index3][j]=NORMmeasurlist[i][j];
			 		
				}
				index3++;
				cbelongs.set(i,3);
				// System.out.println(i + " " + "belong to cluster 3");
		 		
			}
			else
			{
				for(int j=0; j<18; j++)
				{
			 		cluster4[index4][j]=NORMmeasurlist[i][j];
			 		
				}
				index4++;
				cbelongs.set(i,4);
				// System.out.println(i + " " + "belong to cluster 4");
			}		
		 } // end 200 time stamp or object 
		
		// copy the value to cluster1, 2, 3 and 4
					
					
	} // end function 

	public static void cal_centroids() {
		
		double [][] new_centroids = new double [4][18];
		for(int j=0; j<18; j++)
		{
			for(int i=0; i<index1; i++)
			{
				new_centroids[0][j] += (cluster1[i][j]/(index1));  
				centroids[0][j]=new_centroids[0][j];
				
			}
		}
		
		for(int j=0; j<18; j++)
		{
			for(int i=0; i<index2; i++)
			{
				new_centroids[1][j] += (cluster2[i][j]/(index2));  
				centroids[1][j]=new_centroids[1][j];
		
			}
		}
		
		for(int j=0; j<18; j++)
		{
			for(int i=0; i<index3; i++)
			{
				new_centroids[2][j] += (cluster3[i][j]/(index3));  
				centroids[2][j]=new_centroids[2][j];
			}
		}
		
		for(int j=0; j<18; j++)
		{
			for(int i=0; i<index4; i++)
			{
				new_centroids[3][j] += (cluster4[i][j]/(index4));  
				centroids[3][j]=new_centroids[3][j];
			}
		} 
		
		
	}
	public static void K_clusters() {
		
		
		double [][] old_centroids = new double [4][18];
		double dif1=0,dif2=0,dif3=0,dif4=0;
		double tol=0.00000001;
		
		// because it is hard to choose tolerance, use fixed period to check the code
		
		for(int k =0; k < 50000; k++){
			
		
			// System.out.println(cbelongs);
			
			/*for(int j=0; j<18; j++)
			{
					old_centroids[0][j] = centroids[0][j];
					old_centroids[1][j] = centroids[1][j];
					old_centroids[2][j] = centroids[2][j];
					old_centroids[3][j] = centroids[3][j];
					
			}
			
			cal_centroids();
			
			dif1=0;
			dif2=0;
			dif3=0;
			dif4=0;
			
			
			
			
			for(int j=0; j<18; j++)
			{
					dif1 += Math.pow(((old_centroids[0][j] - centroids[0][j])),2);
					dif2 += Math.pow(((old_centroids[1][j] - centroids[1][j])),2);
					dif3 += Math.pow(((old_centroids[2][j] - centroids[2][j])),2);
					dif4 += Math.pow(((old_centroids[3][j] - centroids[3][j])),2);
					
			}
			
			dif1 = Math.sqrt(dif1);
			dif2 = Math.sqrt(dif2);
			dif3 = Math.sqrt(dif3);
			dif4 = Math.sqrt(dif4);
			
			
			if(dif1<=tol&&dif2<=tol&&dif3<=tol&&dif4<=tol)
			{
				break;
			} */
			cal_centroids(); // must be commented
			
			index1=0;
		  	index2=0;
		  	index3=0;
		  	index4=0;
		
		  	for(int i=0; i<200; i++)
			 {
			  	for(int j=0; j<18; j++)
				{
			  		
			  		dist1 += ((centroids[0][j]-NORMmeasurlist[i][j])*(centroids[0][j]-NORMmeasurlist[i][j]));
					
					dist2 += ((centroids[1][j]-NORMmeasurlist[i][j])*(centroids[1][j]-NORMmeasurlist[i][j]));
					
					dist3 += ((centroids[2][j]-NORMmeasurlist[i][j])*(centroids[2][j]-NORMmeasurlist[i][j]));
					
					dist4 += ((centroids[3][j]-NORMmeasurlist[i][j])*(centroids[3][j]-NORMmeasurlist[i][j]));
				
					
					
				}
			  	dist1=Math.sqrt(dist1);	
				dist2=Math.sqrt(dist2);	
				dist3=Math.sqrt(dist3);	
				dist4=Math.sqrt(dist4);	
			
				if(dist1 < dist2)
				{
					if(dist1 < dist3)
					{
						if(dist1 < dist4)
						{
							for(int j=0; j<18; j++)
							{
									cluster1[index1][j]=NORMmeasurlist[i][j];
																	
							}
							index1++;
							cbelongs.set(i , 1);
							
							
						}
						else
						{
							for(int j=0; j<18; j++)
							{
						 		cluster4[index4][j]=NORMmeasurlist[i][j];
						 		
							}
							index4++;
							cbelongs.set(i , 4);
							
							
						}
					}
					
					else if(dist3 < dist4)
					{
						for(int j=0; j<18; j++)
						{
					 		cluster3[index3][j]=NORMmeasurlist[i][j];
					 		
						}
						index3++;
						cbelongs.set(i , 3);
						
						
					}
					else
					{
						for(int j=0; j<18; j++)
						{
					 		cluster4[index4][j]=NORMmeasurlist[i][j];
					 		
						}
						index4++;
						cbelongs.set(i , 4);
						
						
					}
				}
			  	else if(dist2 < dist3)
				{
					if(dist2 < dist4)
					{
						for(int j=0; j<18; j++)
						{
								cluster2[index2][j]=NORMmeasurlist[i][j];
								
						}
						index2++;
						cbelongs.set(i , 2);
						
						
						
						
					}
					else
					{
						for(int j=0; j<18; j++)
						{
					 		cluster4[index4][j]=NORMmeasurlist[i][j];
					 		
						}
						index4++;
						cbelongs.set(i , 4);
						
						
					}
				}
				
				else if(dist3 < dist4)
				{
					for(int j=0; j<18; j++)
					{
				 		cluster3[index3][j]=NORMmeasurlist[i][j];
				 		
					}
					index3++;
					cbelongs.set(i,3);
					
					
				}
				else
				{
					for(int j=0; j<18; j++)
					{
				 		cluster4[index4][j]=NORMmeasurlist[i][j];
				 		
					}
					index4++;
					cbelongs.set(i , 4);
					
					
				}
				
			 }
		}
		
	  	// end while
	
	}// end function 
	
	
	public static double[] getclusters ( ArrayList <Double[]> distancetable , int KNN){
	    	
	    	double [] cluster = new double[5];
	    	cluster [4] = 0;
	   	   	for (int n = 0 ; n < KNN ; n++) {
	   	   		if(distancetable.get(n)[2] == 1)	{ cluster[0] ++; }
	   	   		else if(distancetable.get(n)[2] == 2)	{ cluster[1] ++; }
	   	   		else if(distancetable.get(n)[2] == 3)	{ cluster[2] ++; }
	   	   		else if(distancetable.get(n)[2] == 4)	{ cluster[3] ++; }
	   	   	}
	   	 
	        if( ((cluster[0] > cluster[1]) && (cluster[0] > cluster[2]) && (cluster[0] > cluster[3])) || // if we don't have a tie rerun cluster
	        		((cluster[1] > cluster[0])  && (cluster[1] > cluster[2]) && (cluster[1] > cluster[3])) || //measurement belongs to
	        		((cluster[2] > cluster[0]) && (cluster[2] > cluster[1]) && (cluster[2] > cluster[3])) ||  
	        		((cluster[3] > cluster[0]) && (cluster[3] > cluster[1]) && (cluster[3] > cluster[2])) ) { return cluster; }
	        else { // if we have a tie raise flag
	        	cluster [4] = 1;
	    	    return cluster ;
	        }
	     
	  }
	
	
	
	
	
	
}
