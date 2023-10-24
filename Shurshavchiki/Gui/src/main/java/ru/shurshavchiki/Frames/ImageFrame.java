package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.InstrumentPanel;
import ru.shurshavchiki.Panels.SettingPanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

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

        PanelMediator.getInstance().setSettingPanel(new SettingPanel());
        PanelMediator.getInstance().setInstrumentPanel(new InstrumentPanel());

        this.setJMenuBar(PanelMediator.getInstance().getSettingPanel().getMenuBar());
    	this.getContentPane().add(PanelMediator.getInstance().getOneToolPanel(), BorderLayout.EAST);
        this.getContentPane().add(PanelMediator.getInstance().getInstrumentPanel(), BorderLayout.WEST);
        this.getContentPane().add(PanelMediator.getInstance().getScrollPane(), BorderLayout.CENTER);
        PanelMediator.getInstance().getInstrumentPanel().setPreferredSize(new Dimension(64, 600));
        PanelMediator.getInstance().getOneToolPanel().setPreferredSize(new Dimension(200, 600));

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("icon.png");
        if (resource != null) {
            var icon = new ImageIcon(resource.getPath());
            this.setIconImage(icon.getImage());
        }

        var rand = new Random();
        if (Math.abs(rand.nextLong()) % 10 == 4){
            setTitle("Shurshavchiki: Also try Draw.Me!");
        }else{
            setTitle("Shurshavchiki");
        }

        Toolkit kit = Toolkit.getDefaultToolkit();


        this.setSize(Math.min(kit.getScreenSize().getSize().width, 800), Math.min(kit.getScreenSize().getSize().height, 600));
        
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PanelMediator.getInstance().exit();
            }
        });
    }
}