package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.AnimalDTO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarAnimal(AnimalDTO animal) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO animal (idAnimal, color, raza, tamaño, peso, especie, idDueño) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean animalInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, animal.getIdAnimal());
            consultaPreparada.setString(2, animal.getColor());
            consultaPreparada.setString(3, animal.getRaza());
            consultaPreparada.setString(4, animal.getTamaño());
            consultaPreparada.setString(5, animal.getPeso());
            consultaPreparada.setString(6, animal.getEspecie());
            consultaPreparada.setInt(7, animal.getIdDueño());
            consultaPreparada.executeUpdate();
            animalInsertado = true;

        } finally {
            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return animalInsertado;
    }

    public boolean eliminarAnimalPorId(int idAnimal) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM animal WHERE idAnimal = ?";
        boolean animalEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idAnimal);
            consultaPreparada.executeUpdate();
            animalEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return animalEliminado;
    }

    public boolean modificarAnimal(AnimalDTO animal) throws SQLException, IOException {

        String consultaSQL = "UPDATE animal SET color = ?, raza = ?, tamaño = ?, peso = ?, especie = ?, idDueño = ? WHERE idAnimal = ?";
        boolean animalModificado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, animal.getColor());
            consultaPreparada.setString(2, animal.getRaza());
            consultaPreparada.setString(3, animal.getTamaño());
            consultaPreparada.setString(4, animal.getPeso());
            consultaPreparada.setString(5, animal.getEspecie());
            consultaPreparada.setInt(6, animal.getIdDueño());
            consultaPreparada.setInt(7, animal.getIdAnimal());
            consultaPreparada.executeUpdate();
            animalModificado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return animalModificado;
    }

    public AnimalDTO buscarAnimalPorId(int idAnimal) throws SQLException, IOException {

        AnimalDTO animal = new AnimalDTO(-1, "N/A", "N/A", "N/A", "N/A", "N/A", -1);
        String consultaSQL = "SELECT * FROM animal WHERE idAnimal = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idAnimal);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                animal = new AnimalDTO(
                        resultadoConsulta.getInt("idAnimal"),
                        resultadoConsulta.getString("color"),
                        resultadoConsulta.getString("raza"),
                        resultadoConsulta.getString("tamaño"),
                        resultadoConsulta.getString("peso"),
                        resultadoConsulta.getString("especie"),
                        resultadoConsulta.getInt("idDueño")
                );
            }

        } finally {
            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return animal;
    }

    public List<AnimalDTO> listarAnimales() throws SQLException, IOException {

        List<AnimalDTO> animales = new ArrayList<>();
        String consultaSQL = "SELECT * FROM animal";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {
                AnimalDTO animal = new AnimalDTO(
                        resultadoConsulta.getInt("idAnimal"),
                        resultadoConsulta.getString("color"),
                        resultadoConsulta.getString("raza"),
                        resultadoConsulta.getString("tamaño"),
                        resultadoConsulta.getString("peso"),
                        resultadoConsulta.getString("especie"),
                        resultadoConsulta.getInt("idDueño")
                );

                animales.add(animal);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return animales;
    }
}