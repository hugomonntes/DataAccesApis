
import java.io.Serializable;

public class Persona implements Serializable {
    private int id;
    private String nombre;
    private boolean casado;
    private String sexo;

    public Persona(int id, String nombre, boolean casado, String sexo) {
        this.id = id;
        this.nombre = nombre;
        this.casado = casado;
        this.sexo = sexo;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

    public boolean isCasado() {
        return casado;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }
}
