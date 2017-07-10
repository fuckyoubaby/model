package com.hylanda.util;

import java.util.UUID;

public class GetUUID {

	public static String getUUID()
	{
		String result = null;
		UUID uuid = UUID.randomUUID();
		result = uuid.toString();
		result=result.replace("-", "");
		return result;
	}
}
