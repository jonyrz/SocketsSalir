
package clienteservidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
        private static int PUERTO=5000;
    
    public static void main(String[] args){
        String mensaje = ""; //Declaramos una varibale de tipo String
	boolean alive=true;
        String limpiar = null;
        
        InputStreamReader entrada = new InputStreamReader(System.in);
        //Creamos una instancia BufferedReader en la que guardamos los datos introducidos
        BufferedReader teclado = new BufferedReader(entrada);
        try{
             //Creamos un socket_c al que le pasamos el contenido del objeto socket despues y se hace la conexion con el cliente
            ServerSocket socket_s = new ServerSocket(PUERTO);			
            System.out.println("Iniciando en el puerto: "+PUERTO);
            Socket socket_c = socket_s.accept(); //ejecuta la funcion accept que nos permitira aceptar conexiones de clientes
            System.out.println("Conexion establecida con el cliente");
            while(alive){
                mensaje = teclado.readLine();
                if("SALIR".equals(mensaje) || "salir".equals(mensaje) || "Salir".equals(mensaje)){
                    alive=false;
                    mensaje="salir";
                }
                else{
                    OutputStream out = socket_c.getOutputStream();
                    DataOutputStream infoSalida = new DataOutputStream(out);
                    infoSalida.writeUTF(mensaje);   // enviamos al Servidor el parametro recibido por consola
                    InputStream in = socket_c.getInputStream();
                    DataInputStream infoEntrada = new DataInputStream(in);
                    System.out.println("El cliente dice: "+infoEntrada.readUTF());
                    if(entrada==null){
                        infoSalida.writeUTF("");
                        infoSalida.flush();
                    }
                    //infoSalida.writeUTF(mensaje+"\n");
                    //infoSalida.flush();
                }
            }
             socket_c.close(); //Cierra la conexion            
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}