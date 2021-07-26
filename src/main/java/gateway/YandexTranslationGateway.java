package gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class YandexTranslationGateway {
    static Dotenv dotenv = Dotenv.configure().load();
    private static final String apiKey = dotenv.get("apiKey");
    private static final String folderIdKey = dotenv.get("folderIdKey");

    public Response getYandexTranslateResponse (String translateText, String languageOutput) throws JsonProcessingException {
        RestAssured.baseURI = "https://translate.api.cloud.yandex.net/translate/v2/translate";

        Response response = given().contentType(JSON).
                header("Authorization", "Api-Key " + apiKey).
                body(makeJson(translateText,languageOutput)).post().then().extract().response();

        return response;
    }

    public String makeJson (String translateText, String languageOutput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonBody = mapper.createObjectNode();
        try {
            jsonBody.put("folderId", folderIdKey);
            jsonBody.put("texts", translateText);
            jsonBody.put("targetLanguageCode", languageOutput);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonBody);
    }

}
