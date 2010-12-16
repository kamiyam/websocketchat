package k5m.websocketchat.exchange;

import java.text.SimpleDateFormat;
import java.util.Date;

import k5m.websocketchat.stringutil.Sanitizer;


import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;


public class SocketDataExchange
{

	public String exchange( String data )
	{
		String val = Sanitizer.htmlSanitize( data );
        Date date = new Date(); 
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
        String dateStr = sdf1.format( date );
        
		SocketDataInfomation info = null;
		try
		{
			info = JSON.decode( val, SocketDataInfomation.class );
		}
		catch ( JSONException e )
		{
			e.printStackTrace();
			info = new SocketDataInfomation();
			info.command = "error";
			info.date = dateStr;
			info.errorcode = 99;
			info.errormsg = e.getMessage();
		}

		info.date = dateStr;
		
		return JSON.encode( info );
	}

}
