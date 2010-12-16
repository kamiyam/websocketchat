package k5m.websocketchat;

import javax.servlet.http.HttpServletRequest;

import k5m.websocketchat.socket.ChatWebSocket;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

@SuppressWarnings( "serial" )
public class WebSocketChatServlet extends WebSocketServlet
{
	@Override
	protected WebSocket doWebSocketConnect( HttpServletRequest request, String protocol )
	{
		return new ChatWebSocket();
	}
}