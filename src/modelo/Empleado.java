/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Shelly Pam
 */
public class Empleado extends Persona {
    private int id;
    private String codigo, puesto;
    Conexion cn;
    public Empleado(){}

    public Empleado(int id,String codigo, String puesto, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.codigo = codigo;
        this.puesto = puesto;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    public DefaultTableModel leer(){
     
        DefaultTableModel tabla = new DefaultTableModel();
        try{
        cn = new Conexion();
        cn.abrir_conexion();
       // String query="Select e.id_empleado,e.codigo, e.nombres, e.apellidos, e.direccion, e.telefono, e.fecha_nacimiento from empleados as e";
        //String query2="Select puesto from puestos";
       String query="SELECT * FROM empleados;";
      // query="Select e.id_empleado,e.codigo, e.nombres, e.apellidos, e.direccion, e.telefono, e.fecha_nacimiento, p.puesto from empleados as e inner join puestos as p on e.id_puesto = p.id_puesto; ";
       
       ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
      // ResultSet consulta2 = cn.conexionBD.createStatement().executeQuery(query2);
                
        String encabezado[] = {"id_empleado","codigo", "nombres", "apellidos", "direccion", "telefono", "fecha_nacimiento","id_puesto"};
        tabla.setColumnIdentifiers(encabezado);
        
        String datos[] = new String [8];
        
        while(consulta.next()){
        datos[0]= consulta.getString("id_empleado");
        datos[1]= consulta.getString("codigo");
        datos[2]= consulta.getString("nombres");
        datos[3]= consulta.getString("apellidos");
        datos[4]= consulta.getString("direccion");
        datos[5]= consulta.getString("telefono");
        datos[6]= consulta.getString("fecha_nacimiento");
        datos[7]= consulta.getString("id_puesto");
        
        tabla.addRow(datos);
        }
                cn.cerrar_conexion();
        }catch(SQLException ex){
         cn.cerrar_conexion();
            System.out.println("Error " + ex.getMessage());
        }
        return tabla;
    }
     @Override
    public void agregar (){
    try{
            PreparedStatement parametro;
            String query="INSERT INTO empleados (codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento,id_puesto) VALUES (?,?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getPuesto());
            
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString (executar) + " Registro Ingresado","Agregar",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
         System.out.println("Error " + ex.getMessage());
        }
    
    }
   
    
    @Override
    public void modificar (){
        try{
            PreparedStatement parametro;
            String query="update empleados set codigo=?, nombres=?, apellidos=?, direccion=?, telefono=?, fecha_nacimiento=?,id_puesto=?" +"where id_empleado=?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getPuesto());
            parametro.setInt(8, getId());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString (executar) + " Registro Actualizado","Agregar",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
         System.out.println("Error " + ex.getMessage());
        }
    }
    
    @Override
    public void eliminar (){
    try{
            PreparedStatement parametro;
            String query="delete from empleados where id_empleado = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString (executar) + " Registro Eliminado","Agregar",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
         System.out.println("Error " + ex.getMessage());
        }
    }

    
}
