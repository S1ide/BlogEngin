package com.naumen.blogeng.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naumen.blogeng.dto.DtoJoke;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JokeService {
    @Value("${jokeApi.pid}")
    private String pid;
    @Value("${jokeApi.key}")
    private String key;

    public JokeService() {
    }

    public DtoJoke getJoke() {
        String apiUrl = buildUrl();
        return fetchJoke(apiUrl);
    }

    private DtoJoke fetchJoke(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String jsonStr = response.getBody();
        Map<String, Object> mapping = null;
        try {
            mapping = new ObjectMapper().readValue(jsonStr, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String jokeText = (String) ((LinkedHashMap) mapping.get("item")).get("text");
        return new DtoJoke(jokeText);
    }

    private String buildUrl() {
        String query = buildQuery();
        return "http://anecdotica.ru/api?" + query + "&hash=" + MD5(query + key);
    }

    private String buildQuery() {
        try {
            return "pid=" + URLEncoder.encode(pid, "UTF-8") + "&method=getRandItem&uts=" +
                    (new Timestamp(System.currentTimeMillis())).getTime() / 1000L;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    static String MD5(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = md.digest(str.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hash = number.toString(16);
        hash = String.format("%32s", hash).replace(" ", "0");
        return hash;
    }
}
