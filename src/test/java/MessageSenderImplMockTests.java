import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplMockTests {

    @Test
    void messageSenderRuTest() {
//        Location location = Mockito.mock(Location.class);
//Country country = Mockito.mock(Country.RUSSIA);
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("172").getCountry()).thenReturn(Country.RUSSIA);

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(geoService.byIp("172").getCountry())).thenReturn("Добро пожаловать");


        MessageSenderImpl messageSenderImpl = new MessageSenderImpl(geoService, localizationService);


        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        String mesage = messageSenderImpl.send(headers);
        String expected = "Отправлено сообщение: Добро пожаловать";
        Assertions.assertEquals(expected, mesage);
    }
}
