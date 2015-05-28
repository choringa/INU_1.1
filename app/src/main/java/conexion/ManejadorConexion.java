package conexion;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Choringa on 20/04/15.
 */
public abstract class ManejadorConexion {
    /**
     * name space del servicio a consumir
     */
    private String nameSapce;

    /**
     * Ubicaci�n del servicio a consumir
     */
    private String nombreServicio;

    /**
     * Comando para realizar la conexi�n http desde BalckBerry
     */
    private String conexionHTTP;

    /**
     * Crea un nuevo conector con un servicio web
     * @param nNameSpace namespace del servicio web
     * @param nNombreServicio nombre del servicio web
     * @param nConexion comando de conexi�n http
     */
    public ManejadorConexion(String nNameSpace, String nNombreServicio, String nConexion){
        nameSapce = nNameSpace;
        nombreServicio = nNombreServicio;
        conexionHTTP = nConexion;
    }

    /**
     * Consume un servicio y devuelve la informaci�n entregada por este para objetos deifinidos por java es decir no byte[]
     * @param servicio el servicio que se desea consumir
     * @param nombreParams el nombre de los par�metros solicitados por el servicio
     * @param params los par�metros solicitados por el servicio
     * @return la respuesta entregada por el servicio
     * @throws Exception en caso de que no se pueda realizar la conexi�n
     */
    public Object consumirServicio(String servicio, String[] nombreParams, String[] params) throws Exception{



        Object resultado;
        SoapObject soap = new SoapObject(nameSapce, servicio);
        soap.newInstance();
        for( int i = 0; i < nombreParams.length; i++){
            soap.addProperty(nombreParams[i], params[i]);
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);
        //envelope.implicitTypes = true;
        //envelope.encodingStyle = "utf-8";
        //envelope.enc = SoapSerializationEnvelope.ENC2001;
        //envelope.xsd = SoapEnvelope.XSD;
        //envelope.xsi = SoapEnvelope.XSI;
        System.out.println("conexionhttp: " + conexionHTTP);
        //HttpsTransportSE http = new HttpsTransportSE(	"10.104.1.124/ProjectoX/NewWebService?xsd=1",8080,"",1600000 );
        HttpTransportSE http = new HttpTransportSE(	conexionHTTP, 15000);
        //AndroidHttpTransportSE http = AndroidHttpTransportSE(conexionHTTP);
        System.out.println("consumirServicio1");
        http.debug = true;
        try {
            System.out.println("consumirServicio2: " + nombreServicio);
            http.call(nombreServicio, envelope);
            SoapObject body = (SoapObject)envelope.bodyIn;
            System.out.println("consumirServicio3");
//            SoapPrimitive resultsRequestSOAP = (SoapPrimitive)envelope.getResponse();
            System.out.println("consumirServicio4");
            resultado = procesarRespuesta2(body);
            System.out.println("consumirServicio5->RESPUESTA: "+resultado);
        } catch (IOException e) {
            throw new Exception("Ocurrió el siguiente error de conexión: " + e.getMessage());
        } catch (XmlPullParserException e) {
            throw new Exception("Ocurrió el siguiente error al hacer parser de la respuesta: " + e.getMessage());
        }
        catch(Exception e){
            throw new Exception("Ocurrió el siguiente error: " + e.getMessage());
        }
        return resultado;
    }


    /**
     * Consume un servicio y devuelve la informaci�n entregada por este para el caso de manejar la respuesta si es un objeto x es decir en byte[]
     * @param servicio el servicio que se desea consumir
     * @param nombreParams el nombre de los par�metros solicitados por el servicio
     * @param params los par�metros solicitados por el servicio
     * @return la respuesta entregada por el servicio
     * @throws Exception en caso de que no se pueda realizar la conexi�n
     */
    public SoapObject consumirServicio2(String servicio, String[] nombreParams, String[] params) throws Exception{



        SoapObject resultado;
        SoapObject soap = new SoapObject(nameSapce, servicio);
        soap.newInstance();
        for( int i = 0; i < nombreParams.length; i++){
            soap.addProperty(nombreParams[i], params[i]);
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);
        //envelope.implicitTypes = true;
        //envelope.encodingStyle = "utf-8";
        //envelope.enc = SoapSerializationEnvelope.ENC2001;
        //envelope.xsd = SoapEnvelope.XSD;
        //envelope.xsi = SoapEnvelope.XSI;
        System.out.println("conexionhttp: " + conexionHTTP);
        //HttpsTransportSE http = new HttpsTransportSE(	"10.104.1.124/ProjectoX/NewWebService?xsd=1",8080,"",1600000 );
        HttpTransportSE http = new HttpTransportSE(	conexionHTTP, 15000);
        //AndroidHttpTransportSE http = AndroidHttpTransportSE(conexionHTTP);
        System.out.println("consumirServicio1");
        http.debug = true;
        try {
            System.out.println("consumirServicio2: " + nombreServicio);
            http.call(nombreServicio, envelope);
            SoapObject body = (SoapObject)envelope.bodyIn;
            System.out.println("consumirServicio3");
            //Aca le pasa un soap primitive para analizar la respuesta por que va a ser uno de tipo objeto, es decir un byte[]
//            SoapPrimitive resultsRequestSOAP = (SoapPrimitive)envelope.getResponse();
            System.out.println("consumirServicio4");
            resultado = procesarRespuesta(body);
            System.out.println("consumirServicio5->RESPUESTA: "+resultado);
        } catch (IOException e) {
            throw new Exception("Ocurrió el siguiente error de conexión: " + e.getMessage());
        } catch (XmlPullParserException e) {
            throw new Exception("Ocurrió el siguiente error al hacer parser de la respuesta: " + e.getMessage());
        }
        catch(Exception e){
            throw new Exception("Ocurrió el siguiente error: " + e.getMessage());
        }
        return resultado;
    }

    /**
     * Procesa la respuesta de un servicio si la respuesta del server es en bytes[]
     * @param body la respuesta entregada por el servicio
     * @return la respuesta del servicio en terminos de elementos de la aplicación
     */
    public abstract SoapObject procesarRespuesta(SoapObject body);


    /**
     * Procesa la respuesta de un servicio si la respuesta del server es en otra cosa que no sea bytes[]
     * @param body la respuesta entregada por el servicio
     * @return la respuesta del servicio en terminos de elementos de la aplicación
     */
    public abstract  Object procesarRespuesta2(SoapObject body);
}
