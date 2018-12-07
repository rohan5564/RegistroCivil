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
import colecciones.ListadoParientes;
import colecciones.Parientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


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
    
    public boolean login(String user, String pass){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
    
    /**
     * crea las tablas necesarias para guardar la informacion si es que no existen
     * en la base de datos
     */
    public void crearTablas(){
        crearTablaPoblacion();
        crearTablaChilena();
        crearTablaExtranjera();
        crearTablaEstadosCiviles();
        crearTablaFamiliares();
        crearTablaNacionalidades();
    }
    
    /**
     * crea la tabla correspondieniente a las nacinalidades de los ciudadanos
     */
    private void crearTablaNacionalidades(){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            consulta = 
                    "CREATE TABLE IF NOT EXISTS nacionalidades"
                    +"(identificador                VARCHAR(20) PRIMARY KEY,"
                    +"nacionalidad                  VARCHAR(50) NOT NULL,"
                    + "FOREIGN KEY(identificador) REFERENCES poblacion(identificador))";
            
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
    
    /**
     * crea la tabla correspondieniente a poblacion
     */
    private void crearTablaPoblacion(){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            consulta = 
                    "CREATE TABLE IF NOT EXISTS poblacion"
                    +"(identificador                VARCHAR(20) PRIMARY KEY)";
            
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
    
    /**
     * crea la tabla correspondieniente a los parientes de cada ciudadano
     */
    private void crearTablaFamiliares(){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            consulta = 
                    "CREATE TABLE IF NOT EXISTS parientes"
                    +"(identificador                VARCHAR(20) NOT NULL,"
                    +"id_pariente                   VARCHAR(20) NOT NULL,"
                    +"parentesco                    VARCHAR(20) NOT NULL,"
                    + "FOREIGN KEY(identificador) REFERENCES poblacion(identificador))";
            
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
    
    /**
     * crea la tabla correspondieniente a los estados civiles de cada ciudadano
     */
    private void crearTablaEstadosCiviles(){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            consulta = 
                    "CREATE TABLE IF NOT EXISTS estados_civiles"
                    +"(identificador                VARCHAR(10) NOT NULL,"
                    +"estado                        VARCHAR(10) NOT NULL,"
                    + "FOREIGN KEY(identificador) REFERENCES poblacion(identificador))";
            
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
    
    /**
     * crea la tabla correspondieniente a poblacion chilena
     */
    private void crearTablaChilena(){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    +"comuna_de_nacimiento          VARCHAR(100) NOT NULL,"
                    +"FOREIGN KEY(rut) REFERENCES poblacion(identificador))";
            
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
    
    /**
     * crea la tabla correspondieniente a poblacion extranjera
     */
    private void crearTablaExtranjera(){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    +"primera_visa                  DATE NOT NULL,"
                    + "FOREIGN KEY(pasaporte) REFERENCES poblacion(identificador))";
            
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
                    chileno = new Chileno();
                    chileno.setNombre(rs.getString("nombre"));
                    chileno.setApellido(rs.getString("apellido"));
                    chileno.setSexo(Sexo.valueOf(rs.getString("sexo").toUpperCase()));
                    chileno.setNacimiento(rs.getDate("fecha_de_nacimiento").toLocalDate());
                    chileno.setHoraNacimiento(rs.getString("hora_de_nacimiento"));
                    chileno.setComentarioNacimiento(rs.getString("comentario_de_nacimiento"));
                    chileno.setDefuncion(rs.getDate("fecha_de_defuncion").toLocalDate());
                    chileno.setHoraDefuncion(rs.getString("hora_de_defuncion"));
                    chileno.setComentarioDefuncion(rs.getString("comentario_de_defuncion"));
                    chileno.setProfesion(rs.getString("profesion"));
                    chileno.setEstadoCivil(buscarEstadosCiviles(identificador));
                    chileno.setNacionalidades(buscarNacionalidades(identificador));
                    chileno.setParientes(buscarParientes(identificador));
                    
                    chileno.setRut(rs.getString("rut"));
                    chileno.setNumeroDeDocumento(rs.getString("numero_de_documento"));
                    chileno.setDireccion(rs.getString("direccion"));
                    chileno.setRegionDeNacimiento(rs.getString("region_de_nacimiento"));
                    chileno.setComunaDeNacimiento(rs.getString("comuna_de_nacimiento"));
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
                    extranjero = new Extranjero();
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