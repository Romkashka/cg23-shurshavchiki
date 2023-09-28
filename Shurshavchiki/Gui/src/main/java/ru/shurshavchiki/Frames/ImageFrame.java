package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.PanelMediator;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;


public class ImageFrame extends JFrame {

    public ImageFrame()
    {
    }
    
    public void create() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            new ExceptionHandler().handleException(e);
        }

        this.setJMenuBar(PanelMediator.getInstance().getSettingPanel().getMenuBar());
//    	this.getContentPane().add(PanelMediator.getInstance().getOneToolPanel(), BorderLayout.EAST);
//        this.getContentPane().add(PanelMediator.getInstance().getInstrumentPanel(), BorderLayout.WEST);
        this.getContentPane().add(PanelMediator.getInstance().getScrollPane(), BorderLayout.CENTER);

        var icon = new ImageIcon("/Resources/icon.png");
        
        this.setIconImage(icon.getImage());
        
        setTitle("Paint by shurshavchiki");

        Toolkit kit = Toolkit.getDefaultToolkit();


        this.setSize(Math.min(kit.getScreenSize().getSize().width, 800), Math.min(kit.getScreenSize().getSize().height, 600));
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}