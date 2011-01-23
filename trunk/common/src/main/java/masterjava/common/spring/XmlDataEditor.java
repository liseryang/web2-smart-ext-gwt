package masterjava.common.spring;

import masterjava.common.util.DateTimeUtil;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

/**
 * User: GKislin
 * Date: 22.12.2010
 */
public class XmlDataEditor extends PropertyEditorSupport {

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                setValue(DateTimeUtil.parse(text));
            }
            catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse XML date: " + ex.getMessage());
            }
        } else {
            setValue(new Date());
        }
    }

    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? DateTimeUtil.format(value) : "");
    }
}