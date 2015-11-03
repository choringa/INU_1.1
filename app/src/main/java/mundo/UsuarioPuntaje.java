package mundo;

/**
 * Created by Choringa on 24/09/15.
 */
public class UsuarioPuntaje {

    //Atributos
    private int points;
    private String nombre;
    private int idUsuario;

    //Constructor
    public UsuarioPuntaje(int idUsuario, int points, String nombre) {
        this.points = points;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }

    //Metodos
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
