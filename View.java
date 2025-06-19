/**
 *Text genereted by Simple GUI Extension for BlueJ
 */ 
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;

public class View extends JFrame implements ModelObserver {
    
    private JMenuBar menuBar;
    private JButton[][] fields;
    private JPanel contentPane;
    private JLabel smiley;
    private JLabel anzeige;
    private Controller control;
    private Model model;
    private boolean an;

    //Constructor 
    public View(int max_x, int max_y, Model m, Controller c){
        an = false;
        model = m;
        model.viewAnmelden(this);
        control = c;
        this.setTitle("ASG Minesweeper");
        this.setSize(25*max_x,25*max_y + 128);
        this.setResizable(false);

        //
        //menu generate method
        generateMenu();
        this.setJMenuBar(menuBar);

        //pane with null layout
        contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(25*max_x,25*max_y+128));
        contentPane.setBackground(new Color(192,192,192));

        smiley = new JLabel();
        smiley.setBounds(0,0,128,128);
        smiley.setBackground(new Color(153,255,204));
        smiley.setForeground(new Color(255,255,255));
        smiley.setEnabled(true);
        smiley.setFont(new Font("sansserif",0,12));
        //smiley.setText("");
        smiley.setIcon(new ImageIcon("smiley.png"));
        smiley.setVisible(true);
        
        anzeige = new JLabel();
        anzeige.setBounds(140,0,25*max_x-140,128);
        anzeige.setBackground(new Color(153,255,204));
        anzeige.setForeground(new Color(0,0,0));
        anzeige.setEnabled(true);
        anzeige.setFont(new Font("sansserif",0,18));
        anzeige.setText("Starte ein neues Spiel!");
        anzeige.setVisible(true);
        
        //adding components to contentPane panel
        contentPane.add(smiley);
        contentPane.add(anzeige);
        
        fields = new JFieldButton[max_x][max_y];

        for (int i = 0 ; i < max_x ; i++){
            for (int j = 0 ; j < max_y ; j++){
                setField(i,j);
            }
        }

        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();

        this.setVisible(true);

    }

    public void an()
    {
        this.an = true;
    }

    public void aus()
    {
        this.an = false;
    }

       
    public void setIcon(int x, int y, String i)
    {
        this.fields[x][y].setIcon(new ImageIcon(i)); 
    }
    
    public void setSmiley(String s)
    {
        this.smiley.setIcon(new ImageIcon(s));
    }
    
    public void setAnzeige(String t)
    {
        this.anzeige.setText(t);
    }

    //Wird vom Controller aufgerufen.
    public void neu(int x, int y, String b){
        for (int i = 0 ; i < x ; i++){
            for (int j = 0 ; j < y ; j++){
                fields[i][j].setIcon(new ImageIcon(b)); 
            }
        }
    }

    //Wird im Konstruktor aufgerufen.
    public void setField(int x, int y)
    {         
        fields[x][y] = new JFieldButton(x,y);
        fields[x][y].setBounds(x*25,y*25+128,25,25);
        fields[x][y].setBackground(new Color(214,217,223));
        fields[x][y].setForeground(new Color(0,0,0));
        fields[x][y].setEnabled(true);
        fields[x][y].setFont(new Font("DejaVu Sans",0,12));
        fields[x][y].setText("");
        fields[x][y].setVisible(true);
        fields[x][y].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    openField(evt);
                }
            });
            
            fields[x][y].addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent evt) {
                    markField(evt);
                }
                public void mousePressed(MouseEvent evt) {}
                public void mouseReleased(MouseEvent evt) {}
                public void mouseEntered(MouseEvent evt) {}
                public void mouseExited(MouseEvent evt) {}
            });

        //adding components to contentPane panel
        contentPane.add(fields[x][y]);

    }

    //Method actionPerformed for fields[x][y]
    private void openField (ActionEvent evt) {
        if(an)
        {
            JFieldButton b = (JFieldButton) evt.getSource();
            control.oeffneFeld(b.x, b.y); 
        }
    }
    
    //Method actionPerformed for fields[x][y]
    private void markField (MouseEvent evt) {
        if(an && evt.getButton()==3)
        {
            JFieldButton b = (JFieldButton) evt.getSource();
            control.markiereFeld(b.x, b.y); 
        }
    }
    
    

    //Method actionPerformed for easy
    private void newGame0 (ActionEvent evt) {
        control.starten(0);
    }

    //Method actionPerformed for easy
    private void newGame1 (ActionEvent evt) {
        control.starten(1);
    }

    //Method actionPerformed for easy
    private void newGame2 (ActionEvent evt) {
        control.starten(2);
    }

    //method for generate menu
    public void generateMenu(){
        menuBar = new JMenuBar();

        JMenu open = new JMenu("Neues Spiel");
        //JMenu exit = new JMenu("Exit");
        //JMenu tools = new JMenu("Tools");
        //JMenu help = new JMenu("Help");

        JMenuItem easy = new JMenuItem("Leicht   ");
        easy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    newGame0(evt);
                }
            });

        JMenuItem medium = new JMenuItem("Mittel   ");
        medium.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    newGame1(evt);
                }
            });

        JMenuItem hard = new JMenuItem("Schwer   ");
        hard.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    newGame2(evt);
                }
            });

        //JMenuItem save = new JMenuItem("Save   ");
        //JMenuItem schliessen = new JMenuItem("Schliessen   ");
        //JMenuItem preferences = new JMenuItem("Preferences   ");
        //JMenuItem about = new JMenuItem("About   ");

        
        open.add(easy);
        open.add(medium);
        open.add(hard);
        //file.add(save);
        //file.addSeparator();
        //file.add(exit);
        //exit.add(schliessen);
        //tools.add(preferences);
        //help.add(about);

        menuBar.add(open);
        //menuBar.add(tools);
        //menuBar.add(help);
    }

}