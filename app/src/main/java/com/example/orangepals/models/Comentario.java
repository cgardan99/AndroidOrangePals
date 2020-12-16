package com.example.orangepals.models;

import java.util.Date;

public class Comentario {
    private Integer comentario_id;
    private Integer usuario_id;
    private String username;
    private String texto;
    private Date fecha;
    private Integer likes;
    private boolean es_mio;
    private boolean like_mio;
    private Integer pid;

    public Comentario(Integer comentario_id, Integer usuario_id, String username, String texto, String fecha, Integer likes, boolean es_mio, boolean like_mio, Integer pid) {
        this.comentario_id = comentario_id;
        this.usuario_id = usuario_id;
        this.username = username;
        this.texto = texto;
        this.fecha = new Date(fecha);
        this.likes = likes;
        this.es_mio = es_mio;
        this.like_mio = like_mio;
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public boolean isEs_mio() {
        return es_mio;
    }

    public void setEs_mio(boolean es_mio) {
        this.es_mio = es_mio;
    }

    public boolean isLike_mio() {
        return like_mio;
    }

    public void setLike_mio(boolean like_mio) {
        this.like_mio = like_mio;
    }

    public Integer getComentario_id() {
        return comentario_id;
    }

    public void setComentario_id(Integer comentario_id) {
        this.comentario_id = comentario_id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
