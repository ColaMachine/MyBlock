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
            serverSocket = new ServerSocket(8090, 5);//创建tcp 监听socket
            while(true)
            {
                Socket clientSocket = serverSocket.accept();//获取client过来的socket
                if(clientSocket.isConnected())//如果客户端socket 连接了的话
                {
                    SocketHandler serverHandler = new SocketHandler(clientSocket);//用这个socket 生成sockethandler 类似worker
                    serverHandlers.add(serverHandler);//放入队列
                    serverHandler.listen(true);//worker 里含有一个task 线程 不停的读取socket 数据

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

