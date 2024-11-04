import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class TalkServer implements Runnable{
    ServerSocket serverSocket;

    Socket clientSocket;
    private static final int PORT = 5000;
    List<TalkServerThread> globalList = null;


    private TalkServer(){
        run();
    }




    @Override
    public void run() {
        try{

            serverSocket = new ServerSocket(PORT); //서버소켓 포트번호 열기
            globalList = new Vector<>(); //클라이언트를 담을 제네릭 벡터
            while(true){ //서버를 계속 가동한다.
                clientSocket = serverSocket.accept(); //클라이언트가 접속한 것을 인지하면 서버는 가로챈다.
                System.out.println("클라이언트가 접속했습니다.");

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
