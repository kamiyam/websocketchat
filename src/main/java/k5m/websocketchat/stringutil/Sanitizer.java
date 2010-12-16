package k5m.websocketchat.stringutil;

public class Sanitizer
{
	private class Secure
	{
		static private final String Ampersand = "&amp;";
		static private final String LessThan = "&lt;";
		static private final String GraterThan = "&gt;";
		static private final String Quote = "&quot;";
		static private final String SingleQuotes = "&#39;";
	}

	private class Plan
	{
		static private final String Ampersand = "&";
		static private final String LessThan = "<";
		static private final String GraterThan = ">";
		static private final String Quote = "\"";
		static private final String SingleQuotes = "'";
	}
	static public String htmlSanitize( String string )
	{
		if ( string == null ) return "";
		string = string.replaceAll( Plan.Ampersand, 	Secure.Ampersand 	);
		string = string.replaceAll( Plan.LessThan, 		Secure.LessThan 	);
		string = string.replaceAll( Plan.GraterThan, 	Secure.GraterThan 	);
		string = string.replaceAll( Plan.Quote, 		Secure.Quote 		);
		string = string.replaceAll( Plan.SingleQuotes, 	Secure.SingleQuotes );
		return string;
	}

	static public String htmlUnsanitize( String string )
	{
		if ( string == null ) return "";
		string = string.replaceAll( Secure.Ampersand, 		Plan.Ampersand 		);
		string = string.replaceAll( Secure.LessThan, 		Plan.LessThan 		);
		string = string.replaceAll( Secure.GraterThan, 		Plan.GraterThan 	);
		string = string.replaceAll( Secure.Quote, 			Plan.Quote 			);
		string = string.replaceAll( Secure.SingleQuotes,	Plan.SingleQuotes 	);
		return string;
	}
}

