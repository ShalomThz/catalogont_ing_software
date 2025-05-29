package modelos;

/**
 *
 * @author josha
 */
public class Usuario {
    private int id;
    private String username;
    private String email;
    private String contrasena;
  

    public Usuario(int id, String username, String email, String contrasena) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.contrasena = contrasena;
      
    }

    public Usuario(String username, String email, String contrasena) {
        this.username = username;
        this.email = email;
        this.contrasena = contrasena;
      
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
