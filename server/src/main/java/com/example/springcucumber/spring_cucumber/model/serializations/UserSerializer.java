package com.example.springcucumber.spring_cucumber.model.serializations;

import com.example.springcucumber.spring_cucumber.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> u) {
        super(u);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (user.getId() != null) {
            jsonGenerator.writeNumberField("id", user.getId());
        }
        jsonGenerator.writeStringField("firstName", user.getFirstName());
        jsonGenerator.writeStringField("lastName", user.getLastName());
        jsonGenerator.writeStringField("login", user.getLogin());
        jsonGenerator.writeStringField("password", user.getPassword());
        jsonGenerator.writeNumberField("phoneNumber", user.getPhoneNumber());
        jsonGenerator.writeStringField("role", user.getRole().getName());
        jsonGenerator.writeEndObject();
    }
}
