package carrentalsystem.mapper;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

@Data
@Builder
public class ApiResponse {
    public static ResponseEntity<Object> map(HttpStatus status, Object data, String message) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("data", data);
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }
}
