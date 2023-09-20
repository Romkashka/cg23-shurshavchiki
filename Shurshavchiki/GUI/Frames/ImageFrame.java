package GUI.Frames;

import java.awt.BorderLayout;

import javax.swing.*;

import GUI.PanelMediator;
import GUI.Panels.DrawingPanel;
import GUI.Panels.InstrumentPanel;
import GUI.Panels.OneToolPanel;
import GUI.Panels.SettingPanel;

public class ImageFrame extends JFrame {
    
    public ImageFrame()
    {
    }
    
    public void create(){
    	
        this.getContentPane().add(PanelMediator.INSTANCE.getSettingPanel(), BorderLayout.NORTH);
        this.getContentPane().add(PanelMediator.INSTANCE.getOneToolPanel(), BorderLayout.EAST);
        this.getContentPane().add(PanelMediator.INSTANCE.getInstrumentPanel(), BorderLayout.WEST);
        this.getContentPane().add(PanelMediator.INSTANCE.getDrawingPanel(), BorderLayout.CENTER);

        var icon = new ImageIcon("C:/Users/1/Desktop/cg23-shurshavchiki/Shurshavchiki/GUI/Resources/icon.png");
        
        this.setIconImage(icon.getImage());
        
        setTitle("Paint by shurshavchiki");

        this.setSize(800, 600);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

}