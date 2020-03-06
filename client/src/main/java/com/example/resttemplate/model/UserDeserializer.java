package com.example.resttemplate.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Long id = (Long) node.get("id").numberValue();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        String login = node.get("login").asText();
        String password = node.get("password").asText();
        Long phoneNumber = (Long) node.get("phoneNumber").numberValue();
        String roleName = node.get("role").asText();

        return new User(id, firstName, lastName, login, password, phoneNumber, new Role(roleName));
    }
}
