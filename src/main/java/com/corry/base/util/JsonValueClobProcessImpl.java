package com.corry.base.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class JsonValueClobProcessImpl implements JsonValueProcessor{

	public Object processArrayValue(Object clob, JsonConfig jsonConfig) {
		Reader inStreamDoc;
		try {
			inStreamDoc = ((Clob)clob).getCharacterStream();
			char[] tempDoc = new char[(int) ((Clob)clob).length()];
			inStreamDoc.read(tempDoc);
	        inStreamDoc.close();
	        return new String(tempDoc);
		} catch (SQLException e) {
			System.out.println("====JSON转换CLOB出错");
			e.printStackTrace();
		}   catch (IOException e) {
			System.out.println("====JSON转换CLOB出错");
			e.printStackTrace();
		}
		return "";
	}

	public Object processObjectValue(String key, Object clob, JsonConfig jsonConfig) {
		Reader inStreamDoc;
		try {
			inStreamDoc = ((Clob)clob).getCharacterStream();
			char[] tempDoc = new char[(int) ((Clob)clob).length()];
			inStreamDoc.read(tempDoc);
	        inStreamDoc.close();
	        return new String(tempDoc);
		} catch (SQLException e) {
			System.out.println("====JSON转换CLOB出错");
			e.printStackTrace();
		}   catch (IOException e) {
			System.out.println("====JSON转换CLOB出错");
			e.printStackTrace();
		}
		return "";
	}

}
