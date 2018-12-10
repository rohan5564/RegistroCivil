package utilidades;


import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import Enums.Visa;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Extranjero;
import colecciones.Parientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class ConexionBD{
    
    private static ConexionBD conexionbd;
    private static ComboPooledDataSource conexion;
    private boolean status;
    
    private ConexionBD(){
        conexion = null;
        status = false;
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
    
    /**
     * permite loguearse en el sistema buscando la informacion perteneciente al usuario y la contraseña
     * del individuo en la base de datos
     * @param user usuario a buscar en la base de datos
     * @param pass contraseña asignada al usuario
     * @return true si ingresa correctamente a la base de datos, false en caso contrario
     */
    public boolean login(String user, String pass){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            consulta = "SELECT * FROM operador WHERE usuario = \"" + user + "\"";
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
    
    /**
     * 
     * @param c ciudadano a crear
     * @return true si no existe y se guarda en la base de datos, false en caso
     * de que ya exista
     */
    public boolean crearCiudadano(Ciudadano c){
        boolean seEncuentra = false;
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            if(c instanceof Chileno){
                Chileno ch = (Chileno)c;
                consulta = "INSERT INTO poblacion (identificador)"
                        + "VALUES("+ch.getRut()+");"
                        + "INSERT INTO poblacion_chilena (nombre, apellido, sexo, fecha_de_nacimiento, hora_de_nacimiento,"
                        + "comentario_de_nacimiento, fecha_de_defuncion, hora_de_defuncion, comentario_de_defuncion, profesion, rut,"
                        + "numero_de_documento, direccion, region_de_nacimiento, comuna_de_nacimiento)"
                        + "VALUES("+ch.getNombre()+","+ch.getApellido()+","+ch.getSexo().getNombre()+","+ch.getNacimiento()+","
                        +ch.getHoraNacimiento()+","+ch.getComentarioNacimiento()+","+ch.getDefuncion()+","+ch.getHoraDefuncion()+","
                        +ch.getComentarioDefuncion()+","+ch.getProfesion()+","+ch.getRut()+","+ch.getNumeroDeDocumento()+","+ch.getDireccion()
                        +","+ch.getRegionDeNacimiento()+","+ch.getComunaDeNacimiento()+");"
                        ;
                
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                st.executeUpdate();
                
            }
            else{
                consulta = "SELECT COUNT(*) "
                        + "FROM ("
                        + "SELECT *"
                        + "FROM poblacion_extranjera AS P"
                        + "WHERE P.identificador = \""+c.mostrarIdentificador()+"\""
                        + ")";
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                rs = st.executeQuery();
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
        return seEncuentra;
    }
    
    /**
     * permite buscar a un ciudadano en la base de datos
     * @param identificador identificador del ciudadano
     * @return si encuentra al ciudadano en la base de datos lo retorna, sino
     * returna null
     */
    public Object buscarCiudadano(String identificador)throws FormatoRutException, LongitudRutException{
        Chileno chileno = null;
        Extranjero extranjero = null;
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean esChileno = Chileno.comprobarRut(identificador);
        try{
            if(esChileno){
                consulta = "SELECT * "
                        + "FROM poblacion_chilena AS P "
                        + "WHERE P.rut = \""+identificador+"\"";
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                rs = st.executeQuery();
                if(rs.next()){
                    chileno = new Chileno.BuilderChileno()
                    .setRut(rs.getString("rut"))
                    .setNumeroDeDocumento(rs.getString("numero_de_documento"))
                    .setDireccion(rs.getString("direccion"))
                    .setRegionDeNacimiento(rs.getString("region_de_nacimiento"))
                    .setComunaDeNacimiento(rs.getString("comuna_de_nacimiento"))
                    .setNombre(rs.getString("nombre"))
                    .setApellido(rs.getString("apellido"))
                    .setSexo(Sexo.valueOf(rs.getString("sexo").toUpperCase()))
                    .setNacimiento(rs.getDate("fecha_de_nacimiento").toLocalDate())
                    .setHoraNacimiento(rs.getString("hora_de_nacimiento"))
                    .setComentarioNacimiento(rs.getString("comentario_de_nacimiento"))
                    .setDefuncion(rs.getDate("fecha_de_defuncion").toLocalDate())
                    .setHoraDefuncion(rs.getString("hora_de_defuncion"))
                    .setComentarioDefuncion(rs.getString("comentario_de_defuncion"))
                    .setProfesion(rs.getString("profesion"))
                    .setEstadoCivil(buscarEstadosCiviles(identificador))
                    .setNacionalidades(buscarNacionalidades(identificador))
                    .setParientes(buscarParientes(identificador))
                    .build();
                }
            }
            else{
                consulta = "SELECT * "
                        + "FROM poblacion_extranjera AS P "
                        + "WHERE P.pasaporte = \""+identificador+"\"";
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                rs = st.executeQuery();
                
                if(rs.next()){
                    extranjero = new Extranjero.BuilderExtranjero()
                            .setPasaporte(rs.getString("pasaporte"))
                            .setTipoDeVisa(Visa.valueOf(rs.getString("tipo de visa")))
                            .setPrimeraVisa(rs.getDate("primera visa").toLocalDate())
                            .setNombre(rs.getString("nombre"))
                            .setApellido(rs.getString("apellido"))
                            .setSexo(Sexo.valueOf(rs.getString("sexo")))
                            .setNacimiento(rs.getDate("fecha de nacimiento").toLocalDate())
                            .setHoraNacimiento(rs.getString("hora de nacimiento"))
                            .setComentarioNacimiento(rs.getString("comentario de nacimiento"))
                            .setDefuncion(rs.getDate("fecha de defuncion").toLocalDate())
                            .setHoraDefuncion(rs.getString("hora de defuncion"))
                            .setComentarioDefuncion(rs.getString("comentario de defuncion"))
                            .setProfesion(rs.getString("profesion"))
                            .build();
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
    
    /**
     * busca los parientes de un ciudadano segun su identificador
     * @param identificador identificador del ciudadano
     * @return mapa de parientes que contiene el ciudadano. null en caso
     * de no ser encontrado
     */
    public Parientes buscarParientes(String identificador){
        Parientes parientes = new Parientes();
        String consulta__ = null;
        Connection con__ = null;
        PreparedStatement st__ = null;
        ResultSet rs__ = null;
        try{
            consulta__ = "SELECT id_pariente, parentesco FROM poblacion AS P INNER JOIN parientes AS F "
                    + "WHERE P.identificador = \""+identificador+"\" AND P.identificador = F.identificador";
            con__ = conexion.getConnection();
            
            st__ = con__.prepareStatement(consulta__);
            rs__ = st__.executeQuery();
            while(rs__.next()){
                parientes.agregarPariente(
                        ((Ciudadano)buscarCiudadano(rs__.getString("id_pariente"))).mostrarIdentificador(), EstadoCivil.valueOf(rs__.getString("parentesco").toUpperCase()));
                
            }
            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta__ = null;
            try{
                if(rs__!=null)
                    rs__.close();
                if(st__!=null)
                    st__.close();
                if(con__!=null)
                    con__.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }            
        
        return parientes.getPersonas().isEmpty()?null:parientes;
    }
            
    /**
     * busca las nacionalidades de un ciudadano segun su identificador
     * @param identificador identificador del ciudadano
     * @return una lista de nacionalidades que contiene el ciudadano. null en caso
     * de no ser encontrado
     */
    public List<Nacionalidad> buscarNacionalidades(String identificador){
        List<Nacionalidad> lista = new ArrayList<>();
        String consulta_ = null;
        Connection con_ = null;
        PreparedStatement st_ = null;
        ResultSet rs_ = null;
        try{
            consulta_ = "SELECT nacionalidad FROM poblacion AS P INNER JOIN nacionalidades AS N "
                    + "WHERE P.identificador = \""+identificador+"\" AND P.identificador = N.identificador";
            con_ = conexion.getConnection();
            
            st_ = con_.prepareStatement(consulta_);
            rs_ = st_.executeQuery();
            while(rs_.next()){
                lista.add(Nacionalidad.valueOf(rs_.getString("nacionalidad").toUpperCase()));
            }
            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta_ = null;
            try{
                if(rs_!=null)
                    rs_.close();
                if(st_!=null)
                    st_.close();
                if(con_!=null)
                    con_.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }            
        
        return lista.isEmpty()?null:lista;
    }
    
    
    /**
     * busca los estados civiles de un ciudadano segun su identificador
     * @param identificador identificador del ciudadano
     * @return una lista de estados civiles que contiene el ciudadano. null en caso
     * de no ser encontrado
     */
    public List<EstadoCivil> buscarEstadosCiviles(String identificador){
        List<EstadoCivil> lista = new ArrayList<>();
        String consulta_ = null;
        Connection con_ = null;
        PreparedStatement st_ = null;
        ResultSet rs_ = null;
        try{
            consulta_ = "SELECT estado FROM poblacion AS P INNER JOIN estados_civiles AS E "
                    + "WHERE P.identificador = \""+identificador+"\" AND P.identificador = E.identificador";
            con_ = conexion.getConnection();
            
            st_ = con_.prepareStatement(consulta_);
            rs_ = st_.executeQuery();
            while(rs_.next()){
                lista.add(EstadoCivil.valueOf(rs_.getString("estado").toUpperCase()));
            }
            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta_ = null;
            try{
                if(rs_!=null)
                    rs_.close();
                if(st_!=null)
                    st_.close();
                if(con_!=null)
                    con_.close();
            }catch (SQLException e){
                System.err.println("excepcion: " + e.getMessage());
            }
        }            
        
        return lista.isEmpty()?null:lista;
    }
    
}