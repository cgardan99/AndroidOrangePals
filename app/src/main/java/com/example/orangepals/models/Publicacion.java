package com.example.orangepals.models;

import java.util.List;

public class Publicacion {
    private String titulo;
    private String descripcion;
    private Integer corazones;
    private Integer comentarios;
    private List<Integer> categorias;
    private Boolean saved;

    public Publicacion(String titulo, String descripcion, Integer corazones, Integer comentarios, Boolean saved) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.corazones = corazones;
        this.comentarios = comentarios;
        this.saved = saved;
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
}
