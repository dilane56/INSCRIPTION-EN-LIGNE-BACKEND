package org.kfokam48.inscriptionenlignebackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/oauth2-debug")
@CrossOrigin("*")
public class OAuth2DebugController {
    
    @GetMapping("/callback")
    public ResponseEntity<Map<String, Object>> debugCallback(HttpServletRequest request) {
        Map<String, Object> debug = new HashMap<>();
        debug.put("requestURI", request.getRequestURI());
        debug.put("queryString", request.getQueryString());
        debug.put("method", request.getMethod());
        
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(name -> 
            headers.put(name, request.getHeader(name))
        );
        debug.put("headers", headers);
        
        Map<String, String[]> parameters = request.getParameterMap();
        debug.put("parameters", parameters);
        
        return ResponseEntity.ok(debug);
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("OAuth2 Debug Controller is working!");
    }
}