public class Usuario {
    private String nombre;
    private String password;

    public Usuario(String nom, String pass) {
        nombre = nom;
        password = pass;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto instanceof Usuario) {
            Usuario otroAdministrator = (Usuario)objeto;
            if (nombre.equals(otroAdministrator.nombre) && password.equals(otroAdministrator.password))
                return true;
            else
                return false;
        }
        else
            return false;
    }

}
