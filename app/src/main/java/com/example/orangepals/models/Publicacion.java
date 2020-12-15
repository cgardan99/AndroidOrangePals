package com.example.orangepals.models;

import java.util.List;

public class Publicacion {
    private Integer id_publicacion;
    private String titulo;
    private String descripcion;
    private Integer corazones;
    private Integer comentarios;
    private List<Integer> categorias;
    private Boolean saved;
    private boolean es_mio;
    private String fecha;

    public Publicacion(String titulo, String descripcion, Integer corazones, Integer comentarios, Boolean saved, Integer id_publicacion, boolean es_mio, String fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.corazones = corazones;
        this.comentarios = comentarios;
        this.saved = saved;
        this.id_publicacion = id_publicacion;
        this.es_mio = es_mio;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEs_mio() {
        return es_mio;
    }

    public void setEs_mio(boolean es_mio) {
        this.es_mio = es_mio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCorazones() {
        return corazones;
    }

    public void setCorazones(Integer corazones) {
        this.corazones = corazones;
    }

    public Integer getComentarios() {
        return comentarios;
    }

    public void setComentarios(Integer comentarios) {
        this.comentarios = comentarios;
    }

    public List<Integer> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Integer> categorias) {
        this.categorias = categorias;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Integer getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(Integer id_publicacion) {
        this.id_publicacion = id_publicacion;
    }
}
