package mundo;

import java.io.Serializable;

/**
 * Created by Choringa on 21/04/15.
 * Clase de mapeo para usuario
 */
public class Usuario implements Serializable {

    /**
     * Atributos del usuario
     */
    private int id;
    private String username, name;
    private int id_empresa;
    private int type_user;
    private String usuario_string;

    /**
     * Constructor de la clase
     * @param id
     * @param name
     * @param username
     * @param type_user
     * @param id_empresa
     */
    public Usuario(int id, String name, String username, int type_user, int id_empresa, String usuario_string) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.id_empresa = id_empresa;
        this.type_user = type_user;
        this.usuario_string = usuario_string;
    }

    //----------------------
    // GETTER Y SETTERS
    //----------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getType_user() {
        return type_user;
    }

    public void setType_user(int type_user) {
        this.type_user = type_user;
    }

    public String getUsuario_string() {
        return usuario_string;
    }

    public void setUsuario_string(String usuario_string) {
        this.usuario_string = usuario_string;
    }
}
