package logica.DTOs;

public class AnimalDTO {

    private int idAnimal;
    private String color;
    private String raza;
    private String tamaño;
    private String peso;
    private String especie;
    private int idDueño;

    public AnimalDTO(int idAnimal, String color, String raza, String tamaño, String peso, String especie, int idDueño) {

        this.idAnimal = idAnimal;
        this.color = color;
        this.raza = raza;
        this.tamaño = tamaño;
        this.peso = peso;
        this.especie = especie;
        this.idDueño = idDueño;
    }

    public int getIdAnimal() { return idAnimal;}
    public void setIdAnimal(int idAnimal) { this.idAnimal = idAnimal;}

    public String getColor() { return color;}
    public void setColor(String color) { this.color = color;}

    public String getRaza() { return raza;}
    public void setRaza(String raza) { this.raza = raza;}

    public String getTamaño() { return tamaño;}
    public void setTamaño(String tamaño) { this.tamaño = tamaño;}

    public String getPeso() { return peso;}
    public void setPeso(String peso) { this.peso = peso;}

    public String getEspecie() { return especie;}
    public void setEspecie(String especie) { this.especie = especie;}

    public int getIdDueño() { return idDueño;}
    public void setIdDueño(int idDueño) { this.idDueño = idDueño;}
}
