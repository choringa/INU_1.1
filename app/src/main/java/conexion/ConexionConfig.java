package conexion;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by Choringa on 20/04/15.
 */
public class ConexionConfig extends ManejadorConexion{

    /**
     * Protocolo y ubicación del servicio a consultar
     */
    private final static String NAME_SPACE = "http://ws/";



    /**
     * Ubicación de despliegue y nombre del servicio (schema location)
     */
    private final static String NOMBRE_SERVICIO = "http://190.240.3.28/INUWS/INUWS?wsdl";

    /**
     * Línea que permite configurar la conexión http en Android
     */
    public final static String CONEXION_HTTP_3G = NOMBRE_SERVICIO + "";

    /**
     * Línea que permite configurar la conexión http en Android
     */
    public final static String CONEXION_HTTP_WIFI = NOMBRE_SERVICIO + "";






    //-----------------------------
    // CONSTANTES PARAMETROS
    //-----------------------------

    /**
     * Parametros web de los metodos del web service
     */
    public final static String PARAM_USERNAME = "username";

    public final static String PARAM_PASSWORD = "password";

    public final static String PARAM_ID_USER = "idUser";

    public final static String PARAM_USER_TYPE = "user_type";

    public final static String PARAM_ID_QUESTION = "id_question";

    public final static String PARAM_USER_ANSWER = "user_answer";

    //-----------------------------
    // CONSTANTES METODOS
    //-----------------------------

    /**
     * Constantes del nombre de los metodos del web service.
     */
    public static final String METHOD_LOGIN = "login";

    public static final String METHOD_DAR_PREGUNTA = "darPregunta";

    public static final String METHOD_MARCAR_RESPUESTA = "marcarRespuesta";


    //-------------------
    // CONSTRUCTOR
    //-------------------

    /**
     * Constructor de la conexion
     */
    public ConexionConfig() {
        super(NAME_SPACE, NOMBRE_SERVICIO, CONEXION_HTTP_3G);
    }


    //-------------------
    // METODOS
    //-------------------

    /**
     * Metodo para procesar las respuestas del web service
     * Procesa la respuesta de un servicio si la respuesta del server es en bytes[]
     */
    @Override
    public SoapObject procesarRespuesta(SoapObject body) {
        return body;
    }

    /**
     * Metodo para procesar las respuestas del web service
     * Procesa la respuesta de un servicio si la respuesta del server no es en bytes[] como strings por ejemplo
     */
    @Override
    public String procesarRespuesta2(SoapObject body) {
        return body.getProperty(0).toString();
    }

}
