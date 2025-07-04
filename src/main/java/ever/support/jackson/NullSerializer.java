package ever.support.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class NullSerializer extends JsonSerializer<Object> {
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString("");
	}
}


