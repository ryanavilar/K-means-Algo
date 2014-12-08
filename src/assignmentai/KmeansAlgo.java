/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentai;

import java.util.ArrayList;
import assignmentai.Centroid;
import assignmentai.Data;

/**
 *
 * @author Ryan Avila
 */
public class KmeansAlgo {
    
    private static int NumOfCluster = 3;
    private static int TotalData = 8;
    private static double dataExample [][] = new double[][]    {{1.0, 1.0}, 
                                                                {8.0, 6.0}, 
                                                                {20.0, 3.0}, 
                                                                {21.0, 2.0}, 
                                                                {11.0, 7.0}, 
                                                                {7.0, 7.0}, 
                                                                {1.0, 2.0},
                                                                {6.0, 8.0}};
    
    private static ArrayList<Data> dataSet = new ArrayList<Data>();
    private static ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    
   
    public static void main(String[] args)
    {
        initialize();
        kMeanCluster();
        
        // Print out clustering results.
        for(int i = 0; i < NumOfCluster; i++)
        {
            System.out.println("Cluster " + i + " includes:");
            for(int j = 0; j < TotalData; j++)
            {
                if(dataSet.get(j).cluster() == i){
                    System.out.println("     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ")");
                }
            } // j
            System.out.println();
        } // i
        
        // Print out centroid results.
        System.out.println("Centroids finalized at:");
        for(int i = 0; i < NumOfCluster; i++)
        {
            System.out.println("     (" + centroids.get(i).X() + ", " + centroids.get(i).Y() + ")");
        }
        System.out.print("\n");
        return;
    }
    
    public static void initialize()
    {
        System.out.println("Centroids initialized at:");
        centroids.add(new Centroid(1.0, 1.0)); // lowest set.
        centroids.add(new Centroid(21.0, 2.0)); // highest set.
        centroids.add(new Centroid(8.0, 6.0));
        System.out.println("     (" + centroids.get(0).X() + ", " + centroids.get(0).Y() + ")");
        System.out.println("     (" + centroids.get(1).X() + ", " + centroids.get(1).Y() + ")");
        System.out.println("     (" + centroids.get(2).X() + ", " + centroids.get(2).Y() + ")");
        System.out.print("\n");
        return;
    }
    
    public static void kMeanCluster()
    {
        final double bigNumber = Math.pow(10, 10);    // some big number that's sure to be larger than our data range.
        double minimum = bigNumber;                   // The minimum value to beat. 
        double distance = 0.0;                        // The current minimum value.
        int sampleNumber = 0;
        int cluster = 0;
        boolean isStillMoving = true;
        Data newData = null;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(dataSet.size() < TotalData)
        {
            newData = new Data(dataExample[sampleNumber][0], dataExample[sampleNumber][1]);
            dataSet.add(newData);
            minimum = bigNumber;
            for(int i = 0; i < NumOfCluster; i++)
            {
                distance = dist(newData, centroids.get(i));
                if(distance < minimum){
                    minimum = distance;
                    cluster = i;
                }
            }
            newData.cluster(cluster);
            
            // calculate new centroids.
            for(int i = 0; i < NumOfCluster; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            sampleNumber++;
        }
        
        // Now, keep shifting centroids until equilibrium occurs.
        while(isStillMoving)
        {
            // calculate new centroids.
            for(int i = 0; i < NumOfCluster; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            
            // Assign all data to the new centroids
            isStillMoving = false;
            
            for(int i = 0; i < dataSet.size(); i++)
            {
                Data tempData = dataSet.get(i);
                minimum = bigNumber;
                for(int j = 0; j < NumOfCluster; j++)
                {
                    distance = dist(tempData, centroids.get(j));
                    if(distance < minimum){
                        minimum = distance;
                        cluster = j;
                    }
                }
                tempData.cluster(cluster);
                if(tempData.cluster() != cluster){
                    tempData.cluster(cluster);
                    isStillMoving = true;
                }
            }
        }
        return;
    }
    /**
     * // Calculate Euclidean distance.
     * @param d - Data object.
     * @param c - Centroid object.
     * @return - double value.
     */
    private static double dist(Data d, Centroid c)
    {
        return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2));
    }
}
