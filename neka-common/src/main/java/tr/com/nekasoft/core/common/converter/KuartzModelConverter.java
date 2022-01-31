package tr.com.nekasoft.core.common.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class KuartzModelConverter {


    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        OBJECT_MAPPER.registerModule(hibernate5Module);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static <T> T convert(Object from, TypeReference<T> toClass) {
        Object o = OBJECT_MAPPER.convertValue(from, toClass);
        return (T) o;

    }

    public static <T> T convert(Object from, Class<T> to) {
        return OBJECT_MAPPER.convertValue(from, to);
    }

    public static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }
}
