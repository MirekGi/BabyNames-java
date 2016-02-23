/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn >= 10000) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int namesBoys = 0;
        int namesGirls = 0;
        int namesTotal = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            namesTotal ++;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                namesBoys ++;
            }
            else {
                totalGirls += numBorn;
                namesGirls ++;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("Unique Girls' names = " + namesGirls + " Unique Boys' names = " + namesBoys + " Total names = " + namesTotal);
    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/yob1900.csv");
        totalBirths(fr);
    }
    
    public int getRank(String year, String name, String gender){
        int rank= 0 ;
        //int rankF= 0 ;
        String Filename = "data/yob" + year + ".csv";       
        FileResource fr = new FileResource(Filename);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if ( rec.get(1).equals(gender)) {
                 rank++;
                 if (rec.get(0).equals(name)) return rank;//}
                }                  
            }
        return -1;
       }
    
       public void testgetRank () {
        //FileResource fr = new FileResource();
        //FileResource fr = new FileResource("data/yob1961.csv");
        String year = "1990";
        String gender = "F";
        String name = "Emily";
        int rank = getRank(year,name,gender);
        System.out.println("rank of " + name + " in " + year + " is " + rank);
    }
       
    public String getName(int rank, String year, String gender){
        int rankTemp = 0 ;
        String Filename = "data/yob" + year + ".csv";       
        FileResource fr = new FileResource(Filename);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if ( rec.get(1).equals(gender)) {
                 rankTemp++;
                 if (rank == rankTemp) return rec.get(0);
                }
                   }
        return "NO NAME";
    }
    
       public void testgetName() {
        String year = "2014";
        String gender = "F";
        int rank = 24;
        System.out.println(getName(rank,year,gender) + " is of rank " + rank + " in " + year);
    }
    
    public String whatsNameInYear(String year, String newYear, String name, String gender){
        
        String Filename = "data/yob" + year + ".csv";
        int rank = getRank(year,name,gender);
        String newName = getName(rank,newYear,gender);
        FileResource fr = new FileResource(Filename);
        
        return newName;
       }
   
    
       public void testgwhatsNameInYear() {
        String year = "1972";
        String newYear = "2014";
        String name = "Susan";
        String gender = "F";
        System.out.println(whatsNameInYear(year,newYear,name,gender) + " is in " + newYear + " of the same rank as:" + name    + " in " + year);
    }
    
    public int yearOfHighestRank(String name, String gender) {
        //initial year and rank;
        int rank = 1000000;
        int yearHigh = 0;
        //get the directory:
        DirectoryResource dr = new DirectoryResource();     
        //get the files
        for(File fi : dr.selectedFiles()){
        //get the name of the file, which contains the year
            String fileName = fi.getName();         
            //get the year integer from the name of the file
            int year = Integer.parseInt(fileName.replaceAll("[\\D]", ""));          
            //get the FileResource
            FileResource fr = new FileResource(fi);
        int currRank = -1;
            int pivot = 0;
            for(CSVRecord record : fr.getCSVParser(false)){
                
                if(record.get(1).equals(gender)) {
                    
                    pivot++;
                                
                    if(record.get(0).equals(name)) {
                        currRank = pivot;
                        break;
                    }
                    
                }
                
            }//end for loop;
            
            //int currRank = getRank(year, name, gender);
        //  System.out.println("  At year " + year + " name " + name + " gender " + gender + " ranks " + currRank + ". ");
            
            if(currRank != -1 && currRank < rank){
                rank = currRank;
                yearHigh = year;
            }//end if condition;
        
        }//end for File fi loop;
        
        return yearHigh;
    }
    
    public void testyearOfHighestRank(){
       String name = "Susan";
       String gender = "F";
       System.out.println(name +" is of highest rank in " + yearOfHighestRank(name,gender));
       }
    
       public double getAverageRank(String name, String gender) {       
           int rankSumma = 0;
           int counter = 0;
           //get the directory:
           DirectoryResource dr = new DirectoryResource();     
           //get the files
           for(File fi : dr.selectedFiles()){
                String fileName = fi.getName();         
                //get the year integer from the name of the file
                String year = fileName.replaceAll("[\\D]", "");
                rankSumma = rankSumma + getRank(year,name,gender);
                counter ++;
            }//end of fi loop;
           double rankS = rankSumma;
           double counterD = counter;            
           double averageRank = rankS/counterD;
           return averageRank;  
        }
        
       public void testgetAverageRank(){
           String name = "Robert";
           String gender = "M";
           double averageRank = getAverageRank(name,gender);
           System.out.println("Average rank of: " + name + " is " + averageRank);
        } 
       
        
        public int getTotalBirthRankedHigher(String year, String name, String gender){
        int rank = 0 ;
        
        int totalBirth = 0;
        
        String Filename = "data/yob" + year + ".csv";       
        FileResource fr = new FileResource(Filename);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if ( rec.get(1).equals(gender)) {
                 rank++;
                 
                 if (rec.get(0).equals(name)) return totalBirth;//}
                 totalBirth = totalBirth + Integer.parseInt(rec.get(2));
                }
                
            }
        return -1;
       }
    
       public void testgetTotalBirthRankedHigher() {
        //FileResource fr = new FileResource();
        //FileResource fr = new FileResource("data/yob1961.csv");
        String year = "1990";
        String gender = "M";
        String name = "Drew";
        int rank = getRank(year,name,gender);
        System.out.println("Total Births ranked higher than: " + name + " in " + year + " is " + getTotalBirthRankedHigher(year,name,gender));
    }
        
    }
       