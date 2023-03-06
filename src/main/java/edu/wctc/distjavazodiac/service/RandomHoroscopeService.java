package edu.wctc.distjavazodiac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wctc.distjavazodiac.entity.Birthday;
import edu.wctc.distjavazodiac.entity.Fortune;
import edu.wctc.distjavazodiac.entity.Horoscope;
import edu.wctc.distjavazodiac.repo.FortuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Random horoscopes from https://cafeastrology.com/dailyhoroscopesall-tomorrow.html
 */
@Service
public class RandomHoroscopeService implements HoroscopeService {
    
    private ZodiacService zodiacService;
    private FortuneRepository fortuneRepository;

    @Autowired
    public RandomHoroscopeService(ZodiacService zodiacService, FortuneRepository fortuneRepository) {
        this.zodiacService = zodiacService;
        this.fortuneRepository = fortuneRepository;
    }

    @Override
    public Horoscope getHoroscope(Birthday birthday) {
        String sign;
        if (birthday.getZodiacType().toLowerCase().startsWith("w")) {
            sign = zodiacService.getWesternZodiacSign(birthday);
        } else {
            sign = zodiacService.getEasternZodiacSign(birthday);
        }

        Horoscope hscope = new Horoscope();
        hscope.setSign(sign);

        List<Fortune> list = new ArrayList<>();
        fortuneRepository.findAll().forEach(list::add);

        int randomIndex = (int) (Math.random() * list.size());
        hscope.setHoroscope(list.get(randomIndex).getText());
        return hscope;
    }


}
