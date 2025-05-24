package modelos;

/**
 * Modelo de la entidad Usuario.
 * Utilizado para autenticación y gestión de roles.
 */
public class Usuario {
    private int id;
    private String username;
    private String email;
    private String contrasena;
  

    // Constructor completo
    public Usuario(int id, String username, String email, String contrasena) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.contrasena = contrasena;
      
    }

    // Constructor sin ID (para creación)
    public Usuario(String username, String email, String contrasena) {
        this.username = username;
        this.email = email;
        this.contrasena = contrasena;
      
    }

    // Getters
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



    // Setters
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
