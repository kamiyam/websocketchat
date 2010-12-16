package k5m.websocketchat.socket;

import java.io.IOException;
import java.util.Set;

import k5m.websocketchat.exchange.SocketDataExchange;

import org.eclipse.jetty.websocket.WebSocket;

public class ChatWebSocket implements WebSocket
{
	/**
	 * コネクション
	 */
	private Outbound outbound;

	/**
	 * WebSocket接続時にコール
	 * 
	 * @param Outbound
	 *            org.eclipse.jetty.websocket.WebSocketConnection
	 *            「WebSocketConnection」はWebSocketの接続先を表すクラス
	 */
	@Override
	public void onConnect( Outbound outbound )
	{
		this.outbound = outbound;
		ChatWebSocketMaster.addMembers( this );
	}

	/**
	 * WebSocket切断時にコール
	 */
	@Override
	public void onDisconnect()
	{
		ChatWebSocketMaster.removeMembers( this );
	}

	/**
	 * 接続先からメッセージ受取時にコール
	 * 
	 * @param frame
	 */
	@Override
	public void onMessage( byte frame, String data )
	{
		SocketDataExchange exchange = new SocketDataExchange();
		String result = exchange.exchange( data );
		
		Set<ChatWebSocket> members = ChatWebSocketMaster.getMemvers();

		// すべての接続先に送る
		for ( ChatWebSocket member : members )
		{
			try
			{
				member.outbound.sendMessage( frame, result );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 接続先からメッセージ受取時にコール
	 * 
	 * @param frame
	 *            データ送信の詳細
	 */
	@Override
	public void onMessage( byte frame, byte[] data, int offset, int length )
	{
		Set<ChatWebSocket> members = ChatWebSocketMaster.getMemvers();

		// すべての接続先に送る
		for ( ChatWebSocket member : members )
		{
			try
			{
				member.outbound.sendMessage( frame, data, offset, length );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void onFragment( boolean more, byte opcode, byte[] data, int offset, int length )
	{
		Set<ChatWebSocket> members = ChatWebSocketMaster.getMemvers();

		// すべての接続先に送る
		for ( ChatWebSocket member : members )
		{
			try
			{
				member.outbound.sendFragment( more, opcode, data, offset, length );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

}
