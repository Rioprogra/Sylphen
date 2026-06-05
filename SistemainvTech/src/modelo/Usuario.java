package modelo;

/**
 *
 * @author miche
 */
public class Usuario {
    
    private int idusuario;
    private String nombre;
    private String apellidos;
    private String nickname;
    private String password;
    
    //constructor
    public Usuario(){
    this.idusuario = 0;
    this.nombre = "";
    this.apellidos = "";
    this.nickname = "";
    this.password = ""; 
    }
    
    //getter y setter

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
