/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Shelly Pam
 */
public class Cliente extends Persona {
    private int id;
    private String nit;
    Conexion cn;
    public Cliente(){}
    public Cliente(int id,String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.nit = nit;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    
    /**
     *
     * @return 
     */
    public DefaultTableModel leer(){
     
        DefaultTableModel tabla = new DefaultTableModel();
        try{
        cn = new Conexion();
        cn.abrir_conexion();
        String query;
        //query="Select id_cliente as id_cliente,nit, nombres, apellidos, direccion, telefono, fecha_nacimiento from clientes";
        query="SELECT * FROM clientes;";
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
                
        String encabezado[] = {"id_cliente","nit", "nombres", "apellidos", "direccion", "telefono", "fecha_nacimiento"};
        tabla.setColumnIdentifiers(encabezado);
        
        String datos[] = new String [7];
        
        while(consulta.next()){
        datos[0]= consulta.getString("id_cliente");
        datos[1]= consulta.getString("nit");
        datos[2]= consulta.getString("nombres");
        datos[3]= consulta.getString("apellidos");
        datos[4]= consulta.getString("direccion");
        datos[5]= consulta.getString("telefono");
        datos[6]= consulta.getString("fecha_nacimiento");
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
            String query="INSERT INTO clientes (nit, nombres, apellidos, direccion, telefono, fecha_nacimiento) VALUES (?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            
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
            String query="update clientes set nit=?, nombres=?, apellidos=?, direccion=?, telefono=?, fecha_nacimiento=?"+"where id_cliente=?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getId());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString (executar) + " Registro Ingresado","Agregar",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException ex){
         System.out.println("Error " + ex.getMessage());
        }
    }
    
    @Override
    public void eliminar (){
        try{
            PreparedStatement parametro;
            String query="delete from clientes where id_cliente = ?";
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
