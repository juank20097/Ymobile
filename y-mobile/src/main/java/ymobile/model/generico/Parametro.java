package ymobile.model.generico;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parametro {
	public static String COD_ITEM = "I";
	public static String COD_GENERAL = "G";
	public static String ID_CATEGORIA = "c";
	public static String ID_INF_GENERAL = "g";
	public static String ID_INF_ITEM = "i";
	public static String ID_NUMERACION_SERVICIO = "cat";
	public static String ID_NUMERACION_INF_GENERAL = "ing";
	public static String ID_NUMERACION_INF_ITEM = "inf";
	public static String ID_CATALOGO_ICONOS = "ico";
	public static String ID_ICON_ACTION = "ui-icon-action";
	public static String ID_CATALOGO_ESTADOS = "est";
	public static String ID_ESTADO_ACTIVO = "A";
	public static String DEFAULT_LINK = "-";
	public static String DEFAULT_DATE = "1900-01-01";
	public static double DEFAULT_COORD = 0.0;
	public static double DEFAULT_COORD_LAT = 0.4029181172908282;
	public static double DEFAULT_COORD_LNG = -78.1750899553299;

	public static String nombreImagen() {
		return new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
	}

	public static boolean isUrlValid(String s) {
		String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";
		try {
			Pattern patt = Pattern.compile(regex);
			Matcher matcher = patt.matcher(s);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}
	}
}
