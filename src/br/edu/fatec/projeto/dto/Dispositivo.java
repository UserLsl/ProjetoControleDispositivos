package br.edu.fatec.projeto.dto;

import java.util.Date;

public class Dispositivo {
    private int idDispositivos;
    private String modelo;
    private Marca marca;
    private String processador;
    private String memoriaRam;
    private String armazenamento;
    private String serviceTag;
    private String tamanhoTela;
    private Date dataCadastro;

    public Dispositivo(int idDispositivos, String modelo, Marca marca, String processador, String memoriaRam, String armazenamento, String serviceTag, String tamanhoTela, Date dataCadastro) {
        this.idDispositivos = idDispositivos;
        this.modelo = modelo;
        this.marca = marca;
        this.processador = processador;
        this.memoriaRam = memoriaRam;
        this.armazenamento = armazenamento;
        this.serviceTag = serviceTag;
        this.tamanhoTela = tamanhoTela;
        this.dataCadastro = dataCadastro;
    }

    public Dispositivo(int idDispositivos, String modelo, Marca marca, String processador, String serviceTag) {
        this.idDispositivos = idDispositivos;
        this.modelo = modelo;
        this.marca = marca;
        this.processador = processador;
        this.serviceTag = serviceTag;
    }

    public int getIdDispositivos() {
        return idDispositivos;
    }

    public void setIdDispositivos(int idDispositivos) {
        this.idDispositivos = idDispositivos;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public String getTamanhoTela() {
        return tamanhoTela;
    }

    public void setTamanhoTela(String tamanhoTela) {
        this.tamanhoTela = tamanhoTela;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
