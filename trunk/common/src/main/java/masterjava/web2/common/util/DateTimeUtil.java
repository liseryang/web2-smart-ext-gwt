package masterjava.web2.common.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: GKislin
 * Date: 24.06.2009
 * <p/>
 *
 * @link http://www.w3.org/TR/xmlschema-2/#dateTime
 * for example: 2000-03-04T23:00:00.00+03:00
 */
public class DateTimeUtil {
    private static final DatatypeFactory DATATYPE_FACTORY;

    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    
    private DateTimeUtil() {
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return DATATYPE_FACTORY.newXMLGregorianCalendar(gc).toXMLFormat();
    }

    public static Date parse(String w3cDateTime) throws ParseException {
        if (w3cDateTime == null) {
            return null;
        }
        return DATATYPE_FACTORY.newXMLGregorianCalendar(w3cDateTime).toGregorianCalendar().getTime();
    }
}
