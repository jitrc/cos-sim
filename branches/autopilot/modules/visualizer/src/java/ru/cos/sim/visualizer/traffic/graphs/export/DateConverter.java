package ru.cos.sim.visualizer.traffic.graphs.export;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateConverter implements Converter {
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        Date date = (Date)value;
        writer.setValue(format.format(date));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Date date = null;
        try {
            date = format.parse(reader.getValue());
        } catch (ParseException e) {
            throw new ConversionException(e.getMessage());
        }
        return date;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return Date.class.isAssignableFrom(aClass);
    }
}
