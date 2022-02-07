/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author abarc
 */
public class ModelTableView {

    private String idAlumnos;
    private String nombre;
    private String Apellidos;
    private String materia;
    private Double calificacion;
    private String estatus;

    public ModelTableView() {
    }

    public ModelTableView(String idAlumnos, String nombre, String Apellidos, String materia, Double calificacion, String estatus) {
        this.idAlumnos = idAlumnos;
        this.nombre = nombre;
        this.Apellidos = Apellidos;
        this.materia = materia;
        this.calificacion = calificacion;
        this.estatus = estatus;
    }

    /**
     * @return the idAlumnos
     */
    public String getIdAlumnos() {
        return idAlumnos;
    }

    /**
     * @param idAlumnos the idAlumnos to set
     */
    public void setIdAlumnos(String idAlumnos) {
        this.idAlumnos = idAlumnos;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the Apellidos
     */
    public String getApellidos() {
        return Apellidos;
    }

    /**
     * @param Apellidos the Apellidos to set
     */
    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    /**
     * @return the materia
     */
    public String getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @return the calificacion
     */
    public Double getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    
}
