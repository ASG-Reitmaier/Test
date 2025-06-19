
/**
 * Der Controller stellt das Bindeglied zwischen Model und View dar.
 * 
 */
public class Controller
{
    // instance variables - replace the example below with your own
    private Model model;
    private View view;

    /**
     * Constructor for objects of class Controller
     */
    public Controller(Model m)
    {
        model = m;
        view = new View(m.getX_Max(),m.getY_Max(),m,this);

    }
    //Startet ein neues Spiel mit festgelegter Bombenanzahl.
    public void starten(int schwierigkeitsgrad)
    {
        int x = model.getX_Max();
        int y = model.getY_Max();
        
        switch (schwierigkeitsgrad){
            case 0:
            model.neuesSpiel(x, y ,30, "gras");
            view.neu(x,y, "gras.png");
            view.setSmiley("easy.png");
            view.setAnzeige("Los geht's!");
            view.an();
            break;

            case 1:
            model.neuesSpiel(x, y ,50, "ziegel");
            view.neu(x,y, "ziegel.png");
            view.setSmiley("medium.png");
            view.setAnzeige("Los geht's!");
            view.an();
            break;

            case 2:
            model.neuesSpiel(x, y ,70, "teer");
            view.neu(x,y, "teer.png");
            view.setSmiley("hard.png");
            view.setAnzeige("Los geht's!");
            view.an();
            break;

        }
    }

    //Das Model liefert das passende Icon fuer den Button als Datename des Bildes. Das Bild wird an die View weitergegeben.
    public void oeffneFeld(int x, int y)
    {
        model.oeffneFeld(x,y);
    }
    
    public void markiereFeld(int x, int y)
    {
        model.markiereFeld(x,y);
    }

}

