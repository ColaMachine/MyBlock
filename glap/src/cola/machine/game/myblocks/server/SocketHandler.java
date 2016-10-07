package cola.machine.game.myblocks.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by luying on 16/10/7.
 */
public  class SocketHandler implements SocketStatusListener {

    private Socket socket=null;

    private ReaderTask reader;

    private WriterTask writer;

    public SocketHandler(Socket socket) throws IOException {

        this.socket = socket;
        this.socket.setTcpNoDelay(true);
        reader = new ReaderTask(socket);
        writer = new WriterTask(socket);
        onSocketStatusChanged(socket, STATUS_OPEN, null);
    }


    /**
     * sendMessage:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     */
    public  void sendMessage(String msg) {
        writer.send(msg);
    }

    public void listen(boolean isListen)
    {
        reader.startListener(this);

    }

    public void shutDown() {

        if(!socket.isClosed() &&socket.isConnected())
        {
            try {
                writer.finish();
                reader.finish();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                onSocketStatusChanged(socket, STATUS_CLOSE, e);
            }finally{
                reader = null;
                writer = null;
                System.out.println("Socket连接已关闭！！");
            }
        }

    }

    @Override
    public void onSocketStatusChanged(Socket socket,int status, IOException e) {

        switch (status) {

            case SocketStatusListener.STATUS_CLOSE:
            case SocketStatusListener.STATUS_RESET:
            case SocketStatusListener.STATUS_PIP_BROKEN:
                shutDown();
                break;

            default:
                break;
        }
    }

}
