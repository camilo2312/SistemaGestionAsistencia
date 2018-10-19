package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import vo.EstudianteVo;
import vo.GrupoVo;

public class EstudianteDao {

	public EstudianteVo consultarEstudianteLogin(String documento, String password) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		EstudianteVo miEstudiante = null;

		connection = miConexion.getConnection();
		
		System.out.println("Documento: "+documento+" , pass: "+password);
		
		try {
			if (connection != null) {
				
				String consulta = "SELECT * FROM estudiante where documento = ? and password = ? ";

				statement = connection.prepareStatement(consulta);

				statement.setString(1, documento);
				statement.setString(2, password);
								
				result = statement.executeQuery();
				System.out.println("continua...");
				if (result.next() == true) {
					miEstudiante = new EstudianteVo();
					miEstudiante.setDocumento(result.getString("documento"));
					miEstudiante.setNombre(result.getString("nombre"));
					miEstudiante.setDireccion(result.getString("direccion"));
					miEstudiante.setTelefono(result.getString("telefono"));
					miEstudiante.setEmail(result.getString("email"));
					miEstudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
					miEstudiante.setSexo(result.getString("sexo"));
					miEstudiante.setEstado(result.getString("estado"));
					miEstudiante.setPassword(result.getString("password"));
					miEstudiante.setTipo(result.getString("tipo"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del usuario Estudiante: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return miEstudiante;
	}

	public String registrarEstudiante(EstudianteVo estudianteVo) {
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
		String consulta = "INSERT INTO estudiante (documento,nombre,direccion,telefono,email,grupo,fecha_nacimiento,sexo,estado,password,tipo)"
				+ "  VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, estudianteVo.getDocumento());
			preStatement.setString(2, estudianteVo.getNombre());
			preStatement.setString(3, estudianteVo.getDireccion());
			preStatement.setString(4, estudianteVo.getTelefono());
			preStatement.setString(5, estudianteVo.getEmail());
			preStatement.setString(6, estudianteVo.getGrupo());
			preStatement.setDate(7, estudianteVo.getFechaNacimiento());
			preStatement.setString(8, estudianteVo.getSexo());
			preStatement.setString(9, estudianteVo.getEstado());
			preStatement.setString(10, estudianteVo.getPassword());
			preStatement.setString(11, estudianteVo.getTipo());
			preStatement.execute();

			resultado = "Registro Exitoso!!!";

		} catch (SQLException e) {
			System.out.println("No se pudo registrar el Estudiante: " + e.getMessage());
			resultado = "No se pudo registrar, verifique nuevamente";
		} finally {
			conexion.desconectar();
		}

		return resultado;
	}

		public ArrayList<EstudianteVo> obtenerListaEstudiantes() {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		EstudianteVo estudiante = new EstudianteVo();
		ArrayList<EstudianteVo> listaEstudiantes = null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM estudiante ";
		System.out.println("***************************************");
		System.out.println(consulta);
		try {
			if (connection != null) {
				listaEstudiantes = new ArrayList<>();
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();
				while (result.next() == true) {
					estudiante = new EstudianteVo();
					estudiante.setDocumento(result.getString("documento"));
					estudiante.setNombre(result.getString("nombre"));
					estudiante.setDireccion(result.getString("direccion"));
					estudiante.setTelefono(result.getString("telefono"));
					estudiante.setEmail(result.getString("email"));
					estudiante.setGrupo(result.getString("grupo"));
					estudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
					estudiante.setSexo(result.getString("sexo"));
					estudiante.setEstado(result.getString("estado"));
					estudiante.setPassword(result.getString("password"));
					estudiante.setTipo(result.getString("tipo"));
					estudiante.setFecha(estudiante.getFechaNacimiento()+"");//se agrega fecha a la variable temporal
					listaEstudiantes.add(estudiante);
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Estudiante: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return listaEstudiantes;
	}

	public String actualizarEstudiante(EstudianteVo estudiante) {
		String resultado = "";
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();
		System.out.println("VA A ACTUALIZAR EL ESTUDIANTE");
		try {
			String consulta = "UPDATE estudiante "
					+ " SET nombre = ? , direccion=? , telefono=? , email=? , fecha_nacimiento= ? , sexo= ?, estado=? "
					+ " WHERE documento= ? ";
			PreparedStatement preStatement = connection.prepareStatement(consulta);

			preStatement.setString(1, estudiante.getNombre());
			preStatement.setString(2, estudiante.getDireccion());
			preStatement.setString(3, estudiante.getTelefono());
			preStatement.setString(4, estudiante.getEmail());
			preStatement.setDate(5, estudiante.getFechaNacimiento());
			preStatement.setString(6, estudiante.getSexo());
			preStatement.setString(7, estudiante.getEstado());
			preStatement.setString(8, estudiante.getDocumento());
			preStatement.executeUpdate();

			resultado = "Se ha Actualizado el Estudiante satisfactoriamente";

			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e);
			resultado = "No se pudo actualizar el estudiante";
		}
		return resultado;
	}

	public String eliminarEstudiante(EstudianteVo estudiante) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();

		String resp = "";
		try {
			String sentencia = "DELETE FROM estudiante WHERE documento= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setString(1, estudiante.getDocumento());

			statement.executeUpdate();

			resp = "Se ha eliminado el estudiante exitosamente";
			statement.close();
			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			resp = "No se pudo eliminar el estudiante";
		}
		return resp;
	}

	
}