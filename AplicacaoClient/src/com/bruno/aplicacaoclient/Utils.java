package com.bruno.aplicacaoclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class Utils {
	
	public static String getStringFromInputStream(InputStream stream) throws IOException
	{
	    int n = 0;
	    char[] buffer = new char[1024 * 4];
	    InputStreamReader reader = new InputStreamReader(stream, "UTF8");
	    StringWriter writer = new StringWriter();
	    while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
	    return writer.toString();
	}
	
	public static Double getLocalizacaoFormatada(String localizacao) 
	{
		return Double.valueOf(localizacao.replace("(", ""));
	}

}
