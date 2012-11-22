package org.ia.practiques;

import org.ia.practiques.Rgb;
import org.ia.practiques.Utils;

import android.graphics.Bitmap;

/**
 * This class provides methods to calculate the k-means algorithm
 * @author Victor Martinez, Andrei Jianu
 *
 */
public class Kmeans{
    
    private int iTer = 0;
    private int nClusters = 0;
    private Utils uClass = null;
    private Bitmap bImg = null;
    private Rgb[] rgbClusters = null;
    private int[] cntr;
    
    /**
     * The constructor of the class
     * 
     * @param iTer max number of iterations 
     * @param nClusters number of clusters 
     * @param bImg Bitmap with our image
     * 
     * @see Bitmap
     */
    public Kmeans(int iTer, int nClusters, Bitmap bImg)
    {
        this.iTer        = iTer;
        this.bImg        = bImg;
        
        this.uClass      = new Utils();
        this.nClusters   = nClusters;
        
        this.rgbClusters = new Rgb[this.nClusters];
    }
    
    /**
     * This method initialize the clusters 
     */
    public void initCLusters ()
    {
        int imgWidth    = this.bImg.getWidth();
        int imgHeight   = this.bImg.getHeight();
        
        for(int i = 0; i < this.nClusters; i++)
        {
            int widthRand  = (int) (Math.random() * (imgWidth - 1));
            int heightRand = (int) (Math.random() * (imgHeight - 1));
            this.rgbClusters[i] = uClass.getColor(this.bImg, widthRand, heightRand);
        }
    }
    
    /**
     * @return an Rgb array containing each cluster
     * @see Rgb
     */
    public Rgb[] getClusters()
    {
    	return (this.rgbClusters);
    }
    
    public int[] getCnt() 
    {
    	return this.cntr;
    }
    
    /**
     * This method runs the k-means algorithm
     */
    public void startKmeans ()
    {
    	
        int[] red   = new int[this.nClusters];
        int[] blue  = new int[this.nClusters];
        int[] green = new int[this.nClusters];
        
        int[] ared   = new int[this.nClusters];
        int[] ablue  = new int[this.nClusters];
        int[] agreen = new int[this.nClusters];
        
        cntr  = new int[this.nClusters];
        boolean stop = false;
        
        for(int i = 0; i < this.nClusters; i++)
        {
            ared[i]      = 0;
            ablue[i]     = 0;
            agreen[i]    = 0;
        }
        
        
        
        do
        {
            for(int i = 0; i < this.nClusters; i++)
            {
                red[i]      = 0;
                blue[i]     = 0;
                green[i]    = 0;
                cntr[i]     = 0;
            }
            
            for(int i = 0; i < this.bImg.getWidth(); i++)
            {
                for(int j = 0; j < this.bImg.getHeight(); j++)
                {
                    int whatCluster = 0;
                    Rgb rgbColor = this.uClass.getColor(this.bImg, i, j);
                    int cDistance = this.uClass.getDistance(rgbColor, this.rgbClusters[0]);
                    
                    for(int k = 0; k < this.nClusters; k++)
                    {
                        int tDistance = this.uClass.getDistance(rgbColor, this.rgbClusters[k]);
                        if(tDistance < cDistance)
                        {
                            cDistance = tDistance;
                            whatCluster = k;
                        }
                    }
                    
                    cntr[whatCluster]++;
                    red[whatCluster] += rgbColor.getRed();
                    blue[whatCluster] += rgbColor.getBlue();
                    green[whatCluster] += rgbColor.getGreen();
                }
            }
            
            for(int i = 0; i < this.nClusters; i++)
            {
                if(cntr[i] != 0)
                {
                    red[i]      = (int) (red[i] / cntr[i]);
                    blue[i]     = (int) (blue[i] / cntr[i]);
                    green[i]    = (int) (green[i] / cntr[i]);
                    
                    this.rgbClusters[i] = new Rgb(red[i], green[i], blue[i]);
                }   
            }
            
            stop = true;
            for(int i = 0; i < this.nClusters; i++)
            {
                if(ared[i] != red[i]){stop = false; ared[i] = red[i];}
                if(ablue[i] != blue[i]){stop = false; ablue[i] = blue[i];}
                if(agreen[i] != green[i]){stop = false; agreen[i] = green[i];}
            }
        }while(!stop && this.iTer-- > 0);
    }   
}
