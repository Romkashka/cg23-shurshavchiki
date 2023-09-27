package ru.shurshavchiki.Frames;

import ru.shurshavchiki.PanelMediator;

import java.awt.*;

import javax.swing.*;


public class ImageFrame extends JFrame {


    public ImageFrame()
    {
    }
    
    public void create(){

    	this.setJMenuBar(PanelMediator.getInstance().getSettingPanel().getMenuBar());
    	this.getContentPane().add(PanelMediator.getInstance().getOneToolPanel(), BorderLayout.EAST);
        this.getContentPane().add(PanelMediator.getInstance().getInstrumentPanel(), BorderLayout.WEST);
        this.getContentPane().add(PanelMediator.getInstance().getScrollPane(), BorderLayout.CENTER);


        var icon = new ImageIcon("C:/Users/1/Desktop/cg23-shurshavchiki/Shurshavchiki/GUI/ru.shurshavchiki.Resources/icon.png");
        
        this.setIconImage(icon.getImage());
        
        setTitle("Paint by shurshavchiki");

        Toolkit kit = Toolkit.getDefaultToolkit();


        this.setSize(Math.min(kit.getScreenSize().getSize().width, 800), Math.min(kit.getScreenSize().getSize().height, 600));
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}