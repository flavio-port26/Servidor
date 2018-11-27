package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    double num1;
    double num2;
    double total;
    int operacao;
    public ServerSocket serverSocket;

    public void criaServidor(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
    }

    public Socket esperaConeccao() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    public void tratarConeccao(Socket socket) throws IOException {
        try {
            //protocolo aplicacao
            ObjectOutputStream resultado = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream dados = new ObjectInputStream(socket.getInputStream());
            operacao = dados.readInt();
            num1 = dados.readDouble();
            num2 = dados.readDouble();

            System.out.println("mensagem recebida");

            switch (operacao) {
                case 1:
                    total = (num1 + num2);
                    break;
                case 2:
                    total = (num1 - num2);
                    break;
                case 3:
                    total = (num1 * num2);
                    break;
                default:
                    total = (num1 / num2);
                    break;
            }

            resultado.writeDouble(total);
            resultado.flush();
            resultado.close();
            dados.close();
            fechaSoket(socket);
        } catch (IOException ex) {
            //tratar falhas
        } finally {

            fechaSoket(socket);
        }
    }

    public void fechaSoket(Socket s) throws IOException {

        s.close();
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            System.out.println("aguardando conecçao");
            servidor.criaServidor(12345);
            Socket socket = servidor.esperaConeccao();//protocolo
            System.out.println("Cliente conectado");
            servidor.tratarConeccao(socket);
            System.out.println("cliente finalizado");
        } catch (IOException ex) {
            //trata erro
        }
    }  
}
