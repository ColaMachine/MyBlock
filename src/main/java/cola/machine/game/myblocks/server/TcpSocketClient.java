package cola.machine.game.myblocks.server;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TcpSocketClient {

    public static void main(String[] args) {

        SocketHandler clientHandler = null;
        try {
            Socket clientSocket = new Socket("localhost", 8090);
            clientSocket.setKeepAlive(true);//定时检查是否两端都正常

            clientSocket.setTcpNoDelay(true);//无缓冲 直接发送

            if(clientSocket.isConnected())
            {
                clientHandler = new SocketHandler(clientSocket);
                clientHandler.listen(true);

                while (true)
                {
                    Scanner sc = new Scanner(System.in);
                    String next = sc.nextLine()+"\r\n";
                    if(!clientSocket.isClosed())
                    {
                        clientHandler.sendMessage(next);
                    }else{
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            clientHandler.shutDown();
        }

    }


}