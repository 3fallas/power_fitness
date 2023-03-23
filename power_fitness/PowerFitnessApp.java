import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class PowerFitnessApp {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USUARIO = "ethan";
    private static final String CONTRASEÑA = "Canuto0310";

    public static void main(String[] args) throws SQLException {
        // Conexion a la base de datos.
        Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        String[] opciones = { "Agregar Usuario", "Editar Usuario", "Eliminar Usuario", "Agregar Membresía",
                "Editar Membresía", "Eliminar Membresía", "Agregar Factura", "Ver Facturas", "Agregar Rol" };// Opciones para el menu
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

            CallableStatement cstmt = connection
                    .prepareCall("{call editar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
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
        
        try {
            // Crear la sentencia SQL para insertar un nuevo registro en la tabla "ROLES"
            String sql = "INSERT INTO ROLES(ID, NOMBRE, DESCRIPCION) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            
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

}
