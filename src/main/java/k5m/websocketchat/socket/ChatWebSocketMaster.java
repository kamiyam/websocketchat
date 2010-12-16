package k5m.websocketchat.socket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatWebSocketMaster
{
	
	/**
	 * 接続先すべてを保持
	 * _membersはメッセージ送信のたびに参照されるため，「Set」クラスではなく，
	 * 参照系に有利な「CopyOnWriteArraySet」クラスを使用
	 */
	static private Set<ChatWebSocket> _members = new CopyOnWriteArraySet<ChatWebSocket>();

	/**
	 * 接続数
	 * @return
	 */
	static public int onlineMemberNum()
	{
		return _members.size();	
	}
	
	static public void addMembers( ChatWebSocket menber )
	{
		_members.add( menber );
	}
	
	static public void removeMembers( ChatWebSocket menber )
	{
		_members.remove( menber );
	}
	
	static public Set<ChatWebSocket> getMemvers()
	{
		return _members;
	}
	
}
