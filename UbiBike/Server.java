


import java.net.*;
import java.io.*;

public class Server{


    public static void main(String args[]) throws IOException {

        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Porta 12345 aberta!");

        Socket cliente = servidor.accept();
        System.out.println("Nova conexão com o cliente "+cliente.getInetAddress().getHostAddress());
        // imprime o ip do cliente



    }


}