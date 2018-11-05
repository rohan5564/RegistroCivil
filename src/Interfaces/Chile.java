/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author Jean
 */
public interface Chile {
    
    
    enum REGIONES implements Chile{
        TARAPACA("tarapaca"), ANTOFAGASTA("antofagasta"), ATACAMA("atacama"), COQUIMBO("coquimbo"),
        VALPARAISO("valparaiso"), LIBERTADOR_GENERAL_BERNARDO_OHIGGINS("libertador general bernardo o'higgins"),
        MAULE("maule"), BIO_BIO("bio-bio"), LA_ARAUCANIA("la Araucania"), LOS_LAGOS("los Lagos"),
        AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO("aysen del general carlos ibanez del campo"),
        MAGALLANES_Y_ANTARTICA("magallanes y antartica"), REGION_METROPOLITANA("region metropolitana"), 
        LOS_RIOS("los rios"), ARICA_Y_PARINACOTA("arica y parinacota"), ÑUBLE("ñuble");
        
        private final String nombre;
        
        private REGIONES(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    
    enum TARAPACA{
        ALTO_HOSPICIO("alto hospicio"), IQUIQUE("iquique"), CAMIÑA("camiña"), 
        COLCHANE("colchane"), HUARA("huara"), PICA("pica"), POZO_ALMONTE("pozo almonte");
        
        private final String nombre;
        
        private TARAPACA(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum ANTOFAGASTA{
        ANTOFAGASTA("antofagasta"), MEJILLONES("mejillones"), SIERRA_GORDA("sierra gorda"),
        TALTAL("taltal"), CALAMA("calama"), OLLAGÜE("ollagüe"), 
        SAN_PEDRO_DE_ATACAMA("san pedro de atacama"), MARÍA_ELENA("maría elena"), TOCOPILLA("tocopilla");
        
        private final String nombre;
        
        private ANTOFAGASTA(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum ATACAMA{
        CHAÑARAL("chañaral"), DIEGO_DE_ALMAGRO("diego de almagro"), CALDERA("caldera"), 
        COPIAPÓ("copiapó"), TIERRA_AMARILLA("tierra amarilla"), 
        ALTO_DEL_CARMEN("alto del carmen"), FREIRINA("freirina"), HUASCO("huasco"), VALLENAR("vallenar");
        
        private final String nombre;
        
        private ATACAMA(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum COQUIMBO{
        CANELA("canela"), ILLAPEL("illapel"), LOS_VILOS("los vilos"), SALAMANCA("slamanca"), 
        ANDACOLLO("andacollo"), COQUIMBO("coquimbo"), LA_HIGUERA("la higuera"),
        LA_SERENA("la serena"), PAIHUANO("paihuano"), VICUÑA("vicuña"), COMBARBALÁ("combarbalá"), 
        MONTE_PATRIA("monte patria"), OVALLE("ovalle"),PUNITAQUI("punitaqui"),
        RÍO_HURTADO("río hurtado");
        
        private final String nombre;
        
        private COQUIMBO(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    
    enum VALPARAISO{
        ISLA_DE_PASCUA("isla de pascua"), CALLE_LARGA("calle larga"), LOS_ANDES("los andes"),
        RINCONADA_DE_LOS_ANDES("rinconada de los andes"), SAN_ESTEBAN("san esteban"),
        LIMACHE("limache"), OLMUÉ("olmué"), QUILPUÉ("quilpué"), VILLA_ALEMANA("villa alemana"), 
        CABILDO("cabildo"), LA_LIGUA("la ligua"), PAPUDO("papudo"), PETORCA("petorca"),
        ZAPALLAR("zapallar"), HIJUELAS("hijuelas"), LA_CALERA("la calera"), LA_CRUZ("la cruz"), NOGALES("nogales"),
        QUILLOTA("quillota"), ALGARROBO("algarrobo"), CARTAGENA("cartagena"),EL_QUISCO("el quisco"), 
        EL_TABO("el tabo"), SAN_ANTONIO("san antonio"), SANTO_DOMINGO("santo domingo"), CATEMU("catemu"),
        LLAILLAY("llaillay"), PANQUEHUE("panquehue"),PUTAENDO("putaendo"), SAN_FELIPE("san felipe"), 
        SANTA_MARÍA("santa maría"), CASABLANCA("casablanca"), CONCÓN("concón"), 
        JUAN_FERNÁNDEZ("juan fernández"), PUCHUNCAVÍ("puchuncaví"),QUINTERO("quintero"), 
        VALPARAÍSO("valparaíso"), VIÑA_DEL_MAR("viña del mar");
        
        private final String nombre;
        
        private VALPARAISO(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum LIBERTADOR_GENERAL_BERNARDO_OHIGGINS{
        CODEGUA("codegua"), COÍNCO("coínco"), COLTAUCO("coltauco"), DOÑIHUE("doñihue"), 
        GRANEROS("graneros"), LAS_CABRAS("las cabras"), MACHALÍ("machalí"), MALLOA("malloa"), 
        OLIVAR("olivar"), PEUMO("peumo"), PICHIDEGUA("pichidegua"), 
        QUINTA_DE_TILCOCO("quinta de tilcoco"), RANCAGUA("rancagua"), REQUÍNOA("requínoa"), RENGO("rengo"), 
        SAN_FRANCISCO_DE_MOSTAZAL("san francisco de mostazal"), SAN_VICENTE_DE_TAGUA_TAGUA("san vicente de tagua tagua"), 
        LA_ESTRELLA("la estrella"), LITUECHE("litueche"), MARCHIGÜE("marchigüe"), NAVIDAD("navidad"),
        PAREDONES("paredones"), PICHILEMU("pichilemu"), CHÉPICA("chépica"), CHIMBARONGO("chimbarongo"), LOLOL("lolol"),
        NANCAGUA("nancagua"), PALMILLA("palmilla"), PERALILLO("peralillo"), PLACILLA("placilla"), 
        PUMANQUE("pumanque"), SAN_FERNANDO("san fernando"), SANTA_CRUZ("santa cruz");
        
        private final String nombre;
        
        private LIBERTADOR_GENERAL_BERNARDO_OHIGGINS(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum MAULE{
        CAUQUENES("cauquenes"), CHANCO("chanco"), PELLUHUE("pelluhue"), CURICÓ("curicó"),
        HUALAÑÉ("hualañé"), LICANTÉN("hualañé"), MOLINA("molina"), RAUCO("rauco"), ROMERAL("romeral"),
        SAGRADA_FAMILIA("sagrada familia"), TENO("teno"), VICHUQUÉN("vichuquén"), COLBÚN("colbún"),
        LINARES("linares"), LONGAVÍ("longaví"), PARRAL("parral"), RETIRO("retiro"),
        SAN_JAVIER_DE_LONCOMILLA("san javier de loncomilla"), VILLA_ALEGRE("villa alegre"), YERBAS_BUENAS("yerbas buenas"),
        CONSTITUCIÓN("constitución"), CUREPTO("curepto"), EMPEDRADO("empedrado"), MAULE("maule"),
        PELARCO("pelarco"), PENCAHUE("pencahue"), RÍO_CLARO("río claro"), SAN_CLEMENTE("san clemente"), 
        SAN_RAFAEL("san rafael"), TALCA("talca");
        
        private final String nombre;
        
        private MAULE(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum BIO_BIO{
        ARAUCO("arauco"), CAÑETE("cañete"), CONTULMO("contulmo"), CURANILAHUE("curanilahue"), 
        LEBU("lebu"), LOS_ÁLAMOS("los álamos"), TIRÚA("tirúa"), ALTO_BIOBÍO("alto biobío"), 
        ANTUCO("antuco"), CABRERO("cabrero"), LAJA("laja"), LOS_ÁNGELES("los ángeles"), 
        MULCHÉN("mulchén"), NACIMIENTO("nacimiento"), NEGRETE("negrete"), QUILACO("quilaco"), 
        QUILLECO("quilleco"), SAN_ROSENDO("san rosendo"), SANTA_BÁRBARA("santa bárbara"), TUCAPEL("tucapel"), 
        YUMBEL("yumbel"), CHIGUAYANTE("chiguayante"), CONCEPCIÓN("concepción"), 
        CORONEL("coronel"), FLORIDA("florida"), HUALPÉN("hualpén"), HUALQUI("hualqui"), LOTA("lota"), 
        PENCO("penco"), SAN_PEDRO_DE_LA_PAZ("san pedro de la paz"), SANTA_JUANA("santa juana"), 
        TALCAHUANO("talcahuano"), TOMÉ("tomé");
        
        private final String nombre;
        
        private BIO_BIO(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum LA_ARAUCANIA{
        CARAHUE("carahue"), CHOLCHOL("cholchol"), CUNCO("cunco"), CURARREHUE("curarrehue"), 
        FREIRE("freire"), GALVARINO("galvarino"), GORBEA("gorbea"), LAUTARO("lautaro"), 
        LONCOCHE("loncoche"), MELIPEUCO("melipeuco"), NUEVA_IMPERIAL("nueva imperial"), PADRE_LAS_CASAS("padre las casas"), 
        PERQUENCO("perquenco"), PITRUFQUÉN("pitrufquén"), PUCÓN("pucón"), SAAVEDRA("saavedra"), 
        TEMUCO("temuco"), TEODORO_SCHMIDT("teodoro schmidt"), TOLTÉN("toltén"), VILCÚN("vilcún"), 
        VILLARRICA("villarrica"), ANGOL("angol"), COLLIPULLI("collipulli"), CURACAUTÍN("curacautín"), 
        ERCILLA("ercilla"), LONQUIMAY("lonquimay"), LOS_SAUCES("los sauces"), LUMACO("lumaco"), 
        PURÉN("purén"), RENAICO("renaico"), TRAIGUÉN("traiguén"), VICTORIA("victoria");
        
        private final String nombre;
        
        private LA_ARAUCANIA(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum LOS_LAGOS{
      ANCUD("ancud"), CASTRO("castro"), CHONCHI("chonchi"), CURACO_DE_VÉLEZ("curaco de vélez"), 
      DALCAHUE("dalcahue"), PUQUELDÓN("puqueldón"), QUEILÉN("queilén"), QUELLÓN("quellón"), 
      QUEMCHI("quemchi"), QUINCHAO("quinchao"), CALBUCO("calbuco"), COCHAMÓ("cochamó"), 
      FRESIA("fresia"), FRUTILLAR("frutillar"), LLANQUIHUE("llanquihue"), LOS_MUERMOS("los muermos"), 
      MAULLÍN("maullín"), PUERTO_MONTT("puerto montt"), PUERTO_VARAS("puerto varas"), OSORNO("osorno"), 
      PUERTO_OCTAY("puerto octay"), PURRANQUE("purranque"), PUYEHUE("puyehue"),
      RÍO_NEGRO("río negro"), SAN_PABLO("san pablo"), SAN_JUAN_DE_LA_COSTA("san juan de la costa"), 
      CHAITÉN("chaitén"), FUTALEUFÚ("futaleufú"), HUALAIHUÉ("hualaihué"), PALENA("palena");
      
      private final String nombre;
        
        private LOS_LAGOS(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }    
    enum AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO{
        AYSÉN("aysén"), CISNES("cisnes"), GUAITECAS("guaitecas"), COCHRANE("cochrane"), 
        OHIGGINS("o'higgins"), TORTEL("tortel"), COYHAIQUE("coyhaique"), 
        LAGO_VERDE("lago verde"), CHILE_CHICO("chile chico"), RÍO_IBÁÑEZ("río ibáñez");
        
        private final String nombre;
        
        private AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum MAGALLANES_Y_ANTARTICA{
        ANTÁRTICA("antártica"), CABO_DE_HORNOS("cabo de hornos"), LAGUNA_BLANCA("laguna blanca"), 
        PUNTA_ARENAS("punta arenas"), RÍO_VERDE("río verde"), SAN_GREGORIO("san gregorio"), 
        PORVENIR("porvenir"), PRIMAVERA("primavera"), TIMAUKEL("timaukel"), 
        NATALES("natales"), TORRES_DEL_PAINE("torres del paine");
        
        private final String nombre;
        
        private MAGALLANES_Y_ANTARTICA(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum REGION_METROPOLITANA{
       COLINA("colina"), LAMPA("lampa"), TILTIL("tiltil"), PIRQUE("pirque"), 
       PUENTE_ALTO("puente alto"), SAN_JOSÉ_DE_MAIPO("san josé de maipo"), BUIN("buin"), 
       CALERA_DE_TANGO("calera de tango"), PAINE("paine"), SAN_BERNARDO("san bernardo"), 
       ALHUÉ("alhué"), CURACAVÍ("curacaví"), MARÍA_PINTO("maría pinto"), MELIPILLA("melipilla"), 
       SAN_PEDRO("san pedro"), CERRILLOS("cerrillos"), CERRO_NAVIA("cerro navia"), CONCHALÍ("conchalí"),
       EL_BOSQUE("el bosque"), ESTACIÓN_CENTRAL("estación central"), HUECHURABA("huechuraba"), INDEPENDENCIA("independencia"), 
       LA_CISTERNA("la cisterna"), LA_GRANJA("la granja"), LA_FLORIDA("la florida"), LA_PINTANA("la pintana"),
       LA_REINA("la reina"), LAS_CONDES("las condes"), LO_BARNECHEA("lo barnechea"), 
       LO_ESPEJO("lo espejo"), LO_PRADO("lo prado"), MACUL("macul"), MAIPÚ("maipú"), ÑUÑOA("ñuñoa"), 
       PEDRO_AGUIRRE_CERDA("pedro aguirre cerda"), PEÑALOLÉN("peñalolén"), PROVIDENCIA("providencia"), 
       PUDAHUEL("pudahuel"), QUILICURA("quilicura"), QUINTA_NORMAL("quinta normal"), RECOLETA("recoleta"), 
       RENCA("renca"), SAN_MIGUEL("san miguel"), SAN_JOAQUÍN("san joaquín"), SAN_RAMÓN("san ramón"),
       SANTIAGO("santiago"), VITACURA("vitacura"), EL_MONTE("el monte"), ISLA_DE_MAIPO("isla de maipo"), 
       PADRE_HURTADO("padre hurtado"), PEÑAFLOR("peñaflor"), TALAGANTE("talagante");
       
       private final String nombre;
        
        private REGION_METROPOLITANA(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum LOS_RIOS{
        FUTRONO("futrono"), LA_UNIÓN("la unión"), LAGO_RANCO("lago ranco"), RÍO_BUENO("río bueno"), 
        CORRAL("corral"), LANCO("lanco"), LOS_LAGOS("los lagos"), MÁFIL("máfil"),
        MARIQUINA("mariquina"), PAILLACO("paillaco"), PANGUIPULLI("panguipulli"), VALDIVIA("valdivia");
        
        private final String nombre;
        
        private LOS_RIOS(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum ARICA_Y_PARINACOTA{
        ARICA("arica"),CAMARONES("camarones"),GENERAL_LAGOS("general lagos"),PUTRE("putre");
        
        private final String nombre;
        
        private ARICA_Y_PARINACOTA(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    enum ÑUBLE{
        BULNES("bulnes"), CHILLÁN("chillán"), CHILLÁN_VIEJO("chillán viejo"), EL_CARMEN("el carmen"), 
        PEMUCO("pemuco"), PINTO("pinto"), QUILLÓN("quillón"), SAN_IGNACIO("san ignacio"), 
        YUNGAY("yungay"), COBQUECURA("cobquecura"), COELEMU("coelemu"), NINHUE("ninhue"), 
        PORTEZUELO("portezuelo"), QUIRIHUE("quirihue"), RÁNQUIL("ránquil"), TREGUACO("treguaco"), 
        COIHUECO("coihueco"), ÑIQUÉN("ñiquén"), SAN_CARLOS("san carlos"), SAN_FABIÁN("san fabián"), 
        SAN_NICOLÁS("san nicolás");
        
        private final String nombre;
        
        private ÑUBLE(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
    }
    
}