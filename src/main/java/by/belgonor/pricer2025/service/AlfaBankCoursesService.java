package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.dto.CurrencyData;
import by.belgonor.pricer2025.entity.CurrencyNb;
import by.belgonor.pricer2025.entity.CurrencyRate;
import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.repository.CurrencyNbRepo;
import by.belgonor.pricer2025.repository.CurrencyRateRepo;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AlfaBankCoursesService {
    @Autowired
    private CurrencyNbService currencyNbService;

    @Autowired
    private CurrencyNbRepo currencyNbRepo;

    @Autowired
    private CurrencyRateRepo currencyRateRepo;

    @Autowired
    private XlsxParse xlsxParse;


    public void checkNbCourses(List<Seller> sellers) throws IOException {
//        если нет курсов валют на сегодняшнюю дату, то получаем их в Альфабанке и записываем в БД
        if (!isActualCoursesExist()) {setActualCourses();}
    }

    public boolean isActualCoursesExist() {
        //          запрашиваем курсы на сегодня из нашей БД, если массив пуст, то будем записывать актуальные курсы
        boolean isExist = true;
        LocalDate coorseByDate = LocalDate.now();
        List<CurrencyRate> currencyRates = currencyRateRepo.findByDate(coorseByDate);
        System.out.println("\bcoorseByDate = " + coorseByDate);
        System.out.println("\bcurrencyRates = " + currencyRates);
        if (currencyRates.isEmpty()) {
            isExist = false;
        }
        return isExist;
    }

    public void setActualCourses() throws IOException {

        // Получаем данные о списке валют напрямую из репозитория
        List<CurrencyNb> currencyList = currencyNbRepo.findAll();
        LocalDate coorseByDate = LocalDate.now();
        String bankAnswer = AlfaBankCoursesService.getAlfaNB();
        System.out.println("bankAnswer = " + bankAnswer);
//            // декодируем ответ из банка
        List<CurrencyData> currencyFromNb = decodeBankAnswer(bankAnswer);
        for (CurrencyNb currencyNbCode : currencyList) {
            CurrencyRate toAdd = new CurrencyRate();
            System.out.println("currencyNbCode = " + currencyNbCode);
            for (CurrencyData alfaAnwer : currencyFromNb) {
                if (currencyNbCode.getCurrencyCode() == alfaAnwer.getCode()) {
                    toAdd.setDate(coorseByDate);
                    toAdd.setCurrencyRate((alfaAnwer.getRate()).divide(BigDecimal.valueOf(alfaAnwer.getQuantity())));
                    toAdd.setCurrency(currencyNbCode);
                    currencyRateRepo.save(toAdd);
                    System.out.println("Записано значение = " + toAdd);
                }
            }
        }

    }

    public List<CurrencyData> decodeBankAnswer(String json) {
        List<CurrencyData> currencyDataList = new ArrayList<>();
        JSONObject bankAnswer = new JSONObject(json);
        JSONArray ratesArray = bankAnswer.getJSONArray("rates");

        for (int i = 0; i < ratesArray.length(); i++) {
            JSONObject rateObject = ratesArray.getJSONObject(i);
            int code = rateObject.getInt("code");
            int quantity = rateObject.getInt("quantity");
            BigDecimal rate = rateObject.getBigDecimal("rate");

            CurrencyData currencyData = new CurrencyData(code, quantity, rate);
            currencyDataList.add(currencyData);
        }
        return currencyDataList;
    }

    //          ##############  получение курсов НБ из АльфаБанка   ################
    public static String getAlfaNB() throws IOException {

        OkHttpClient client = new OkHttpClient();

        LocalDate currentDate = LocalDate.now();
        System.out.println("currentDate = " + currentDate);
        String currentDateAlfaNB = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        System.out.println("currentDateAlfaNB = " + currentDateAlfaNB);

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://developerhub.alfabank.by:8273/partner/1.0.1/public/nationalRates?").newBuilder();

        urlBuilder.addQueryParameter("date", currentDateAlfaNB); // Замените на нужную дату
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        assert response.body() != null;
        String bankAnswer = response.body().string();
        return bankAnswer;
    }

}
