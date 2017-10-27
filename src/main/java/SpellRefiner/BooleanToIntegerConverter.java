package SpellRefiner;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToIntegerConverter 
        implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean b) {
        if (b == null) {
            return null;
        }
        if (b.booleanValue()) {
            return 1;
        }
        return 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer i) {
        if (i == null) {
            return null;
        }
        if (i == 1) {
            return Boolean.TRUE;
        }
        
        return Boolean.FALSE;
    }

}


