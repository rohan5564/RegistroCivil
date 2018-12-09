
package Enums;


public enum Nacionalidad {
    AFGANISTAN("afganistan"), ALBANIA("albania"), ALEMANIA("alemania"), 
    ANDORRA("andorra"), ANGOLA("angola"), ANTIGUA_Y_BARBUDA("antigua y barbuda"),
    ARABIA_SAUDITA("arabia saudita"), ARGELIA("argelia"), ARGENTINA("argentina"),
    ARMENIA("armenia"), AUSTRALIA("australia"), AUSTRIA("austria"), 
    AZERBAIYAN("azerbaiyan"), BAHAMAS("bahamas"), BANGLADES("banglades"), BARBADOS("barbados"), 
    BAREIN("barein"), BELGICA("belgica"), BELICE("belice"), BENIN("benin"), 
    BIELORRUSIA("bielorrusia"), BIRMANIA("birmania"), BOLIVIA("bolivia"), 
    BOSNIA_Y_HERZEGOVINA("bosnia y herzegovina"), BOTSUANA("botsuana"), 
    BRASIL("brasil"), BRUNEI("brunei"), BULGARIA("bulgaria"),
    BURKINA_FASO("burkina faso"), BURUNDI("burundi"), BUTAN("butan"), 
    CABO_VERDE("cabo verde"), CAMBOYA("camboya"), CAMERUN("camerun"), CANADA("canada"), 
    CATAR("catar"), CHAD("chad"), CHILE("chile"), CHINA("china"), CHIPRE("chipre"), 
    CIUDAD_DEL_VATICANO("ciudad del vaticano"), COLOMBIA("colombia"), COMORAS("comoras"),
    OREA_DEL_NORTE("corea del norte"), COREA_DEL_SUR("corea del sur"), 
    COSTA_DE_MARFIL("costa de marfil"), COSTA_RICA("costa rica"), CROACIA("croacia"), 
    CUBA("cuba"), DINAMARCA("dinamarca"), DOMINICA("dominica"), ECUADOR("ecuador"), 
    EGIPTO("egipto"), EL_SALVADOR("el salvador"), EMIRATOS_ARABES_UNIDOS("emiratos arabes unidos"),
    ERITREA("eritrea"), ESLOVAQUIA("eslovaquia"), ESLOVENIA("eslovenia"), ESPAÑA("españa"),
    ESTADOS_UNIDOS("estados unidos"), ESTONIA("estonia"), ETIOPIA("etiopia"),
    FILIPINAS("filipinas"), FINLANDIA("finlandia"), FIYI("fiyi"), FRANCIA("francia"), 
    GABON("gabon"), GAMBIA("gambia"), GEORGIA("georgia"), GHANA("ghana"), GRANADA("granada"),
    GRECIA("grecia"), GUATEMALA("guatemala"), GUYANA("guyana"), GUINEA("guinea"), 
    GUINEA_ECUATORIAL("guinea ecuatorial"), GUINEA_BISAU("guinea bisau"), HAITI("haiti"),
    HONDURAS("honduras"), HUNGRIA("hungria"), INDIA("india"), INDONESIA("indonesia"),
    IRAK("irak"), IRAN("iran"), IRLANDA("irlanda"), ISLANDIA("islandia"),
    ISLAS_MARSHALL("islas marshall"), ISLAS_SALOMON("islas salomon"), ISRAEL("israel"),
    ITALIA("italia"), JAMAICA("jamaica"), JAPON("japon"), JORDANIA("jordania"),
    KAZAJISTAN("kazajistan"), KENIA("kenia"), KIRGUISTAN("kirguistan"),
    KIRIBATI("kiribati"), KUWAIT("kuwait"), LAOS("laos"), LESOTO("lesoto"), LETONIA("letonia"),
    LIBANO("libano"), LIBERIA("liberia"), LIBIA("libia"), LIECHTENSTEIN("liechtenstein"),
    LITUANIA("lituania"), LUXEMBURGO("luxemburgo"), MADAGASCAR("madagascar"),
    MALASIA("malasia"), MALAUI("malaui"), MALDIVAS("maldivas"), MALI("mali"), 
    MALTA("malta"), MARRUECOS("marruecos"), MAURICIO("mauricio"), MAURITANIA("mauritania"),
    MEXICO("mexico"), MICRONESIA("micronesia"), MOLDAVIA("moldavia"), MONACO("monaco"),
    MONGOLIA("mongolia"), MONTENEGRO("montenegro"), MOZAMBIQUE("mozambique"),
    NAMIBIA("namibia"), NAURU("nauru"), NEPAL("nepal"), NICARAGUA("nicaragua"), 
    NIGER("niger"), NIGERIA("nigeria"), NORUEGA("noruega"), NUEVA_ZELANDA("nueva zelanda"),
    OMAN("oman"), PAISES_BAJOS("paises bajos"), PAKISTAN("pakistan"), PALAOS("palaos"), 
    PANAMA("panama"), PAPUA_NUEVA_GUINEA("papua nueva guinea"), PARAGUAY("paraguay"),
    PERU("peru"), POLONIA("polonia"), PORTUGAL("portugal"), 
    REINO_UNIDO("reino unido"), REPUBLICA_CENTROAFRICANA("republica centroafricana"),
    REPUBLICA_CHECA("republica checa"), REPUBLICA_DE_MACEDONIA("republica de macedonia"),
    REPUBLICA_DEL_CONGO("republica del congo"), REPUBLICA_DEMOCRATICA_DEL_CONGO("republica democratica del congo"),
    REPUBLICA_DOMINICANA("republica dominicana"), REPUBLICA_SUDAFRICANA("republica sudafricana"),
    RUANDA("ruanda"), RUMANIA("rumania"), RUSIA("rusia"), SAMOA("samoa"), 
    SAN_CRISTOBAL_Y_NIEVES("san cristobal y nieves"), SAN_MARINO("san_marino"),
    SAN_VICENTE_Y_LAS_GRANADINAS("san vicente y las granadinas"), SANTA_LUCIA("santa lucia"),
    SANTO_TOME_Y_PRINCIPE("santo tome y principe"), SENEGAL("senegal"),
    SERBIA("serbia"), SEYCHELLES("seychelles"), SIERRA_LEONA("sierra leona"), 
    SINGAPUR("singapur"), SIRIA("siria"), SOMALIA("somalia"), SRI_LANKA("sri_lanka"),
    SUAZILANDIA("suazilandia"), SUDAN("sudan"), SUDAN_DEL_SUR("sudan del sur"), SUECIA("suecia"),
    SUIZA("suiza"), SURINAM("surinam"), TAILANDIA("tailandia"), TANZANIA("tanzania"),
    TAYIKISTAN("tayikistan"), TIMOR_ORIENTAL("timor oriental"), TOGO("togo"), TONGA("tonga"), 
    TRINIDAD_Y_TOBAGO("trinidad y tobago"), TUNEZ("tunez"), TURKMENISTAN("turkmenistan"),
    TURQUIA("turquia"), TUVALU("tuvalu"), UCRANIA("ucrania"), UGANDA("uganda"),
    URUGUAY("uruguay"), UZBEKISTAN("uzbekistan"), VANUATU("vanuatu"), VENEZUELA("venezuela"),
    VIETNAM("vietnam"), YEMEN("yemen"), YIBUTI("yibuti"), ZAMBIA("zambia"), ZIMBABUE("zimbabue");
    
    private String nombre;
        
        private Nacionalidad(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
}
