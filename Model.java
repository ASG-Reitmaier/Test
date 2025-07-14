import java.util.Random;
/**
 * Schreibe hier eine Beschreibung
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Model
{
    private String icon;
    // Nur ein Observerplatz noetig.
    private ModelObserver view;
    // Spaltenanzahl
    private int x_max;
    // Zeilenanzahl
    private int y_max;
    // Liefert Zufallszahlen. Benoetigt beim Streuen der Bomben bei einem neuen Spiel.
    private Random zufallszahl;
    //Speichert die Anzahl der Bomben, die bei einem neuen Spiel erstellt werden
    private int bombenzahl;
    //Speichert, 
    private int[][] spielfeld;
    //Speichert, ob ein Feld schon geoeffnet wurd.
    private boolean[][] geoeffnet;
    //Speichert die noch geschlossenen Felder.
    private boolean[][] markiert;
    //Speichert die noch geschlossenen Felder.
    private int geschlossen;

    /**
     * Constructor for objects of class Model
     */
    public Model(int x_neu, int y_neu)
    {

        zufallszahl = new Random();
        x_max = x_neu;
        y_max = y_neu;

    } 

    public int getX_Max()
    {
        return this.x_max;
    }

    public int getY_Max()
    {
        return this.y_max;
    }

    //Mit dieser Methode koennen sich GUIs beim Model anmelden.
    public void viewAnmelden(View v)
    {
        view = v;
    }

    //0 entspricht keine Bombe. 1 entspricht Bombe! Damit kann man die Nachbarbomben eines Feldes recht einfach zaehlen.
    public void neuesSpiel(int x, int y, int anzahl, String s)
    {
        //Beide Felder wieder mit Nuller/false fuellen.
        geoeffnet = new boolean [x][y];
        spielfeld = new int [x][y];
        markiert = new boolean[x][y];
        geschlossen = x*y;
        bombenzahl = anzahl;
        bombenStreuen(x, y, anzahl);
        icon = s;

    }

    //Streut eine gewisse Anzahl an Bomben auf beliebige Felder des Spiels.
    public void bombenStreuen(int x, int y, int anzahl)
    {
        int bombenuebrig = anzahl;

        while(bombenuebrig != 0)
        {
            //Per Zufallszahl ein Feld auswählen und überprüfen, ob schon eine Bombe da liegt.
            int a = zufallszahl.nextInt(x_max);
            int b = zufallszahl.nextInt(y_max);

            if(spielfeld[a][b] != 1)
            {
                spielfeld[a][b] = 1;
                bombenuebrig --;
            }
        }
    }

    //Liefert die Anzahl der Nachbarbomben fuer ein Feld.
    public int gibNachbarBombenzahl(int xPos, int yPos)
    {
        int nachbarbomben = 0;
        for(int i = -1; i <=1 ; i++)
        {
            for(int j = -1 ; j <= 1 ; j++)
            {
                if(xPos+i >=0 && xPos+i < x_max && yPos + j >= 0 && yPos + j < y_max)
                {
                    //Erhoeht die nachbarbombenzahl um 1, wenn das Nachbarfeld eine Bombe ist.
                    nachbarbomben += spielfeld[xPos+i][yPos+j];
                }
            }
        }
        return nachbarbomben;
    }

    //Oeffnet ein Feld und untersucht die Auswirkungen, je nach Art des Feldes.
    public void oeffneFeld(int xPos, int yPos)
    {
        if(!geoeffnet[xPos][yPos] && !markiert[xPos][yPos]){
            int nachbarbomben = gibNachbarBombenzahl(xPos,yPos);
            geoeffnet[xPos][yPos] = true;
            if(spielfeld[xPos][yPos] == 1)
            {
                view.setIcon(xPos, yPos,"bombe.png");
                view.setSmiley("kaboom.png");
                view.setAnzeige("Du bist explodiert!");
                view.aus();
            }
            else{

                oeffneRekursiv(xPos, yPos);

                if(gewonnen()){
                    view.setSmiley("win.png");
                    view.setAnzeige("Alle Bomben gefunden!");
                    view.aus();
                }
            }
        }
    }

    public void markiereFeld(int xPos, int yPos)
    {
        if(!geoeffnet[xPos][yPos])
        {
            if(markiert[xPos][yPos]){
                markiert[xPos][yPos] = false;
                view.setIcon(xPos, yPos, icon + ".png");

            }
            else{
                markiert[xPos][yPos] = true;
                view.setIcon(xPos, yPos, "fahne"+icon+".png");
            }
        }
    }

    //Durchsucht das Spielfeld rekursiv nach freien Feldern, bis Felder wieder Bomben als Nachbarn haben.
    public void oeffneRekursiv(int xPos, int yPos)
    {
        int nachbarbombenzahl = gibNachbarBombenzahl(xPos,yPos);
        geoeffnet[xPos][yPos]=true;
        geschlossen--;
        view.setIcon(xPos, yPos, "b_" + gibNachbarBombenzahl(xPos,yPos)+".png");
        //Durchsuche alle Nachbarfelder, wenn kein Nachbarfeld eine Bombe ist.
        if(gibNachbarBombenzahl(xPos,yPos) == 0 ){
            for(int i = -1; i <=1 ; i++)
            {
                for(int j = -1 ; j <= 1 ; j++)
                {
                    //Wenn das Feld innerhalb des Bereichs liegt
                    if(xPos+i >=0 && xPos+i < x_max && yPos + j >= 0 && yPos + j < y_max)
                    {
                        //Wenn das Nachbarfeld keine Bombe ist und auch noch ungeoeffnet/unmarkiert ist, dann oeffne es.
                        if(spielfeld[xPos+i][yPos+j] != 1  &&  !geoeffnet[xPos+i][yPos+j] && !markiert[xPos+i][yPos+j]){

                            oeffneRekursiv(xPos+i, yPos+j);
                        }
                    }
                }
            }
        }
    }

    //Ueberprueft, ob die geschlossenen Felder und die Bombenzahl uebereinstimmen. Wenn ja, hat man gewonnen.
    public boolean gewonnen()
    {

        if(bombenzahl < geschlossen)
        {
            view.setAnzeige("Noch " + geschlossen + " geschlossen! Bombenzahl: " + bombenzahl);
        }

        return (bombenzahl == geschlossen);

    }
}
