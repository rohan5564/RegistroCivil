
package utilidades;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.PreparedStatement;


public class ConexionBD{
    private static ComboPooledDataSource conexion;
    private boolean status;
    
    public ConexionBD(){
        conexion = null;
        status = false;
    }
        
    public boolean getStatus(){
        return status;
    }
    
    public ComboPooledDataSource getConexion()throws PropertyVetoException{
        return getConexion("192.168.0.104:3306/registro civil POO", "proyectoPOO", "admin");
    }
    
    public static ComboPooledDataSource getConexion(String urlBD, String userBD, String passBD) throws PropertyVetoException{
        conexion = new ComboPooledDataSource();
        conexion.setJdbcUrl("jdbc:mysql://"+urlBD);
        conexion.setUser(userBD);
        conexion.setPassword(passBD);    
        conexion.setCheckoutTimeout(2000); //milisegundos
        conexion.setInitialPoolSize(5);
        conexion.setMinPoolSize(5);
        conexion.setAcquireIncrement(5);
        conexion.setMaxPoolSize(20);
        conexion.setMaxStatements(100);
        conexion.setPreferredTestQuery("SELECT 1");
        conexion.setTestConnectionOnCheckout(false);
        conexion.setTestConnectionOnCheckin(true);
        conexion.setIdleConnectionTestPeriod(100);
        return conexion;
    }
    
    public boolean checkConexion()throws SQLException, PropertyVetoException{
        boolean stat = false;
        try {
            stat = getConexion().getConnection().prepareStatement(conexion.getPreferredTestQuery()).executeQuery().next();
        }catch(SQLException e){
            System.err.println("excepcion: " + e.getMessage());
        }
        return stat;
    }
    
    public boolean login(String user, String pass){
        Connection conexionLocal = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            //rs = consulta por comandos de MySql
            String consulta = "SELECT * FROM loginOP WHERE usuario = " + "\"" + user + "\"";
            conexionLocal = getConexion().getConnection();
            ////////////////////////////////////////////////////////////////////getConexion().isTestConnectionOnCheckin();
            st = conexionLocal.prepareStatement(consulta);
            rs = st.executeQuery();
            rs.next(); //avanza a la siguiente tupla (actualmente al primero)
            if(rs.getString("pass").equals(pass))
                this.status = true;
                //return status;
            //itera sobre los resultados, invalido si la llave primaria es user
            /*while (rs.next())
            {
                String password = rs.getString("pass");
                if(password.equals(pass)){
                    stat = true;
                    System.out.println("ingreso aceptado");
                    break;
                }
            }*/
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            try{
                if(rs!=null)
                    rs.close();
                if(st!=null)
                    st.close();
                if(conexionLocal!=null)
                    conexionLocal.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }
        return status;
    }
    
}