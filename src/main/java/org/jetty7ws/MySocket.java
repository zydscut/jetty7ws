package org.jetty7ws;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

public class MySocket implements WebSocket, WebSocket.OnTextMessage, WebSocket.OnBinaryMessage {
	protected boolean connected = false;
	protected CountDownLatch openLatch = new CountDownLatch(1);

	public MySocket() {
	}

	@Override
	public void onMessage(String msg) {
		//for test only
		System.out.println(msg);
//		Log.i("on message", msg);
	}

	@Override
	public void onMessage(byte[] buf, int offset, int length) {
		MessagePack msgp = new MessagePack();
		try {
			Value val = msgp.read(buf);
			//messageReceived(val);
			System.out.println(val);
//			Log.i("on message bin", String.valueOf(val));
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void onOpen(Connection connection) {
		connected = true;
		openLatch.countDown();
	}

	public void onClose(int statusCode, String reason) {
		System.out.println(Calendar.getInstance() + " onClose");
//		Log.i("on close", dtFormat.format(new Date(System.currentTimeMillis())) + " onClose");
		this.connected = false;
	}

	public boolean awaitOpen(int duration, TimeUnit unit)
			throws InterruptedException {
		boolean res = openLatch.await(duration, unit);
		if (this.connected) {
//			Log.i("await open", "Connection established");
			System.out.println("Connection established");
		} else {
//			Log.e("await open", "Cannot connect to the remote server ");
			System.out.println("Cannot connect to the remote server ");
		}
		return res;
	}
}
