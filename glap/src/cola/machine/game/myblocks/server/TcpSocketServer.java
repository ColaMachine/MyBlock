package cola.machine.game.myblocks.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by luying on 16/10/8.
 */
public class TcpSocketServer {
    public static void main(String[] args) {

        List<SocketHandler> serverHandlers = new CopyOnWriteArrayList<SocketHandler>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8090, 5);
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                if(clientSocket.isConnected())
                {
                    SocketHandler serverHandler = new SocketHandler(clientSocket);
                    serverHandlers.add(serverHandler);
                    serverHandler.listen(true);

                    serverHandler.sendMessage("Host:"+serverSocket.getInetAddress().getHostAddress()+"\r\n");

				/*while (true)
				{
					Scanner sc = new Scanner(System.in);
					String next = sc.nextLine()+"\r\n";
					for (SocketHandler scItem : serverHandlers) {
						scItem.sendMessage(next);
					}
				}*/
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                for (SocketHandler serverHandler : serverHandlers)
                {
                    serverHandler.shutDown();
                }
                serverHandlers.clear();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}

