/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class PowerFitnessApp {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USUARIO = "system";
    private static final String CONTRASEÑA = "1234";

    public static void main(String[] args) throws SQLException, ParseException {
        // Conexion a la base de datos.
        Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        String[] opciones = { "Agregar Usuario", "Editar Usuario", "Eliminar Usuario", "Agregar Membresía",
                "Editar Membresía", "Eliminar Membresía", "Agregar Factura", "Ver Facturas", "Agregar Rol","Editar Rol","Eliminar Rol","Agregar datos usuario","actualizar datos usuario","ver datosUsuario","eliminar datos usuario" };// Opciones para el menu
                                                                                              // principal.

        int seleccion = JOptionPane.showOptionDialog(null, "Bienvenido a Power Fitness. ¿Qué acción desea realizar?",
                "Power Fitness", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);// Menu principal.

        switch (seleccion) {
            case 0:
                agregarUsuario(conexion);
                break;
            case 1:
                editarUsuario(conexion);
                break;
            case 2:
                eliminarUsuario(conexion);
                break;
            case 3:
                agregarMembresia(conexion);
                break;
            case 4:
                editarMembresia(conexion);
                break;
            case 5:
                eliminarMembresia(conexion);
                break;
            case 6:
                agregarFactura(conexion);
                break;
            case 7:
                verFacturas(conexion);
                break;
            case 8:
            agregarRol(conexion);
            break;
            case 9:
                Editar_rol(conexion);
                break;
            case 10:
                Eliminar_rol(conexion);
                break;
            case 11:
                Agregar_DatosUsuario(conexion);
                break;
            case 12:
                 actualizar_DatosUsuario(conexion);
              
                 
                 break;
                 
            case 13:
                ver_datosUsuario(conexion);
                break;
            case 14:
               eliminar_DatosUsuario(conexion);
                break;
            
            
            default:
            
                
            
                break;
        }
    }

    // Métodos para cada opción del menú.

    public static void agregarUsuario(Connection conexion) {
        
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el id del usuario:"));
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del usuario:");
            String primerApellido = JOptionPane.showInputDialog(null, "Ingrese el primer apellido del usuario:");
            String segundoApellido = JOptionPane.showInputDialog(null, "Ingrese el segundo apellido del usuario:");
            LocalDate fechaNacimiento = LocalDate.parse(
                    JOptionPane.showInputDialog(null, "Ingrese la fecha de nacimiento del usuario (en formato yyyy-mm-dd):"));
            String correo = JOptionPane.showInputDialog(null, "Ingrese el correo electrónico del usuario:");
            String contraseña = JOptionPane.showInputDialog(null, "Ingrese la contraseña del usuario:");
            String celular = JOptionPane.showInputDialog(null, "Ingrese el número de celular del usuario (solo números):");
            String direccion = JOptionPane.showInputDialog(null, "Ingrese la dirección del usuario:");
            int idRol = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el id del rol del usuario:"));
            int idMembresia = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el id de la membresía del usuario:"));
            
            CallableStatement procedimiento = conexion.prepareCall("{call agregar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, id);
            procedimiento.setString(2, nombre);
            procedimiento.setString(3, primerApellido);
            procedimiento.setString(4, segundoApellido);
            procedimiento.setDate(5, java.sql.Date.valueOf(fechaNacimiento));
            procedimiento.setString(6, correo);
            procedimiento.setString(7, contraseña);
            procedimiento.setString(8, celular);
            procedimiento.setString(9, direccion);
            procedimiento.setInt(10, idRol);
            procedimiento.setInt(11, idMembresia);
            
            procedimiento.execute();
            
            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar usuario: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: debe ingresar un número entero en los campos id_rol e id_membresia");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos cancelado");
        }
    }

    public static void editarUsuario(Connection connection) {
        try {
            String idUsuario = JOptionPane.showInputDialog("Ingrese el ID del usuario que desea editar:");
            String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
            String primerApellido = JOptionPane.showInputDialog("Ingrese el nuevo primer apellido:");
            String segundoApellido = JOptionPane.showInputDialog("Ingrese el nuevo segundo apellido:");
            String fechaNacimiento = JOptionPane
                    .showInputDialog("Ingrese la nueva fecha de nacimiento (formato: dd/mm/aaaa):");
            String correo = JOptionPane.showInputDialog("Ingrese el nuevo correo electrónico:");
            String contrasena = JOptionPane.showInputDialog("Ingrese la nueva contraseña:");
            String celular = JOptionPane.showInputDialog("Ingrese el nuevo número de celular:");
            String direccion = JOptionPane.showInputDialog("Ingrese la nueva dirección:");
            String idRol = JOptionPane.showInputDialog("Ingrese el nuevo ID del rol:");
            String idMembresia = JOptionPane.showInputDialog("Ingrese el nuevo ID de membresía:");
            String idDatosUsuario = JOptionPane.showInputDialog("Ingrese el nuevo ID de datos de usuario:");

            CallableStatement cstmt = connection.prepareCall("{call editar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cstmt.setString(1, idUsuario);
            cstmt.setString(2, nombre);
            cstmt.setString(3, primerApellido);
            cstmt.setString(4, segundoApellido);
            cstmt.setString(5, fechaNacimiento);
            cstmt.setString(6, correo);
            cstmt.setString(7, contrasena);
            cstmt.setString(8, celular);
            cstmt.setString(9, direccion);
            cstmt.setString(10, idRol);
            cstmt.setString(11, idMembresia);
            cstmt.setString(12, idDatosUsuario);

            cstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario editado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar el usuario: " + e.getMessage());
        }
    }

    public static void eliminarUsuario(Connection conexion) throws SQLException {
        CallableStatement cs = null;
        try {
            String idString = JOptionPane.showInputDialog(null, "Introduce el ID del usuario a eliminar:");
            int id = Integer.parseInt(idString);

            cs = conexion.prepareCall("{ call eliminar_usuario(?) }");
            cs.setInt(1, id);
            cs.execute();
            JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debes introducir un número entero válido para el ID del usuario.");
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al eliminar el usuario.");
            e.printStackTrace();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public static void agregarMembresia(Connection conexion) throws SQLException {
        String id = JOptionPane.showInputDialog("Ingrese el ID de la membresía:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la membresía:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la membresía:");
        String fechaInicio = JOptionPane
                .showInputDialog("Ingrese la fecha de inicio de la membresía (formato: yyyy-MM-dd):");
        String fechaExpiracion = JOptionPane
                .showInputDialog("Ingrese la fecha de expiración de la membresía (formato: yyyy-MM-dd):");
        String precio = JOptionPane.showInputDialog("Ingrese el precio de la membresía:");

        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall("{call agregar_membresia(?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(id));
            cs.setString(2, nombre);
            cs.setString(3, descripcion);
            cs.setDate(4, java.sql.Date.valueOf(fechaInicio));
            cs.setDate(5, java.sql.Date.valueOf(fechaExpiracion));
            cs.setDouble(6, Double.parseDouble(precio));
            cs.execute();
            JOptionPane.showMessageDialog(null, "La membresía ha sido agregada exitosamente");
        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al agregar la membresía: " + ex.getMessage());
            conexion.rollback();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public static void editarMembresia(Connection conexion) throws SQLException {
        String idString = JOptionPane.showInputDialog(null, "Ingrese el ID de la membresía:", "Editar membresía",
                JOptionPane.QUESTION_MESSAGE);
        int id = Integer.parseInt(idString);

        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre de la membresía:",
                "Editar membresía", JOptionPane.QUESTION_MESSAGE);

        String descripcion = JOptionPane.showInputDialog(null, "Ingrese la nueva descripción de la membresía:",
                "Editar membresía", JOptionPane.QUESTION_MESSAGE);

        String fechaInicioString = JOptionPane.showInputDialog(null,
                "Ingrese la nueva fecha de inicio de la membresía (en formato yyyy-mm-dd):", "Editar membresía",
                JOptionPane.QUESTION_MESSAGE);
        Date fechaInicio = Date.valueOf(fechaInicioString);

        String fechaExpiracionString = JOptionPane.showInputDialog(null,
                "Ingrese la nueva fecha de expiración de la membresía (en formato yyyy-mm-dd):", "Editar membresía",
                JOptionPane.QUESTION_MESSAGE);
        Date fechaExpiracion = Date.valueOf(fechaExpiracionString);

        String precioString = JOptionPane.showInputDialog(null, "Ingrese el nuevo precio de la membresía:",
                "Editar membresía", JOptionPane.QUESTION_MESSAGE);
        double precio = Double.parseDouble(precioString);

        CallableStatement cs = conexion.prepareCall("{call EDITAR_MEMBRESIA(?, ?, ?, ?, ?, ?)}");
        cs.setInt(1, id);
        cs.setString(2, nombre);
        cs.setString(3, descripcion);
        cs.setDate(4, fechaInicio);
        cs.setDate(5, fechaExpiracion);
        cs.setDouble(6, precio);
        cs.execute();
        cs.close();

        JOptionPane.showMessageDialog(null, "La membresía ha sido editada exitosamente.", "Editar membresía",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void eliminarMembresia(Connection conexion) throws SQLException {
        CallableStatement cs = null;
        try {
            // Solicitar el ID de la membresía a eliminar
            String idMembresiaString = JOptionPane.showInputDialog("Ingrese el ID de la membresía que desea eliminar:");
            int idMembresia = Integer.parseInt(idMembresiaString);

            cs = conexion.prepareCall("{ call eliminar_membresia(?) }");
            cs.setInt(1, idMembresia);
            cs.executeUpdate();
            JOptionPane.showMessageDialog(null, "La membresía con el ID " + idMembresia + " ha sido eliminada.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la membresía.");
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID de la membresía debe ser un número entero.");
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public static void agregarFactura(Connection conexion) {
        try {
            // Solicitar los datos de entrada mediante JOptionPane
            String fechaString = JOptionPane.showInputDialog(null, "Ingrese la fecha (YYYY-MM-DD):");
            Date fecha = Date.valueOf(fechaString);
            String totalString = JOptionPane.showInputDialog(null, "Ingrese el total:");
            double total = Double.parseDouble(totalString);
            String descripcion = JOptionPane.showInputDialog(null, "Ingrese la descripción:");
            String idUsuarioString = JOptionPane.showInputDialog(null, "Ingrese el ID de usuario:");
            int idUsuario = Integer.parseInt(idUsuarioString);

            // Crear una llamada al procedimiento PL/SQL
            String procedimiento = "{ call AGREGAR_FACTURA(?,?,?,?) }";
            CallableStatement cs = conexion.prepareCall(procedimiento);
            cs.setDate(1, fecha);
            cs.setDouble(2, total);
            cs.setString(3, descripcion);
            cs.setInt(4, idUsuario);

            // Ejecutar el procedimiento
            cs.execute();

            JOptionPane.showMessageDialog(null, "Factura agregada correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar factura: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void verFacturas(Connection conexion) throws SQLException {
        String id = JOptionPane.showInputDialog("Ingrese el ID de la factura que desea ver:");
        String sql = "{ call ver_factura(?) }";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            stmt.execute();
            System.out.println("Factura mostrada correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al mostrar la factura: " + ex.getMessage());
        }
    }

    public static void agregarRol(Connection conexion) {
        String id = JOptionPane.showInputDialog("Ingrese el ID del rol:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del rol:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del rol:");
        String sql="{ call  AGREGAR_ROL(?,?,?)}";
        try {
            // Crear la sentencia SQL para insertar un nuevo registro en la tabla "ROLES"
            
           CallableStatement statement= conexion.prepareCall(sql);
            
            // Establecer los valores de los parámetros de la sentencia SQL
            statement.setInt(1, Integer.parseInt(id));
            statement.setString(2, nombre);
            statement.setString(3, descripcion);
            
            // Ejecutar la sentencia SQL para insertar el nuevo registro
            int filasAfectadas = statement.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "El rol ha sido agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido agregar el rol.");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el rol: " + e.getMessage());
        }
    }
public static void ver_rol(Connection conexion){

  try {
       

        // Crear una consulta SQL
        String sql = "SELECT ID, nombre, descripcion FROM Roles";

        // Crear un objeto PreparedStatement para ejecutar la consulta
        PreparedStatement stmt = conexion.prepareStatement(sql);

        // Ejecutar la consulta y obtener los resultados
        ResultSet rs = stmt.executeQuery();

        // Mostrar los resultados
        while (rs.next()) {
            int rollId = rs.getInt("Id");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            JOptionPane.showMessageDialog(null,"Rol #"+rollId+"\n"+"Nombre: "+nombre+"\n"+ "Obligacion:"+descripcion);
        }

        // Cerrar recursos
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (conexion!= null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
public static void Editar_rol(Connection conexion){
           String idString = JOptionPane.showInputDialog(null, "Ingrese el ID de la membresía:", "Editar membresía",
                JOptionPane.QUESTION_MESSAGE);
        int id = Integer.parseInt(idString);
        String nombre= JOptionPane.showInputDialog(null,"ingrese el nuevo nombre:");
        String descripcion= JOptionPane.showInputDialog(null,"ingrese la nueva descripcion:");
        try {
            CallableStatement cr= conexion.prepareCall("{call EDITAR_ROL(?,?,?)}");
            cr.setInt(1, id);
            cr.setString(2,nombre);
            cr.setString(3,descripcion);
            cr.execute();
            cr.close();
            JOptionPane.showMessageDialog(null, "La membresía ha sido editada exitosamente.", "Editar membresía",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(PowerFitnessApp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error .", "Error",
                JOptionPane.INFORMATION_MESSAGE);
        }
        

}

public static void Eliminar_rol(Connection conexion){
    CallableStatement cr = null;
    try {
        String idRolint = JOptionPane.showInputDialog(null, "Ingrese el id del rol a eliminar:");
        int IdRol = Integer.parseInt(idRolint);
        cr = conexion.prepareCall("{call eliminar_rol(?)}");
        cr.setInt(1, IdRol);
        cr.execute();
        JOptionPane.showMessageDialog(null,"Se eliminio con ");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar la membresía.");
        ex.printStackTrace();
    } finally {
        if (cr != null) {
            try {
                cr.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

public static void Agregar_DatosUsuario(Connection conexion) throws SQLException {
  int id= Integer.parseInt(JOptionPane.showInputDialog(null,"ingrese la id del dato:"));
  float peso= Float.parseFloat(JOptionPane.showInputDialog(null,"ingrese el peso"));
  float altura= Float.parseFloat(JOptionPane.showInputDialog(null,"ingrese la altura;"));
  String fechaIngresada = JOptionPane.showInputDialog(null, "Ingrese la fecha (dd/MM/yyyy)");
 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
 java.util.Date fechaUtil;

try {
    fechaUtil = dateFormat.parse(fechaIngresada);
} catch (ParseException e) {
    e.printStackTrace();
    return;
}
java.sql.Date fecha = new java.sql.Date(fechaUtil.getTime());
  int idusuario =Integer.parseInt(JOptionPane.showInputDialog(null,"ingrese el id de usuario"));
  String sql="{call llenar_datos_usuario(?,?,?,?,?)}";
 try{
 CallableStatement statement= conexion.prepareCall(sql);
 statement.setInt(1, id);
 statement.setDouble(2,peso);
 statement.setDouble(3,altura);
 statement.setDate(4,fecha);
 statement.setInt(5,idusuario);
 statement.execute();
     System.out.println("Los datos del usuario se agregaron correctamente.");
} catch (SQLException e) {
    System.out.println("Error al agregar los datos del usuario: " + e.getMessage());
}
 
 
 }
public static void ver_datosUsuario(Connection conexion) throws SQLException{

 try{   
    String sql ="Select ID,PESO,ALTURA,FECHA,ID_USUARIO from  datos_usuario";

 PreparedStatement stmt = conexion.prepareStatement(sql);

        // Ejecutar la consulta y obtener los resultados
        ResultSet rs = stmt.executeQuery();

        // Mostrar los resultados
        while(rs.next()){
                int datoId=rs.getInt("ID");
                double PesoDATo=rs.getDouble("PESO");
                double AlturaDATo=rs.getDouble("ALTURA");
                Date fecha=rs.getDate("FECHA");
                int IdUsuario=rs.getInt("ID_USUARIO");
                 JOptionPane.showMessageDialog(null,"ID #"+datoId+"\n"+"Peso: "+PesoDATo+"\n"+ "Altura:"+AlturaDATo+"\n"+"Fecha:"+
                         fecha+"\n"+"Id usuario"+IdUsuario);
        }
        rs.close();
        stmt.close();
        } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (conexion!= null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





public static void actualizar_DatosUsuario(Connection conexion) throws SQLException {
    int id_usuario = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el ID del usuario a actualizar:"));
    double peso = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el nuevo peso:"));
    double altura = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese la nueva altura:"));
    String fechaIngresada = JOptionPane.showInputDialog(null, "Ingrese la nueva fecha (dd/MM/yyyy):");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date fechaUtil = null;
    try {
        fechaUtil = dateFormat.parse(fechaIngresada);
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(null, "La fecha ingresada no es válida.");
        return;
    }
    java.sql.Date fecha = new java.sql.Date(fechaUtil.getTime());
    String sql = "{call actualizar_datos_usuario(?,?,?,?)}";
    try {
        CallableStatement statement = conexion.prepareCall(sql);
        statement.setInt(1, id_usuario);
        statement.setDouble(2, peso);
        statement.setDouble(3, altura);
        statement.setDate(4, fecha);
        statement.execute();
        JOptionPane.showMessageDialog(null, "Datos de usuario actualizados correctamente.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar los datos del usuario: " + e.getMessage());
    }
}
public static void eliminar_DatosUsuario(Connection conexion) throws SQLException {
    int id_registro = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el ID del registro a eliminar:"));
    String sql = "{call eliminar_datos_usuario(?)}";
    try {
        CallableStatement statement = conexion.prepareCall(sql);
        statement.setInt(1, id_registro);
        statement.execute();
        JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + e.getMessage());
    }
}



}









