package dk.nversion.copybook;

import dk.nversion.copybook.exceptions.CopyBookException;
import dk.nversion.copybook.serializers.CopyBookParser;
import dk.nversion.copybook.serializers.CopyBookSerializerBase;
import dk.nversion.copybook.serializers.CopyBookSerializerConfig;

import java.lang.reflect.InvocationTargetException;

public class CopyBookSerializer {
    private CopyBookSerializerBase serializer;

    public CopyBookSerializer(Class type) throws CopyBookException {
        this(type, false);
    }

    public CopyBookSerializer(Class type, boolean debug) throws CopyBookException {
        CopyBookParser parser = new CopyBookParser(type, debug);
        try {
            serializer = (CopyBookSerializerBase)parser.getSerializerClass().getDeclaredConstructor(CopyBookSerializerConfig.class).newInstance(parser.getConfig());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new CopyBookException("Failed to load Serialization class("+ parser.getSerializerClass().getSimpleName() +")", e);
        }
    }

    public <T> byte[] serialize(T obj) throws CopyBookException {
        return serializer.serialize(obj);
    }

    public  <T> T deserialize(byte[] bytes, Class<T> type) throws CopyBookException, InstantiationException {
        return serializer.deserialize(bytes, type);
    }

}
