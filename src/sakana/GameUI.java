package sakana;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;

import java.io.InputStream;
import java.util.ArrayList;

public class GameUI {
    private Display display;
    private Shell shell;
    private Label background, nameText, healthText;
    private Image backgroundImage, fishImage;
    private GC gc;
    private Button feed,tricks,name;
    private Color coolPink, fontColor,buttonColor;
    private Font font, buttonFont,smallButtonFont;
    private Fish fish;
    private Canvas fishCanvas;
    private int dialogBoxWidth = 300;
    private int dialogBoxLength = 150;
    private int dialogBoxX = 300;
    private int dialogBoxY = 300;
    public GameUI() {
        fish = new Fish();
        display = new Display();
        shell = new Shell(display, SWT.SHELL_TRIM);
        background = new Label(shell, SWT.NONE);
        nameText = new Label(shell, SWT.NONE);
        healthText = new Label(shell, SWT.NONE);
        backgroundImage = new Image(display,this.getClass().getResourceAsStream("/fishBowl_600x600.jpg"));
        coolPink = new Color(Display.getCurrent(),248,202,230);
        buttonColor = coolPink;
        fontColor = display.getSystemColor(SWT.COLOR_WHITE);
        feed = new Button(shell, SWT.PUSH);
        tricks = new Button(shell, SWT.PUSH);
        name = new Button(shell, SWT.PUSH);
        buttonFont = new Font(shell.getDisplay(), "Helvetica", 22, SWT.BOLD);
        smallButtonFont = new Font(shell.getDisplay(), "Helvetica", 12, SWT.BOLD);
        font = new Font(shell.getDisplay(), "Helvetica", 20, SWT.NORMAL);
        fishCanvas = new Canvas(shell, SWT.NONE);
    }
    
    
    public void setDrawing() {
        shell.setSize(600,730);
        shell.setBackground(coolPink);
        //gc.drawImage(backgroundImage, 0, 0);
        //250,450
        //gc.drawImage(fishImage, fish.getX(), fish.getY());
        background.setImage(backgroundImage);
        background.moveBelow(shell);
        background.setSize(600, 600);
        
        drawFish();
        setButtons();
        setMenu();
        updateName();
        updateHealth();
        
        shell.setText("Sakana");
        //shell.pack();
        shell.open ();
        
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }
    
    private void drawFish() {
        fishCanvas.moveAbove(background);
        fishCanvas.setBounds(0, 0, 600, 600);
        fishCanvas.setBackgroundImage(backgroundImage);
        fishCanvas.addPaintListener(new PaintListener() { 
            public void paintControl(PaintEvent e) { 
                fishImage = new Image(display,this.getClass().getResourceAsStream(fish.getImageLocation()));
                e.gc.drawImage(fishImage, fish.getX(),fish.getY());
                
            }
        });
        fishCanvas.addMouseListener(new MouseListener()
    	{	@Override
    		public void mouseDown(MouseEvent e)
    		{
    			System.out.println("Mouse Down.");
    			drawHearts();
    		}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				drawHearts();
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				drawHearts();
			}
           });
        //shell.redraw();
        //shell.update();
        System.out.println("Fish Drawn!");
        
    }
    
    private void drawHearts() {
        Image heartImage = new Image(display,this.getClass().getResourceAsStream("/heart_65.png"));
    	gc = new GC(fishCanvas);
    	int offset ;
    	if(fish.getDirection()< 0) {
    		offset = -7;
    	}
    	else {offset = 70;}
        gc.drawImage(heartImage, fish.getX()+offset,fish.getY()-43);
        fish.giveLove();
        updateHealth();
        fishCanvas.update();
        try {
			Thread.sleep(100);
			
	        
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        gc.dispose();
        heartImage.dispose();
      //fishCanvas.redraw();
        
    	
        /*fishCanvas.addPaintListener(new PaintListener() { 
            public void paintControl(PaintEvent e) { 
            	System.out.println("inside oaint lister");
                Image heartImage = new Image(display,this.getClass().getResourceAsStream("/heart_65.png"));
                e.gc.drawImage(heartImage, fish.getX()-10,fish.getY()-10);
                fishCanvas.redraw();
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                e.gc.dispose();
                fishCanvas.redraw();
            	}
            });*/
    }
    
    private void setButtons() {
        //75 space between
        int space = 0;
        int width = 200;
        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(feed);
        buttons.add(tricks);
        buttons.add(name);
        
        
        for(int i=0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setBounds(width*i, 600, width, 100);
            //buttons.get(i).setBackground();
            button.setFont(buttonFont);
            button.setForeground(fontColor);
            button.setBackground(buttonColor);
        }
        
        //feed.setBounds(0, 600, width, 100);
        feed.setText("Feed");
        feed.addSelectionListener(new SelectionAdapter() {       
            @Override
            public void widgetSelected(SelectionEvent e) {
                    feed();
                }
            });
        
        //tricks.setBounds(width+space, 600, width, 100);
        tricks.setText("Play");
        tricks.addSelectionListener(new SelectionAdapter() {       
            @Override
            public void widgetSelected(SelectionEvent e) {
                    swim();
                }
            });
        
        //name.setBounds(width*2+space,600, width, 100);
        name.setText("Name");
        name.addSelectionListener(new SelectionAdapter() {       
            @Override
            public void widgetSelected(SelectionEvent e) {      
                renameFish();
                }
            });

    }
    private void setMenu() {
    	Menu menuBar = new Menu(shell,SWT.BAR);
    	MenuItem saveItem = new MenuItem(menuBar, SWT.PUSH);
    	MenuItem loadItem = new MenuItem(menuBar, SWT.PUSH);
    	
    	saveItem.setText("Save");
    	saveItem.addSelectionListener(new SelectionAdapter() {       
            @Override
            public void widgetSelected(SelectionEvent e) { 
                fish.saveData();
                }
            });
    	loadItem.setText("Load Defaults");
    	loadItem.addSelectionListener(new SelectionAdapter() {       
            @Override
            public void widgetSelected(SelectionEvent e) { 
                resetFish();
                }
            });
    	
    	shell.setMenuBar(menuBar);
    }
    private void updateHealth() {
        if(fish.isAlive()) {
            healthText.setFont(font);
            healthText.setForeground(fontColor);
            healthText.moveAbove(fishCanvas);
            healthText.setBounds(450, 2, 250, 22);
            healthText.setText("Health: " + fish.getHealth());
        }
        else {showDeathScreen();}
        
    }
        
    
    
    private void updateName() {
    	nameText.setFont(font);
        nameText.setForeground(fontColor);
        nameText.moveAbove(fishCanvas);
        nameText.setBounds(0, 2, 250, 22);
        nameText.setText("Name: " + fish.getName());
    }
    
    private void redrawFish() {
        fishCanvas.redraw();
        fishCanvas.update();
        System.out.println("End of redrawFish");
    }
    
    private void renameFish() {
        if(fish.isAlive()) {
        	final Shell renameShell = new Shell(display, SWT.SHELL_TRIM);
            Label instructLabel = new Label(renameShell,SWT.CENTER);
            instructLabel.setFont(font);
            instructLabel.setForeground(fontColor);
            instructLabel.setBounds(0, 10, 300, 30);
            instructLabel.setText("Rename " + fish.getName());
            
            final Text t = new Text(renameShell, SWT.BORDER);
            t.setText("Enter New Name Here");
            t.setBounds(0, 60, 200, 30);
            
            Button submitButton = new Button(renameShell,SWT.PUSH);
            submitButton.setForeground(fontColor);
            submitButton.setBackground(buttonColor);
            submitButton.setFont(smallButtonFont);
            submitButton.setBounds(200, 60, 100, 30);
            submitButton.setText("Submit!");
            submitButton.addSelectionListener(new SelectionAdapter() {       
                @Override
                public void widgetSelected(SelectionEvent e) { 
                    String newName = t.getText();
                    fish.setName(newName);
                    updateName();
                    renameShell.close();
                    }
                });
            
            renameShell.pack();

            // Set framework size.
            renameShell.setBounds(dialogBoxX, dialogBoxY, dialogBoxWidth, dialogBoxLength);
            //renameShell.setSize(150,50);
            renameShell.setBackground(coolPink);
            renameShell.setText("Rename " + fish.getName());
            renameShell.open();

            while (!renameShell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        }
        else {showDeathScreen();}
    	
    }
    
    
    private void swim() {
        fish.swim();
            try {
                Thread.sleep(100);
                System.out.println("In Swim");
                System.out.println(fish.getX()+" "+ fish.getY());
                redrawFish();
                updateHealth();
            
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        System.out.println("Out Of Loop: " + fish.getX() + fish.getY());
        
    }
    private void feed() {
        fish.feed();
        updateHealth();
    }
    
    private void showDeathScreen() {
    	healthText.setText("Health: 0");
    	Shell deathShell = new Shell(display, SWT.SHELL_TRIM);
    	Label deathLabel = new Label(deathShell, SWT.CENTER);
    	
    	deathLabel.setBounds(0, 3, 300, 30);
    	deathLabel.setFont(font);
    	deathLabel.setForeground(fontColor);
    	deathLabel.setText(fish.getName() + " has died :(");
    	
    	Button quitButton = new Button(deathShell, SWT.PUSH);
    	Button replayButton = new Button(deathShell, SWT.PUSH);
    	ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(quitButton);
        buttons.add(replayButton);
        int width = dialogBoxWidth / 2;
        for(int i=0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setBounds(width*i, 30, width, dialogBoxLength - 30);
            button.setFont(buttonFont);
            button.setForeground(fontColor);
            button.setBackground(buttonColor);
        }
        quitButton.setText("Quit");
        quitButton.addSelectionListener(new SelectionAdapter() {       
            @Override
            public void widgetSelected(SelectionEvent e) { 
                System.out.println("QUIT CLICk");
            	fish.deleteData();
                deathShell.close();
                shell.close();
                display.close();
                }
            });
        
        
        replayButton.setText("Retry");
        replayButton.addSelectionListener(new SelectionAdapter() {       
            @Override
            public void widgetSelected(SelectionEvent e) { 
                System.out.println("REPLAY CLICk");
            		resetFish();
            		fish.deleteData();
            		deathShell.close();
                }
            });
        
        deathShell.setBounds(dialogBoxX, dialogBoxY, dialogBoxWidth, dialogBoxLength);
        deathShell.setBackground(coolPink);
        deathShell.pack();
        deathShell.setText(fish.getName() + " has died :(");
        deathShell.open();
        while (!deathShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    private void resetFish() {
    	fish.resetData();
		drawFish();
		updateName();
		updateHealth();
    }
    
}