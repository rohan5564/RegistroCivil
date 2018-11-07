
package utilidades;

import colecciones.Operador;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;



public class ConexionMySql {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/datos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                settings.put(Environment.USER, "c3p0");
                settings.put(Environment.PASS, "64fa4632");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, true);

                // c3p0 configuration
                settings.put(Environment.C3P0_MIN_SIZE, 5);         //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, 20);        //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1);//Number of connections acquired at a time when pool is exhausted 
                settings.put(Environment.C3P0_TIMEOUT, 1800);       //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
                
                settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);
            
                registryBuilder.applySettings(settings);
            
                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Operador.class);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                if (registry != null) {
                   StandardServiceRegistryBuilder.destroy(registry);
                }
                e.printStackTrace();
             }
          }
          return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
