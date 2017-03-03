package java.com.zcbspay.platform.cnaps.transfer.message.constant;

import java.io.File;

public class PathConstants {
	public final static String DIR = PathConstants.class.getClassLoader().getResource("input").getPath();
	public final static String baseConfig = DIR + File.separator + "BaseConfig.xml";
}
