import com.fasterxml.jackson.core.JsonProcessingException;
import entities.Translations;
import gateway.YandexTranslationGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class YandexTranslateTest {
    private static final String textToTranslate = "Hello World!";
    private static final String targetLanguageCodeValue = "ru";
    private static final String translatedText = "Привет, мир!";
    private static long timeExecution;
    YandexTranslationGateway yandexTranslateGateway = new YandexTranslationGateway();

    @Test
    @DisplayName("Перевод текста")
    public void checkTextTranslated() throws JsonProcessingException {
        Translations translations = yandexTranslateGateway.
                getYandexTranslateResponse(textToTranslate, targetLanguageCodeValue).as(Translations.class);
        Assertions.assertEquals(translations.getTranslations().get(0).getText(), translatedText);
    }

    @Test
    @DisplayName("Проверка времени исполнения запроса")
    public void checkResponseTime() throws JsonProcessingException {
        timeExecution = yandexTranslateGateway.
                getYandexTranslateResponse(textToTranslate, targetLanguageCodeValue).then().extract().time();
        Assertions.assertTrue(timeExecution<=5000);
    }
}
