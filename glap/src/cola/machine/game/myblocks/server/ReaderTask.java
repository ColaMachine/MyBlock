package cola.machine.game.myblocks.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
/**
 * Created by luying on 16/10/7.
 */
public class ReaderTask extends Thread{

    private SocketStatusListener socketStatusListener;

    private BufferedReader bufferedReader;

    private Socket socket;

    private boolean listening;

    public ReaderTask(Socket socket) throws IOException
    {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
    }

    /**
     * finish:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * @throws IOException
     *
     */
    public void finish() throws IOException
    {
        listening = false;
        interrupt();
        if(bufferedReader!=null && socket!=null)
        {
            if(socket.isInputShutdown())
            {
                socket.shutdownInput();
            }
            bufferedReader.close();
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public synchronized  void run()
    {
        while (listening)
        {
            String readStr = null;
            try {
                while((readStr=bufferedReader.readLine())!=null)
                {
                    System.err.println("[Server]:"+readStr);
                }
            } catch (IOException e) {
                listening = false;
                if(socketStatusListener!=null)
                {
                    int status = parseSocketStatus(e);
                    socketStatusListener.onSocketStatusChanged(socket, status, e);
                }
                e.printStackTrace();
                return;//终止线程继续运行,这里也可以使用continue
            }

        }
    }

    private int parseSocketStatus(IOException e)
    {
        if(SocketException.class.isInstance(e))
        {
            String msg = e.getLocalizedMessage().trim();
            if("Connection reset".equalsIgnoreCase(msg))
            {
                return SocketStatusListener.STATUS_RESET;
            }
            else if("Socket is closed".equalsIgnoreCase(msg))
            {
                return SocketStatusListener.STATUS_CLOSE;
            }
            else if("Broken pipe".equalsIgnoreCase(msg))
            {
                return SocketStatusListener.STATUS_PIP_BROKEN;
            }

        }
        return SocketStatusListener.STATUS_UNKOWN;
    }

    /**
     * listen:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     *
     */
    public void startListener(SocketStatusListener ssl) {
        listening = true;
        this.socketStatusListener = ssl;
        start();
    }

}