package utilidades;


import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import Enums.Visa;
import Excepciones.CantidadParentescoException;
import Excepciones.FormatoPasaporteException;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import GUI_RegistroCivil.Elementos;
import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Extranjero;
import colecciones.Operador;
import colecciones.Parientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConexionBD{
    
    private static ConexionBD conexionbd;
    private static ComboPooledDataSource conexion;
    private boolean status;
    private String strE = "";
    private String strN = "";
    private String strP = "";
    
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
            consulta =  "SELECT * "+
                        "FROM operador "+
                        "WHERE usuario = \"" + user + "\"";
            con = conexion.getConnection();
            
            st = con.prepareStatement(consulta);
            rs = st.executeQuery();
            rs.next(); //avanza a la siguiente tupla (actualmente al primero)
            if(rs.getString("contraseña").equals(pass))
                this.status = true;
                
        }catch (SQLException e) {
            System.err.println("excepcion: " + e.getMessage());
            Elementos.notificar("Error", "datos invalidos");
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
     * permite contar el total de extranjeros registrados
     * @return total de extranjeros registrados en la BD
     */
    public HashMap<String, Integer> totalExtranjerosPorVisa(){
        HashMap<String, Integer> total = new HashMap<>();
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            consulta = "select tipoVisa as \"visa\", count(*) as \"total\" "+
            "from extranjero group by tipoVisa; ";
            
            con = conexion.getConnection();
            st = con.prepareStatement(consulta);
            rs = st.executeQuery();
            while(rs.next()){
                total.put(rs.getString("visa"), Integer.parseInt(rs.getString("total")));
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
        return total.isEmpty()?null:total;
    }
    
    /**
     * permite contar el totalChilenos de chilenos registrados
     * @return totalChilenos de registrados en la BD
     */
    public int totalChilenos(){
        int total = 0;
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            consulta = "select count(*) as \"total\" "+
            "from chileno; ";
            
            con = conexion.getConnection();
            st = con.prepareStatement(consulta);
            rs = st.executeQuery();
            if(rs.next()){
                total = Integer.parseInt(rs.getString("total"));
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
        return total;
    }
    
    /**
     * permite separar la cantidad de chilenos por region
     * @return mapa con totalChilenos de personas por region
     */
    public Map<String, Integer> totalPorRegion(){
        HashMap<String, Integer> total = new HashMap<>();
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            consulta = "select regionNacimiento as \"region\", count(*) as \"total\" "+
            "from LugarDeNacimiento "+
            "group by regionNacimiento; ";
            
            con = conexion.getConnection();
            st = con.prepareStatement(consulta);
            rs = st.executeQuery();
            while(rs.next()){
                total.put(rs.getString("region"), Integer.parseInt(rs.getString("total")));
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
        return total.isEmpty()?null:total;
    }
    
    /**
     * permite agregar un nuevo extranjero en la base de datos
     * @param ex extranjero a crear en la base de datos
     */
    public void crearExtranjero(Extranjero ex){
        boolean seEncuentra = false;
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            if(ex.getEstadoCivil().size()>0){
                for(EstadoCivil e : ex.getEstadoCivil()){
                    strE = strE+"REPLACE INTO estadoCivil(idCiudadano, estadoCivil) "+
                            "VALUES ('"+ex.getPasaporte()+"', \""+e.getNombreMasculino()+"\"); ";
                }
                
            }
            
            if(ex.getNacionalidades().size()>0){
                for(Nacionalidad n : ex.getNacionalidades()){
                    strN = strN+"REPLACE INTO nacionalidad(idCiudadano, nacionalidad) "+
                            "VALUES ('"+ex.getPasaporte()+"', \""+n.getNombre()+"\"); ";
                }
            }
            
            if(ex.getParientes().totalParientes()>0){
                ex.getParientes().getPersonas().forEach((k,v)->{
                    v.getListadoParientes().forEach(pariente->{
                        strP = strP+"REPLACE INTO parentesco(idCiudadano, idPariente, relacionP_C) "+
                                "VALUES (\""+ex.getPasaporte()+"\", \""+pariente+"\", , \""+k.getNombreMasculino()+"\"); ";
                    });
                });
            }
            
            consulta = "REPLACE INTO ciudadano(esChileno, idCiudadano, ubicacionActual, nombre, apellido, sexo, fechaNacimiento, horaNacimiento) "+
                        "VALUES ('N', '"+ex.getPasaporte()+"', \""+Operador.getInstancia().getRegion()+"\", \""+ex.getNombre()+"\", \""+ex.getApellido()+"\", '"+ex.getSexo().getNombre()+"', '"+ex.getNacimiento().toString()+"', '"+ex.getHoraNacimiento()+"'); "+
                        "REPLACE INTO extranjero(pasaporte, tipoVisa, fechaPrimeraVisa) "+
                        "VALUES ('"+ex.getPasaporte()+"', \""+ex.getTipoDeVisa().getNombre()+"\", \""+ex.getPrimeraVisa().toString()+"\"); "+
                        strE+strN+strP;
            
            con = conexion.getConnection();
            st = con.prepareStatement(consulta);
            st.executeUpdate();                            
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta = null;
            strE = "";
            strN = "";
            strP = "";
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
     * permite agregar un chileno en la base de datos
     * @param ch chileno a crear en la base de datos
     */
    public void crearChileno(Chileno ch){
        String consulta = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{            
            if(ch.getEstadoCivil().size()>0){
                for(EstadoCivil e : ch.getEstadoCivil()){
                    strE = strE+"REPLACE INTO estadoCivil(idCiudadano, estadoCivil) "+
                            "VALUES ('"+ch.getRut()+"', \""+e.getNombreMasculino()+"\"); ";
                }
                
            }
            
            if(ch.getNacionalidades().size()>0){
                for(Nacionalidad n : ch.getNacionalidades()){
                    strN = strN+"REPLACE INTO nacionalidad(idCiudadano, nacionalidad) "+
                            "VALUES ('"+ch.getRut()+"', \""+n.getNombre()+"\"); ";
                }
            }
            
            if(ch.getParientes().totalParientes()>0){
                ch.getParientes().getPersonas().forEach((k,v)->{
                    v.getListadoParientes().forEach(pariente->{
                        strP = strP+"REPLACE INTO parentesco(idCiudadano, idPariente, relacionP_C) "+
                                "VALUES (\""+ch.getRut()+"\", \""+pariente+"\", , \""+k.getNombreMasculino()+"\"); ";
                    });
                });
            }
            
            consulta = "REPLACE INTO ciudadano(esChileno, idCiudadano, ubicacionActual, nombre, apellido, sexo, fechaNacimiento, horaNacimiento) "+
                        "VALUES ('Y', '"+ch.getRut()+"', \""+ch.getRegionDeNacimiento()+"\", \""+ch.getNombre()+"\", \""+ch.getApellido()+"\", '"+ch.getSexo().getNombre()+"', '"+ch.getNacimiento().toString()+"', '"+ch.getHoraNacimiento()+"'); "+
                        "REPLACE INTO chileno(rut";
                    
            if(ch.getNumeroDeDocumento()!=null)
                consulta+= ", numeroDeDocumento";
            if(ch.getDireccion()!=null)
                consulta+=", direccion\"";  
            consulta+=") VALUES ('"+ch.getRut()+"'";
            if(ch.getNumeroDeDocumento()!=null)
                consulta+= ", \""+ch.getNumeroDeDocumento()+"\"";
            if(ch.getDireccion()!=null)
                consulta+=", \""+ch.getDireccion()+"\"";
            consulta+=");\n REPLACE INTO lugardenacimiento(rut, comunaNacimiento, regionNacimiento) "+
                        "VALUES('"+ch.getRut()+"', \""+ch.getComunaDeNacimiento()+"\", \""+ch.getRegionDeNacimiento()+"\"); "+
                        strE+strN+strP;

            
            con = conexion.getConnection();                           
            st = con.prepareStatement(consulta);
            st.executeUpdate();                     
        }catch (Exception e) {
            System.err.println("excepcion: " + e.getMessage());
        }finally {
            consulta = null;
            strE = "";
            strN = "";
            strP = "";
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
     * permite buscar a un chileno en la base de datos
     * @param identificador identificador del chileno
     * @return si encuentra al chileno en la base de datos lo retorna, sino returna null
     */
    public Chileno buscarChileno(String identificador){
        Chileno chileno = null;
        String consulta;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            boolean esChileno = Chileno.comprobarRut(identificador);
            if(esChileno){
                consulta =  "select * "+
                            "from personachilena as persona "+
                            "where persona.rut = \""+identificador+"\";";
                
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                rs = st.executeQuery();
                if(rs.next()){
                    chileno = new Chileno.BuilderChileno()
                    .setRut(rs.getString("rut"))
                    .setNumeroDeDocumento(rs.getString("numero de documento"))
                    .setDireccion(rs.getString("direccion"))
                    .setRegionDeNacimiento(rs.getString("region de nacimiento"))
                    .setComunaDeNacimiento(rs.getString("comuna de nacimiento"))
                    .setNombre(rs.getString("nombre"))
                    .setApellido(rs.getString("apellido"))
                    .setSexo(Sexo.valorDe(rs.getString("sexo").toUpperCase()))
                    .setNacimiento(rs.getDate("fecha de nacimiento").toLocalDate())
                    .setHoraNacimiento(rs.getString("hora de nacimiento"))
                    .setComentarioNacimiento(rs.getString("comentario de nacimiento"))
                    .setDefuncion(rs.getDate("fecha de defuncion")!=null?rs.getDate("fecha de defuncion").toLocalDate():null)
                    .setHoraDefuncion(rs.getString("hora de defuncion")!=null?rs.getString("hora de defuncion"):null)
                    .setComentarioDefuncion(rs.getString("comentario de defuncion")!=null?rs.getString("comentario de defuncion"):null)
                    .setProfesion(rs.getString("profesion")!=null?rs.getString("profesion"):null)
                    .setEstadoCivil(buscarEstadosCiviles(identificador))
                    .setNacionalidades(buscarNacionalidades(identificador))
                    .setParientes(buscarParientes(identificador))
                    .build();
                }
            }
        }catch(FormatoRutException | LongitudRutException e){
            Elementos.notificar("Error", "error en el formato y/o longitud del rut");
        }catch(SQLException e){
            Elementos.notificar("Error", "error en cargar la base de datos");
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
                Elementos.notificar("Error", "error al cerrar la base de datos");
            }
        }
        
        return chileno;
    }
    
    
    /**
     * permite buscar a un extranjero en la base de datos
     * @param identificador identificador del extranjero
     * @return si encuentra al extranjero en la base de datos lo retorna, sino returna null
     */
    public Extranjero buscarExtranjero(String identificador){
        Extranjero extranjero = null;
        String consulta;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            boolean esExtranjero = Extranjero.comprobarPasaporte(identificador);
            if(esExtranjero){
                consulta =  "select * "+
                            "from personaextranjera as persona "+
                            "where persona.pasaporte = \""+identificador+"\";";
                con = conexion.getConnection();
            
                st = con.prepareStatement(consulta);
                rs = st.executeQuery();
                
                if(rs.next()){
                    extranjero = new Extranjero.BuilderExtranjero()
                            .setPasaporte(rs.getString("pasaporte"))
                            .setTipoDeVisa(Visa.valorDe(rs.getString("tipo de visa")))
                            .setPrimeraVisa(rs.getDate("primera visa").toLocalDate())
                            .setNombre(rs.getString("nombre"))
                            .setApellido(rs.getString("apellido"))
                            .setSexo(Sexo.valorDe(rs.getString("sexo")))
                            .setNacimiento(rs.getDate("fecha de nacimiento").toLocalDate())
                            .setHoraNacimiento(rs.getString("hora de nacimiento"))
                            .setComentarioNacimiento(rs.getString("comentario de nacimiento"))
                            .setDefuncion(rs.getDate("fecha de defuncion")!=null?rs.getDate("fecha de defuncion").toLocalDate():null)
                            .setHoraDefuncion(rs.getString("hora de defuncion")!=null?rs.getString("hora de defuncion"):null)
                            .setComentarioDefuncion(rs.getString("comentario de defuncion")!=null?rs.getString("comentario de defuncion"):null)
                            .setProfesion(rs.getString("profesion")!=null?rs.getString("profesion"):null)
                            .setEstadoCivil(buscarEstadosCiviles(identificador))
                            .setNacionalidades(buscarNacionalidades(identificador))
                            .setParientes(buscarParientes(identificador))
                            .build();
                }
            } 
        }catch(FormatoPasaporteException e){
            Elementos.notificar("Error", "error en el formato del pasaporte");
        }catch(SQLException e){
            Elementos.notificar("Error", "error en cargar la base de datos");
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
                Elementos.notificar("Error", "error al cerrar la base de datos");
            }
        }
        return extranjero;
    }
    
    
    /**
     * permite buscar a un ciudadano en la base de datos, sea chileno o extranjero
     * @param identificador identificador del ciudadano
     * @return si encuentra al ciudadano en la base de datos lo retorna, sino returna null
     */
    public Ciudadano buscarCiudadano(String identificador){
        return buscarChileno(identificador)!=null?buscarChileno(identificador):buscarExtranjero(identificador);
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
            boolean esChileno = Chileno.comprobarRut(identificador);
            boolean esExtranjero = Extranjero.comprobarPasaporte(identificador);
            if(esChileno){
                consulta__ =    "select idPariente as \"pariente\", relacionP_C as \"parentesco\" "+
                                "from personaChilena as persona inner join parentesco as p on persona.rut = p.idCiudadano "+
                                "where persona.rut = \""+identificador+"\";";
            }else if(esExtranjero){
                consulta__ =    "select idPariente as \"pariente\", relacionP_C as \"parentesco\" "+
                                "from personaExtranjera as persona inner join parentesco as p on persona.pasaporte = p.idCiudadano "+
                                "where persona.pasaporte = \""+identificador+"\";";
            }
            con__ = conexion.getConnection();
            
            st__ = con__.prepareStatement(consulta__);
            rs__ = st__.executeQuery();
            while(rs__.next()){
                parientes.agregarPariente(
                        ((Ciudadano)buscarCiudadano(rs__.getString("pariente"))).mostrarIdentificador(), EstadoCivil.valorDe(rs__.getString("parentesco").toUpperCase()));
                
            }  
        }catch(FormatoRutException | LongitudRutException e){
            Elementos.notificar("Error", "error en el formato y/o longitud del rut");
        }catch(CantidadParentescoException e){
            Elementos.notificar("Error", "cantidad de parientes por estado excedida");
        }catch(FormatoPasaporteException e){
            Elementos.notificar("Error", "error en el formato del pasaporte");
        }catch(SQLException e){
            Elementos.notificar("Error", "error en cargar la base de datos");
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
                Elementos.notificar("Error", "error al cerrar la base de datos");
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
            boolean esChileno = Chileno.comprobarRut(identificador);
            boolean esExtranjero = Extranjero.comprobarPasaporte(identificador);
            if(esChileno){
                consulta_ = "select nacionalidad "+
                            "from personaChilena as persona inner join nacionalidad as n on persona.rut = n.idCiudadano "+
                            "where persona.rut = \""+identificador+"\";";
            }else if(esExtranjero){
                consulta_ = "select nacionalidad "+
                            "from personaExtranjera as persona inner join nacionalidad as n on persona.pasaporte = n.idCiudadano "+
                            "where persona.pasaporte = \""+identificador+"\";";
            }
            con_ = conexion.getConnection();
            
            st_ = con_.prepareStatement(consulta_);
            rs_ = st_.executeQuery();
            while(rs_.next()){
                lista.add(Nacionalidad.valorDe(rs_.getString("nacionalidad").toUpperCase()));
            }
        }catch(FormatoRutException | LongitudRutException e){
            Elementos.notificar("Error", "error en el formato y/o longitud del rut");
        }catch(FormatoPasaporteException e){
            Elementos.notificar("Error", "error en el formato del pasaporte");
        }catch(SQLException e){
            Elementos.notificar("Error", "error en cargar la base de datos");
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
                Elementos.notificar("Error", "error al cerrar la base de datos");
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
            boolean esChileno = Chileno.comprobarRut(identificador);
            boolean esExtranjero = Extranjero.comprobarPasaporte(identificador);
            if(esChileno){
                consulta_ = "select estadoCivil as \"estado civil\" "+
                            "from personaChilena as persona inner join estadocivil as n on persona.rut = n.idCiudadano "+
                            "where persona.rut = \""+identificador+"\";";
            }else if(esExtranjero){
                consulta_ = "select estadoCivil as \"estado civil\" "+
                            "from personaExtranjera as persona inner join estadocivil as n on persona.pasaporte = n.idCiudadano "+
                            "where persona.pasaporte = \""+identificador+"\";";
            }
            con_ = conexion.getConnection();
            
            st_ = con_.prepareStatement(consulta_);
            rs_ = st_.executeQuery();
            while(rs_.next()){
                lista.add(EstadoCivil.valorDe(rs_.getString("estado civil").toUpperCase()));
            }
        }catch(FormatoRutException | LongitudRutException e){
            Elementos.notificar("Error", "error en el formato y/o longitud del rut");
        }catch(FormatoPasaporteException e){
            Elementos.notificar("Error", "error en el formato del pasaporte");
        }catch(SQLException e){
            Elementos.notificar("Error", "error en cargar la base de datos");
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
                Elementos.notificar("Error", "error al cerrar la base de datos");
            }
        }            
        
        return lista.isEmpty()?null:lista;
    }
    
}