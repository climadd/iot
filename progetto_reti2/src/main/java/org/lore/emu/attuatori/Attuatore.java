package org.lore.emu.attuatori;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;


//select x ID attuatore


public class Attuatore {
    public static void main(String[]args) {



        JFrame finestra = new JFrame();
        finestra.setSize(600,400);
        JPanel panel=new JPanel();
        BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);

        panel.setLayout(layout);


        AttuatConfig istanza = new AttuatConfig();
        JLabel title = new JLabel("ATTUATORE VISUALIZZATO: "+ istanza.getNameID() );
        JSeparator separator = new JSeparator();

        boolean u = true;
        boolean i = false;
        boolean t = true;

        JLabel nameUmi = new JLabel("UMIDITA");
        JLabel ledUmi = new JLabel("⬤");
        if (u) ledUmi.setForeground(Color.GREEN);
        else ledUmi.setForeground(Color.RED);
        JLabel labelLvlUmi = new JLabel("LIVELLO UMIDITA: "+ istanza.getLvlUmi()+"%");
        JLabel labelModeUmi = new JLabel("MODALITA UMIDITA: "+ istanza.getModeUmi());

        JLabel nameIll = new JLabel("ILLUMINAZIONE");
        JLabel ledIll = new JLabel("⬤");
        if (i) ledIll.setForeground(Color.GREEN);
        else ledIll.setForeground(Color.RED);

        JLabel labelLvlIll = new JLabel("LIVELLO ILLUMINAZIONE: "+ istanza.getLvlIll());
        JLabel labelModeIll = new JLabel("MODALITA ILLUMINAZIONE: "+ istanza.getModeIll());

        JLabel nameTem = new JLabel("TEMPERATURA");
        JLabel ledTem = new JLabel("⬤");
        if (t) ledTem.setForeground(Color.GREEN);
        else ledTem.setForeground(Color.RED);
        JLabel labelLvlTem = new JLabel("LIVELLO TEMPERATURA: "+ istanza.getLvlTem()+"°C");
        JLabel labelModeTem = new JLabel("MODALITA TEMPERATURA: "+ istanza.getModeTem());



//		panel.add(icona);
//		panel.add(labello);
//		panel.add(form);




        panel.add(title);
        panel.add(separator);
        panel.add(nameUmi);
        panel.add(ledUmi);
        panel.add(labelLvlUmi);
        panel.add(labelModeUmi);

        panel.add(nameIll);
        panel.add(ledIll);
        panel.add(labelLvlIll);
        panel.add(labelModeIll);

        panel.add(nameTem);
        panel.add(ledTem);
        panel.add(labelLvlTem);
        panel.add(labelModeTem);

        finestra.add(panel);




        finestra.setVisible(true);

    }

}