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



   protected void agregarUsuario(int id, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento,String correo, String contraseña, int celular,
            String direccion, int idRol, int idMembresia, int IdDatosUsuario, DefaultTableModel uTabla) {
        
        
        try {
         
            
            CallableStatement procedimiento = con.prepareCall("{call agregar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, id);
            procedimiento.setString(2, nombre);
            procedimiento.setString(3, primerApellido);
            procedimiento.setString(4, segundoApellido);
            procedimiento.setDate(5, java.sql.Date.valueOf(LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            procedimiento.setString(6, correo);
            procedimiento.setString(7, contraseña);
            procedimiento.setInt(8, celular);
            procedimiento.setString(9, direccion);
            procedimiento.setInt(10, idRol);
            procedimiento.setInt(11, idMembresia);
            procedimiento.setInt(12,IdDatosUsuario);
            procedimiento.execute();
            
            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente");
                  int filasAfectadas =  procedimiento.executeUpdate();
 if (filasAfectadas > 0) {
    JOptionPane.showMessageDialog(null, "El usuario ha sido agregado correctamente.");
    Object[] fila = {id, nombre, primerApellido, segundoApellido, fechaNacimiento, correo, contraseña, celular, direccion, idRol, idMembresia, IdDatosUsuario};
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
    Object[] fila = new Object[12];
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
    fila[11] = rs.getInt("ID_DATOS_USUARIO");
    modeloTabla.addRow(fila);
    modeloTabla.fireTableDataChanged();
}

    }
    

public void editarUsuario(int id, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento,
                           String correo, String contraseña, int celular, String direccion, int idRol, int idMembresia, int idDatosUsuario) {
    try {
        CallableStatement cr = con.prepareCall("{call EDITAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
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
        cr.setInt(12, idDatosUsuario);

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
public static void VerDATOSUsuario(Connection conexion) throws SQLException{

 String idDatosUsuarioStr = JOptionPane.showInputDialog("Ingrese el ID_DATOS_USUARIO del usuario a consultar:");
    int idDatosUsuario = Integer.parseInt(idDatosUsuarioStr);
    String consulta = "SELECT * FROM DatosUsuarios WHERE ID_DATOS_USUARIO = ?";
    
    try {
        PreparedStatement ps = conexion.prepareStatement(consulta);
        ps.setInt(1, idDatosUsuario);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            int peso = rs.getInt("peso");
            int altura = rs.getInt("altura");
            Date fecha = rs.getDate("fecha");
            
            JOptionPane.showMessageDialog(null, "Nombre: " + nombre + "\nApellido: " + apellido
                + "\nPeso: " + peso + "\nAltura: " + altura + "\nFecha: " + fecha);
        }
        
        // Cerramos los recursos
        rs.close();
        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }


}


}
        








