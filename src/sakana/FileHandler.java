package sakana;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileHandler {
    private File textFile;
    private Scanner reader;
    private FileWriter writer;
    private String fishName;
    private String filename;
    /*
     * data points are as follows
     * 0,x // 1,y // 2,xVel // 3,health
     */
    private ArrayList<Integer> data;
    
    public FileHandler() {
        //textFile = new File(filename);
        data = new ArrayList<>();
    }
    public boolean readSavedFile(String filename) {
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            reader = new Scanner(inputStream);
            while(reader.hasNext()) {
                if(reader.hasNextInt()) {
                    data.add(reader.nextInt());
                }
                else {
                    fishName = reader.next();
                }
            }
            reader.close();
            inputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch(NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean readFile(String filename){
        
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(filename);
            
            reader = new Scanner(inputStream);
            while(reader.hasNext()) {
                if(reader.hasNextInt()) {
                    data.add(reader.nextInt());
                }
                else {
                    fishName = reader.next();
                }
            }
            reader.close();
            inputStream.close();
            return true;
        }
        
        catch(FileNotFoundException e) {
            System.out.println("An Error Occured While Lookin for Dis File " + filename);
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch(NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        
        
    }
    
    private boolean hasData() {
        if(data.size()>0) {
            return true;
        }
        else {
            System.out.println("Failed to load fish data");
            return false;
        }
    }
    
    public int getX(){
        if(hasData()) {
            return data.get(0);
        }
        else {return 0;}
    }
    public int getY(){
        if(hasData()) {
            return data.get(1);
        }
        else {return 0;}
    }
    public int getXVel(){
        if(hasData()) {
            return data.get(2);
        }
        else {return 0;}
    }
    public int getHealth(){
        if(hasData()) {
            return data.get(3);
        }
        else {return 0;}
    }
    public String getName() {
        if(fishName != null) {
            return fishName;
        }
        else {
            return "Failed to load fish data";
        }
    }
    
    public void writeFile (String filename, String fishName, ArrayList<Integer> data) {
        try{
            
            writer= new FileWriter(filename);
            writer.write(fishName);
            writer.write(System.lineSeparator());
            for(Integer i : data) {
                writer.write(i.toString());
                writer.write(System.lineSeparator());
            }
            writer.close();
            System.out.println(filename + " successfully written");
        }
        catch(IOException e) {
            System.out.println("lol aye idk what happend but here dis info");
            e.printStackTrace();
        }
    }
    public void deleteFile(String filename) {
    	try {
    		File file = new File(filename);
        	if(file.delete()) {
        		System.out.println(filename + " deleted");
        	}
        	else {
        		System.out.println("Error Deleting file " + filename);
        	}
        	
    	} catch(NullPointerException e) {
    		e.printStackTrace();
    	}
    	
    }
}
