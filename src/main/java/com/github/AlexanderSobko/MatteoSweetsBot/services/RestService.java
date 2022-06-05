package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    private final static String PROF_PHOTOS_URL = "https://api.telegram.org/bot%s/getUserProfilePhotos?user_id=%s";
    private final static String PHOTO_PATH_URL = "https://api.telegram.org/bot%s/getFile?file_id=%s";
    private final static String PHOTO_URL = "https://api.telegram.org/file/bot%s/%s";

    @Value("${BOT_TOKEN}")
    private String botToken;

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public byte[] getUserPhoto(String userId) {

        byte[] image = null;


        try {
            String url = PROF_PHOTOS_URL.formatted(botToken, userId);
            JsonObject jsonObject = new JsonParser().parse(restTemplate.getForObject(url, String.class)).getAsJsonObject();

            String fileId = jsonObject
                    .getAsJsonObject("result")
                    .getAsJsonArray("photos").get(0)
                    .getAsJsonArray().get(0)
                    .getAsJsonObject().get("file_id").getAsString();

            url = PHOTO_PATH_URL.formatted(botToken, fileId);
            jsonObject = new JsonParser().parse(restTemplate.getForObject(url, String.class)).getAsJsonObject();

            String filePath = jsonObject.getAsJsonObject("result").get("file_path").getAsString();

            url = PHOTO_URL.formatted(botToken, filePath);

            image = restTemplate.getForObject(url, byte[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }
}
