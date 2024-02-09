package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonHelper {

    private static ObjectMapper objectMapper;

    public static String readFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file " + filePath);
        }
    }

    public static <T> String readObject(T object) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert object to String");
        }
    }

    public static <T, V> V createDTO(T json, Class<V> dtoClass) {
        objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(String.valueOf(json), dtoClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create DTO from json");
        }
    }
}