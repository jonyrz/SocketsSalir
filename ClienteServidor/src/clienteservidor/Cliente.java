package clienteservidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Cliente {
    static final String HOST ="localhost";
    static final int PUERTO=5000;
    
    public static void main(String[] args){
	String mensaje, limpiar=null;
	boolean alive = true;
        
        InputStreamReader entrada = new InputStreamReader(System.in);
        //Creamos una instancia BufferedReader en la que guardamos los datos introducidos
        BufferedReader teclado = new BufferedReader(entrada);
        try{
            Socket socket_c = new Socket(HOST, PUERTO); //Declaramos un objeto socket para realizar la comunicacion
            System.out.println("Iniciando en el puerto: "+PUERTO);
            System.out.println("Conexión establecida con el servidor");
            
            while(alive){
                mensaje = teclado.readLine();
                OutputStream out = socket_c.getOutputStream();
                DataOutputStream infoSalida = new DataOutputStream(out);
                if("SALIR".equals(mensaje) || "salir".equals(mensaje) || "Salir".equals(mensaje)){
                    alive=false;
                    mensaje="salir";
                }
                else{
                    infoSalida.writeUTF(mensaje);   // enviamos al Servidor el parametro recibido por consola
                    InputStream in = socket_c.getInputStream();
                    DataInputStream infoEntrada = new DataInputStream(in);
                    System.out.println("El servidor dice : "+infoEntrada.readUTF());
                    //infoSalida.writeUTF(mensaje+"\n");
                    //infoSalida.flush();
                }   
            }
            socket_c.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }   
}