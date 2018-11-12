package utilidades;


import Enums.Sexo;
import Enums.Visa;
import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Extranjero;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.PreparedStatement;


public class ConexionBD{
    
    private static ConexionBD conexionbd;
    private static ComboPooledDataSource conexion;
    private boolean status;
    private String consulta;
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;
    
    private ConexionBD(){
        conexion = null;
        status = false;
        consulta = null;
        con = null;
        st = null;
        rs = null;
    }
    
    public static ConexionBD getInstancia(){
        if(conexionbd == null)
            conexionbd = new ConexionBD();
        return conexionbd;
    }
    
    public boolean getStatus(){
        return status;
    }
    
    public ComboPooledDataSource getConexion(String url, String user, String pass) throws PropertyVetoException{
        conexion = new ComboPooledDataSource();
        conexion.setJdbcUrl("jdbc:mysql://"+url+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        conexion.setUser(user);
        conexion.setPassword(pass);    
        conexion.setCheckoutTimeout(2000); //milisegundos
        conexion.setInitialPoolSize(1);
        conexion.setMinPoolSize(1);
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
            stat = conexion.getConnection().prepareStatement(conexion.getPreferredTestQuery()).executeQuery().next();
        }catch(SQLException e){
            System.err.println("excepcion: " + e.getMessage());
        }
        return stat;
    }
    
    public boolean login(String user, String pass){
        try {
            consulta = "SELECT * FROM operadores WHERE usuario = \"" + user + "\"";
            con = conexion.getConnection();
            
            st = con.prepareStatement(consulta);
            rs = st.executeQuery();
            rs.next(); //avanza a la siguiente tupla (actualmente al primero)
            if(rs.getString("contraseña").equals(pass))
                this.status = true;
                
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta = null;
            try{
                if(rs!=null)
                    rs.close();
                if(st!=null)
                    st.close();
                if(con!=null)
                    con.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }
        return status;
    }
    
    public void crearTablas(){
        crearTablaChilena();
        crearTablaExtranjera();
    }
    
    private void crearTablaChilena(){
        try{
            consulta = 
                    "CREATE TABLE IF NOT EXISTS poblacion_chilena"
                    +"(nombre                       VARCHAR(50) NOT NULL,"
                    +"apellido                      VARCHAR(50) NOT NULL,"
                    +"sexo                          VARCHAR(10) NOT NULL,"
                    +"fecha_de_nacimiento           DATE NOT NULL,"
                    +"hora_de_nacimiento            VARCHAR(10) NOT NULL,"
                    +"comentario_de_nacimiento      TEXT,"
                    +"fecha_de_defuncion            DATE,"
                    +"hora_de_defuncion             VARCHAR(10),"
                    +"comentario_de_defuncion       TEXT,"
                    +"profesion                     VARCHAR(50),"
                    +"rut                           VARCHAR(9) PRIMARY KEY,"
                    +"numero_de_documento           VARCHAR(20),"
                    +"direccion                     VARCHAR(100),"
                    +"region_de_nacimiento          VARCHAR(100) NOT NULL,"
                    +"comuna_de_nacimiento          VARCHAR(100) NOT NULL)";
            
            con = conexion.getConnection();
            st = con.prepareStatement(consulta);
            st.executeUpdate();
            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta = null;
            try{
                if(rs!=null)
                    rs.close();
                if(st!=null)
                    st.close();
                if(con!=null)
                    con.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }            
    }
    
    private void crearTablaExtranjera(){
        try{
            consulta = 
                    "CREATE TABLE IF NOT EXISTS poblacion_extranjera"
                    +"(nombre                       VARCHAR(50) NOT NULL,"
                    +"apellido                      VARCHAR(50) NOT NULL,"
                    +"sexo                          VARCHAR(10) NOT NULL,"
                    +"fecha_de_nacimiento           DATE NOT NULL,"
                    +"hora_de_nacimiento            VARCHAR(10) NOT NULL,"
                    +"comentario_de_nacimiento      TEXT,"
                    +"fecha_de_defuncion            DATE,"
                    +"hora_de_defuncion             VARCHAR(10),"
                    +"comentario_de_defuncion       TEXT,"
                    +"profesion                     VARCHAR(50),"
                    +"pasaporte                     VARCHAR(20) PRIMARY KEY,"
                    +"tipo_de_visa                  VARCHAR(10) NOT NULL,"
                    +"primera_visa                  DATE NOT NULL)";
            
            con = conexion.getConnection();
            st = con.prepareStatement(consulta);
            st.executeUpdate();
            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta = null;
            try{
                if(rs!=null)
                    rs.close();
                if(st!=null)
                    st.close();
                if(con!=null)
                    con.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }            
    }
    
    public Ciudadano buscarCiudadano(String identificador){
        Chileno chileno = null;
        Extranjero extranjero = null;
        boolean esChileno = Chileno.comprobarRut(identificador);
        try{
            if(esChileno){
                consulta = "SELECT * FROM poblacion_chilena WHERE identificador = \""+identificador+"\"";
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                rs = st.executeQuery();
                chileno = new Chileno();
                while(rs.next()){
                    chileno.setNombre(rs.getString("nombre"));
                    chileno.setApellido(rs.getString("apellido"));
                    chileno.setSexo(Sexo.valueOf(rs.getString("sexo")));
                    chileno.setNacimiento(rs.getDate("fecha de nacimiento").toLocalDate());
                    chileno.setHoraNacimiento(rs.getString("hora de nacimiento"));
                    chileno.setComentarioNacimiento(rs.getString("comentario de nacimiento"));
                    chileno.setDefuncion(rs.getDate("fecha de defuncion").toLocalDate());
                    chileno.setHoraDefuncion(rs.getString("hora de defuncion"));
                    chileno.setComentarioDefuncion(rs.getString("comentario de defuncion"));
                    chileno.setProfesion(rs.getString("profesion"));
                    //ciudadano.setEstadoCivil(rs.getString("nombre"));
                    //ciudadano.setNacionalidades(rs.getString("nombre"));
                    //ciudadano.setParientes(rs.getString("nombre"));
                    
                    chileno.setRut(rs.getString("rut"));
                    chileno.setNumeroDeDocumento(rs.getString("numero de documento"));
                    chileno.setDireccion(rs.getString("direccion"));
                    chileno.setRegionDeNacimiento(rs.getString("region de nacimiento"));
                    chileno.setComunaDeNacimiento(rs.getString("comuna de nacimiento"));
                }
            }
            else{
                consulta = "SELECT * FROM poblacion_extranjera WHERE identificador = \""+identificador+"\"";
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                rs = st.executeQuery();
                extranjero = new Extranjero();
                while(rs.next()){
                    extranjero.setNombre(rs.getString("nombre"));
                    extranjero.setApellido(rs.getString("apellido"));
                    extranjero.setSexo(Sexo.valueOf(rs.getString("sexo")));
                    extranjero.setNacimiento(rs.getDate("fecha de nacimiento").toLocalDate());
                    extranjero.setHoraNacimiento(rs.getString("hora de nacimiento"));
                    extranjero.setComentarioNacimiento(rs.getString("comentario de nacimiento"));
                    extranjero.setDefuncion(rs.getDate("fecha de defuncion").toLocalDate());
                    extranjero.setHoraDefuncion(rs.getString("hora de defuncion"));
                    extranjero.setComentarioDefuncion(rs.getString("comentario de defuncion"));
                    extranjero.setProfesion(rs.getString("profesion"));
                    //ciudadano.setEstadoCivil(rs.getString("nombre"));
                    //ciudadano.setNacionalidades(rs.getString("nombre"));
                    //ciudadano.setParientes(rs.getString("nombre"));
                    
                    extranjero.setPasaporte(rs.getString("pasaporte"));
                    extranjero.setTipoDeVisa(Visa.valueOf(rs.getString("tipo de visa")));
                    extranjero.setPrimeraVisa(rs.getDate("primera visa").toLocalDate());
                }
            }
            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta = null;
            try{
                if(rs!=null)
                    rs.close();
                if(st!=null)
                    st.close();
                if(con!=null)
                    con.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }            
        return esChileno?chileno:extranjero;
    }
    
    /*
    public List<EstadoCivil> buscarEstadosCiviles(String identificador){
        List<EstadoCivil> lista = null;
        try{
            consulta = "SELECT estados FROM poblacion INNER JOIN  WHERE identificador = \""+identificador+"\"";
            con = conexion.getConnection();
            
            st = con.prepareStatement(consulta);
            rs = st.executeQuery();
            while(rs.next()){
                
            }
            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta = null;
            try{
                if(rs!=null)
                    rs.close();
                if(st!=null)
                    st.close();
                if(con!=null)
                    con.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }            
        
        return lista;
    }
    */
}