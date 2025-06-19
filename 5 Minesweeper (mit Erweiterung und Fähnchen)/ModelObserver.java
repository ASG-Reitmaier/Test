
/**
 * Grafikschnittstelle. Implementiert als Beobachter des Modells.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface ModelObserver
{
    /**
     * Methoden fuer das Model um seine View anzupassen.
     */
    public void an();
    public void aus();
    public void setIcon(int x, int y, String s);
    public void setSmiley(String s);
    public void setAnzeige(String t);
    
    
}
