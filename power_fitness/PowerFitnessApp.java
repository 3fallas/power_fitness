/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lossa
 */
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
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PowerFitnessApp {
   
  protected Connection con;
  
        // Conexion a la base de datos.
     
       

protected void conectarOracle() {
    try { 
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String usuario = "system";
        String password = "1234";
        con = DriverManager.getConnection(url, usuario, password);
        System.out.println("Conexión establecida.");
    } catch (SQLException ex) {
        System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
    }
}



  protected void agregarUsuario(int id, String nombre, String primerApellido, String segundoApellido, 
           LocalDate fechaNacimiento, String correo, String contraseña, int celular,
            String direccion, int idRol, int idMembresia, DefaultTableModel uTabla) {

        try {
            CallableStatement procedimiento = con.prepareCall("{call agregar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, id);
            procedimiento.setString(2, nombre);
            procedimiento.setString(3, primerApellido);
            procedimiento.setString(4, segundoApellido);
           procedimiento.setDate(5, java.sql.Date.valueOf(fechaNacimiento));

            procedimiento.setString(6, correo);
            procedimiento.setString(7, contraseña);
            procedimiento.setInt(8, celular);
            procedimiento.setString(9, direccion);
            procedimiento.setInt(10, idRol);
            procedimiento.setInt(11, idMembresia);
         
            procedimiento.execute();
            
            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente");
                  int filasAfectadas =  procedimiento.executeUpdate();
 if (filasAfectadas > 0) {
    JOptionPane.showMessageDialog(null, "El usuario ha sido agregado correctamente.");
    Object[] fila = {id, nombre, primerApellido, segundoApellido, fechaNacimiento, correo, contraseña, celular, direccion, idRol, idMembresia};
    uTabla.addRow(fila);
            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente");
} 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar usuario: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: debe ingresar un número entero en los campos id_rol e id_membresia");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos cancelado");
        }
   
    }
  public void verUsuario(DefaultTableModel modeloTabla)throws SQLException{
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM Usuario");
      while(rs.next()){
    Object[] fila = new Object[11];
    fila[0] = rs.getInt("ID");
    fila[1] = rs.getString("NOMBRE");
    fila[2] = rs.getString("APELLIDO");
    fila[3] = rs.getString("SEGUNDOAPELLIDO");
    fila[4] = rs.getDate("FECHANACIMIENTO").toLocalDate();
    fila[5] = rs.getString("CORREO");
    fila[6] = rs.getString("CONTRASENA");
    fila[7] = rs.getInt("TELEFONO");
    fila[8] = rs.getString("DIRECCION");
    fila[9] = rs.getInt("ID_ROL");
    fila[10] = rs.getInt("ID_MENBRESIA");
    
    modeloTabla.addRow(fila);
    modeloTabla.fireTableDataChanged();
}

    }
public void editarUsuario(int id, String nombre, String primerApellido, String segundoApellido, LocalDate fechaNacimiento,
                           String correo, String contraseña, int celular, String direccion, int idRol, int idMembresia) {
    try {
        CallableStatement cr = con.prepareCall("{call EDITAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        cr.setInt(1, id);
        cr.setString(2, nombre);
        cr.setString(3, primerApellido);
        cr.setString(4, segundoApellido);
        cr.setDate(5, Date.valueOf(fechaNacimiento));
        cr.setString(6, correo);
        cr.setString(7, contraseña);
        cr.setInt(8, celular);
        cr.setString(9, direccion);
        cr.setInt(10, idRol);
        cr.setInt(11, idMembresia);
       

        cr.executeUpdate();
        cr.close();
        JOptionPane.showMessageDialog(null, "El usuario ha sido editado exitosamente.", "Editar usuario",
                JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        Logger.getLogger(PowerFitnessApp.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error al editar el usuario: " + ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

public void eliminarUsuario(int usuarioId, DefaultTableModel modeloTabla) {
    try {
        
        String borrar = "{ call eliminar_usuario(?) }";

     
        PreparedStatement eliminacion = con.prepareStatement(borrar);

        eliminacion.setInt(1, usuarioId);

       
        eliminacion.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado correctamente.");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el registro de la base de datos: " + ex.getMessage());
    }
}


public void Vermembresia(DefaultTableModel modeloTabla) throws SQLException {
    Statement stm = con.createStatement();
    ResultSet rs = stm.executeQuery("SELECT * FROM membresias");
    while (rs.next()) {
        Object[] fila = new Object[6];
        fila[0] = rs.getInt("ID");
        fila[1] = rs.getString("NOMBRE");
        fila[2] = rs.getString("DESCRIPCION");
        fila[3] = rs.getDate("FECHA_INICIO").toLocalDate();
        fila[4] = rs.getDate("FECHA_EXPIRACION").toLocalDate();
        fila[5] = rs.getFloat("PRECIO");
        modeloTabla.addRow(fila);
    }
    modeloTabla.fireTableDataChanged();
}
protected void agregarMembresia(int IDMembresia, String NombreMembresia, String Descripcion, String FechaInicio, String fechaExpiracion,
            float precio, DefaultTableModel modeloTabla) {
        
        try {
            CallableStatement procedimiento = con.prepareCall("{ call agregar_membresia(?, ?, ?, ?, ?, ?) }");
            procedimiento.setInt(1, IDMembresia);
            procedimiento.setString(2, NombreMembresia);
            procedimiento.setString(3, Descripcion);
            procedimiento.setDate(4, java.sql.Date.valueOf(FechaInicio));
            procedimiento.setDate(5, java.sql.Date.valueOf(fechaExpiracion));
            procedimiento.setDouble(6, Double.parseDouble(String.valueOf(precio)));
            procedimiento.execute();
            
            JOptionPane.showMessageDialog(null, "Membresía agregada exitosamente");
            
            Object[] fila = {IDMembresia, NombreMembresia, Descripcion, FechaInicio, fechaExpiracion, precio};
            modeloTabla.addRow(fila);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar la membresía: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: debe ingresar un número válido en el campo de precio");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos cancelado");
        }
    }
public void editarMembresia(int id, String nombre, String descripcion, String fechaInicio, String fechaExpiracion,
        float precio) throws SQLException {
    try {
        CallableStatement cs = con.prepareCall("{ call EDITAR_MEMBRESIA(?, ?, ?, ?, ?, ?) }");
        cs.setInt(1, id);
        cs.setString(2, nombre);
        cs.setString(3, descripcion);
        cs.setDate(4, java.sql.Date.valueOf(fechaInicio));
        cs.setDate(5, java.sql.Date.valueOf(fechaExpiracion));
        cs.setDouble(6, Double.parseDouble(String.valueOf(precio)));
        cs.execute();
        cs.close();
        JOptionPane.showMessageDialog(null, "La membresía ha sido editada exitosamente.", "Editar membresía",
                JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        throw new SQLException("Error al editar la membresía: " + ex.getMessage());
    } catch (NumberFormatException ex) {
        throw new NumberFormatException("Error: debe ingresar un número válido en el campo de precio");
    } catch (NullPointerException ex) {
        throw new NullPointerException("Ingreso de datos cancelado");
    }
}
    public  void eliminarMembresia(int IDMembresia,DefaultTableModel modeloTabla  ) throws SQLException {
           
         try {
        
        
        String borrar="{call eliminar_membresia(?)}";
           PreparedStatement eliminacion=con.prepareStatement(borrar);
           eliminacion.setInt(1,IDMembresia);
           eliminacion.executeUpdate();
           JOptionPane.showMessageDialog(null, "La membresia  ha sido eliminado correctamente.");
         }catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el registro de la base de datos: " + ex.getMessage());
    }
    
    
    
    
    }

    public  void agregarFactura(String Fecha, float total, String descripcion, int idUsuario) {
        try {
          
            String procedimiento = "{ call AGREGAR_FACTURA(?,?,?,?) }";
            CallableStatement cs = con.prepareCall(procedimiento);
            cs.setDate(1, java.sql.Date.valueOf(Fecha));
            cs.setDouble(2, total);
            cs.setString(3, descripcion);
            cs.setInt(4, idUsuario);

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

public void agregarRol(int id, String nombre, String descripcion, DefaultTableModel cTabla) {
    String sql = "{ call AGREGAR_ROL(?,?,?) }";
    try {
        CallableStatement statement = con.prepareCall(sql);
        statement.setInt(1, id);
        statement.setString(2, nombre);
        statement.setString(3, descripcion);
        int filasAfectadas = statement.executeUpdate();
        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "El rol ha sido agregado correctamente.");
            Object[] fila = {id, nombre, descripcion};
            cTabla.addRow(fila);
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido agregar el rol.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al agregar el rol: " + e.getMessage());
    }
}
public void VerRoles(DefaultTableModel modeloTabla) throws SQLException {
   
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM roles");
    while (rs.next()) {
        Object[] fila = new Object[3];
        fila[0] = rs.getInt("ID");
        fila[1] = rs.getString("NOMBRE");
        fila[2] = rs.getString("Descripcion");
        modeloTabla.addRow(fila);
        modeloTabla.fireTableDataChanged();
    }
   
    System.out.println("Filas en la tabla: " + modeloTabla.getRowCount());
}
public  void Editar_rol(int Id,String nombre, String descripcion){
        

        try {
            CallableStatement cr= con.prepareCall("{call EDITAR_ROL(?,?,?)}");
            cr.setInt(1, Id);
            cr.setString(2,nombre);
            cr.setString(3,descripcion);
            cr.executeUpdate();

            cr.close();
            JOptionPane.showMessageDialog(null, "La membresía ha sido editada exitosamente.", "Editar membresía",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(PowerFitnessApp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error .", "Error",
                JOptionPane.INFORMATION_MESSAGE);
        }
        

}
public void Eliminar_rol(int IdRol, DefaultTableModel modeloTabla){
    try {
        
        String borrar = "{ call Eliminar_rol(?) }";

     
        PreparedStatement eliminacion = con.prepareStatement(borrar);

        eliminacion.setInt(1, IdRol);

       
        eliminacion.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado correctamente.");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el registro de la base de datos: " + ex.getMessage());
    }
}

protected void agregarDatosUsuario(float peso, float altura, String fecha, int id_usuario, DefaultTableModel DatosUsuario) {
    try {
        CallableStatement procedimiento = con.prepareCall("{ call INSERTAR_DATOS_USUARIO(?, ?, ?, ?) }");
        procedimiento.setFloat(1, peso);
        procedimiento.setFloat(2, altura);
        procedimiento.setDate(3, java.sql.Date.valueOf(fecha));
        procedimiento.setInt(4, id_usuario);
        procedimiento.execute();

        JOptionPane.showMessageDialog(null, "Datos de usuario agregados exitosamente");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al agregar los datos de usuario: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Error: debe ingresar números válidos en los campos de peso y altura");
    } catch (NullPointerException e) {
        JOptionPane.showMessageDialog(null, "Ingreso de datos cancelado");
    }
}
public void VerUsuarioDatos(DefaultTableModel DatosUsuario) throws SQLException {
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM vista_datos_usuario");
    while (rs.next()) {
        Object[] fila = new Object[7];
        fila[0] = rs.getInt("ID_DATOS_USUARIO");
        fila[1] = rs.getString("nombre");
        fila[2] = rs.getString("apellido");
        fila[3] = rs.getString("segundoapellido");
        fila[4] = rs.getFloat("peso");
        fila[5] = rs.getFloat("altura");
        fila[6] = rs.getDate("fecha");
        DatosUsuario.addRow(fila);
        DatosUsuario.fireTableDataChanged();
    }
}
public void actualizarDatosUsuario(int id, float peso, float altura, String fecha) throws SQLException {
    try (CallableStatement stmt = con.prepareCall("{call ACTUALIZAR_DATOS_USUARIO(?,?,?,?)}")) {
        stmt.setInt(1, id);
        stmt.setFloat(2, peso);
        stmt.setFloat(3, altura);
         stmt.setDate(4, java.sql.Date.valueOf(fecha));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Los datos del usuario han sido actualizados correctamente.");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al actualizar los datos del usuario: " + ex.getMessage());
    }
}
public void eliminarDatosUsuario(int id) throws SQLException {
   try{
    CallableStatement stmt = con.prepareCall("{call ELIMINAR_DATOS_USUARIO(?)}");
    stmt.setInt(1, id);
    stmt.executeUpdate();
    JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado correctamente.");
}
catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el registro de la base de datos: " + ex.getMessage());
    }

}



public void AgregarEquipoGimnasio(int IDequipo, String tipo, String marca, float peso, float precio, String nombre,int cantidad, DefaultTableModel ProductoTabla) throws SQLException {
        
        try {
            CallableStatement procedimiento = con.prepareCall("{ call agregar_equipo_gimnasio(?, ?, ?, ?, ?, ?,?) }");
            procedimiento.setInt(1, IDequipo);
            procedimiento.setString(2, tipo);
            procedimiento.setString(3, marca);
            procedimiento.setFloat(4, peso);
            procedimiento.setFloat(5, precio);
            procedimiento.setString(6, nombre);
            procedimiento.setInt(7, cantidad);
            procedimiento.execute();
            
            JOptionPane.showMessageDialog(null, "Equipo agregado exitosamente");
            
            Object[] fila = {IDequipo, tipo, marca, peso, precio, nombre,cantidad};
            ProductoTabla.addRow(fila);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el equipo: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: debe ingresar un número válido en el campo de precio");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ingreso de datos cancelado");
        }
    }
public void VerEquipoGimmnasio(DefaultTableModel ProductoTabla) throws SQLException{
    try {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from equipos_gimnasio");
        while(rs.next()){
            Object[] fila = new Object[7];
            fila[0]= rs.getInt("ID_EQUIPO");
            fila[1]= rs.getString("TIPO");
            fila[2]= rs.getString("MARCA");
            fila[3]= rs.getFloat("PESO");
            fila[4]= rs.getFloat("PRECIO");
            fila[5]= rs.getString("NOMBRE");
            fila[6]= rs.getInt("CANTIDAD");
            ProductoTabla.addRow(fila);
            ProductoTabla.fireTableDataChanged();
        }
    } catch (SQLException e) {
        System.out.println("Error al ejecutar la consulta SQL: " + e.getMessage());
    }
}
public void EliminarEquipoGimnnasio(int ID_EQUIPO,DefaultTableModel ProductoTabla) throws SQLException{
   
   try{ 
    String borrar= "{call eliminar_equipo_gimnasio(?)}";
    PreparedStatement eliminar=con.prepareCall(borrar);
    eliminar.setInt(1, ID_EQUIPO);
    eliminar.executeUpdate();
    JOptionPane.showMessageDialog(null, "se eliminno el producto");
    
   } catch(SQLException ex){
    JOptionPane.showMessageDialog(null, "Error al eliminar equipo de la base de datos: " + ex.getMessage());
   }

}
public void actualizarEquipoGimnasio(int idEquipo, String tipo, String marca, float peso, float precio, String nombre, int cantidad) {
    try {
        CallableStatement cs = con.prepareCall("{ call actualizar_equipo_gimnasio(?, ?, ?, ?, ?, ?,?) }");
        cs.setInt(1, idEquipo);
        cs.setString(2, tipo);
        cs.setString(3, marca);
        cs.setFloat(4, peso);
        cs.setFloat(5, precio);
        cs.setString(6, nombre);
        cs.setInt(7, cantidad);
        cs.executeUpdate();
        JOptionPane.showMessageDialog(null, "El equipo se ha actualizado correctamente.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar el equipo: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Error: debe ingresar un número válido en el campo de precio");
    }
}




}
        








