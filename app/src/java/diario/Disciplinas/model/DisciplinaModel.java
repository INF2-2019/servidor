/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diario.disciplinas.model;

import diario.disciplinas.model.Model;

public class DisciplinaModel extends Model {
	private int id, idTurmas, cargaHorariaMin;
	private String nome;

    public DisciplinaModel(int id,int idTurmas, String nome,int cargaHorariaMin) {
        this.id = id;
        this.idTurmas = idTurmas;
        this.cargaHorariaMin = cargaHorariaMin;
        this.nome = nome;
    }

    public DisciplinaModel(int idTurmas, int cargaHorariaMin, String nome) {
        this(ID_INDEFINIDO, idTurmas,nome,cargaHorariaMin);
    }
    
    public int getId() {
        return id;
    }
    
    public int getIdTurmas() {
        return idTurmas;
    }

    public void setIdTurmas(int idTurmas) {
        this.idTurmas = idTurmas;
    }

    public int getCargaHorariaMin() {
        return cargaHorariaMin;
    }

    public void setCargaHorariaMin(int cargaHorariaMin) {
        this.cargaHorariaMin = cargaHorariaMin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
