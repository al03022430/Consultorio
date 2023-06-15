public class Recepcion {
    public Recepcion (String n, String f){
        nombre =n;
        Funciones =f;
    }

    public String toString(){
        return "Recepcionista : " + nombre + " Funcioones: " + Funciones;
    }

    private String nombre;
    private String Funciones;
}
