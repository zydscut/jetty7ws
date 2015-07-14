package org.jetty7ws;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

public class TestVertXMain {
	public static int DEFAULT_CONNECTION_TIMEOUT = 20000;

	private static MySocket socketHandler;
	private static WebSocketClientFactory factory;
	private static WebSocketClient webSocketClient;
	
	public static void main(String[] argvs) throws Exception {
		factory = new WebSocketClientFactory();
        socketHandler = new MySocket();
        factory.start();
        webSocketClient = factory.newWebSocketClient();
        
		int port = 0;
		String ip = null;
		int cnt = 0;
		try {
			ip = argvs[0];
			port = Integer.parseInt(argvs[1]);
			cnt = Integer.parseInt(argvs[2]);

			System.out.println(ip + " " + port + " " + cnt);
		} catch (Exception e) {
			System.out
					.println("Usage: java -jar  TestPush.jar <IP> <Port> <Cnt>");
			System.out.println("   IP:Vertx IP Address");
			System.out.println("   Port:Vertx port");
			System.out.println("   Cnt:WebScoket connection count");
			System.exit(1);
		}

		try {
			URI uri = new URI("ws", null, "172.24.181.124", 19080,
					"/websocket/1/11F1AF738D1E8148E0AE0CB70E842F0D", null,
					null);

			webSocketClient.open(uri, socketHandler, DEFAULT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
