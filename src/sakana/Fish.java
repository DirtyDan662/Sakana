package sakana;
import java.util.ArrayList;
public class Fish {
    private int x,xMin,xMax,xVelocity,y;
    private String name;
    private int health;
    private String leftFaceImageLocation = "/fish_50.png";
    private String rightFaceImageLocation = "/flipped_fish_50.png";
    private String defaultSettings ="defaultFishData.txt";
    private String savedSettings = System.getProperty("user.home")+"/fishData.txt";
    private FileHandler file;

    public Fish() {
        xMin = 100;
        xMax = 400;
        file = new FileHandler();
        setData();
    }
    
    public void swim() {
        if(x <= xMin ) {
            xVelocity *= -1;
        }
        else if(x >= xMax) {
            xVelocity *= -1;
        }
        x+= xVelocity;
        health-=2;
    }
    public void feed() {
        health += 10;
        if(health > 100) {
            health = 100;
        }
    }
    public void giveLove() {
    	health += 1;
    }
    
    public boolean isAlive() {
    	if(health <= 0) {
    		return false;
    	}
    	else {return true;}
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getHealth() {
        if(health > 100) {
        	health = 100;
        }
    	return health;
    }
    
    public String getName() {
        return name;
    }
    public int getDirection() {
    	 if(xVelocity < 0) {
             return -1;
         }
         else {return 1;}
    }
    public String getImageLocation() {
        if(xVelocity < 0) {
            return leftFaceImageLocation;
        }
        else {return rightFaceImageLocation;}
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    private void setData() {
        if(file.readSavedFile(savedSettings)) {
            name = file.getName();
            x = file.getX();
            y = file.getY();
            xVelocity = file.getXVel();
            health = file.getHealth();
        }
        else {
            resetData();
        }
    }
    public void resetData() {
		/*file.readFile(defaultSettings);
		name = file.getName();
	    x = file.getX();
	    y = file.getY();
	    xVelocity = file.getXVel();
	    health = file.getHealth();*/
    	name = "Phill";
    	x = 320;
    	y = 420;
    	xVelocity = -10;
    	health = 100;
    	
    }
    public void saveData() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(x);
        data.add(y);
        data.add(xVelocity);
        data.add(health);
        
        file.writeFile(savedSettings, name, data);
    }
    
    public void deleteData() {
    	file.deleteFile(savedSettings);
    }
    

}
