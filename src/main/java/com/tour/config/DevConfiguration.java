package com.tour.config;

import com.tour.model.*;
import com.tour.model.enums.UserRole;
import com.tour.services.GroupService;
import com.tour.services.GuideAccountService;
import com.tour.services.TourService;
import com.tour.services.TouristAccountService;
import com.tour.services.impl.TouristAccountServiceImpl;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Configuration
@Profile("DEV")
public class DevConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static BaseUser.Role userRole = BaseUser.Role.ROLE_USER;
    private static BaseUser.Role guideRole =BaseUser.Role.ROLE_STAFF;

    private static final Integer[] MOBILE_OPERATOR_CODES = new Integer[]{
            33,
            29,
            31,

    };

    private static final Integer[] PRICE = new Integer[]{
            390,
            500,
            630,
            660,
            670,
            680,
            930,
            950,
            960,
            970,
            980,
            990
    };

    private static final String[] CITIES = new String[]{
            "Гродно",
            "Орша",
            "Лида",
            "Минск",
            "Обзор",
            "Мадрид",
            "Москва",
            "Санкт-Петербург",
            "Краков",
            "Вроцлав"
    };

    private static final String[] TOUR_NAMES = new String[]{
            "ТУДА-СЮДА",
            "ОТСЮДА-ТУДА",
            "Горячий тур в DEFAULT_CITY",
            "Горячий тур",

    };


    private static final String[] DESCRIPTIONS = new String[]{
            "Горящий тур в ОАЭ с вылетом из Киева по невероятно низкой цене! Большой выбор отелей и продолжительности вашего отдыха!",
            "Пятизвездочный отель, на самом берегу Красного моря. Отличный сервис, питание и инфраструктура отеля! Выгодное предложение!",
            "Бархатный сезон! Выгодные предложения!Планируете свой отдых в жарком Египте? Забронируйте тур с вылетом из Минска, Витебска, Гомеля и Могилева!",
            "САМЫЕ ВЫГОДНЫЕ ПРЕДЛОЖЕНИЯ В ХУРГАДУ И ШАРМ-ЭЛЬ-ШЕЙХ! ВЫЛЕТЫ ИЗ КИЕВА, МИНСКА, МОГИЛЕВА, ВИТЕБСКА, ГОМЕЛЯ ЛЬВОВА! ГОРЯЩИЕ ДАТЫ И РАННЕЕ БРОНИРОВАНИЕ!",
            "Египет из Киева, Минска и Гомеля! Супер цены на туры с вылетом из Киева! Предоставляем трансфер в аэропорт.",
    };

    public static class NameGenerator {

        private static String[] Beginning = {"Kr", "Ca", "Ra", "Mrok", "Cru",
                "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
                "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
                "Mar", "Luk"};
        private static String[] Middle = {"air", "ir", "mi", "sor", "mee", "clo",
                "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
                "marac", "zoir", "slamar", "salmar", "urak"};
        private static String[] End = {"d", "ed", "ark", "arc", "es", "er", "der",
                "tron", "med", "ure", "zur", "cred", "mur"};

        private static Random rand = new Random();

        public static String generateName() {

            return Beginning[rand.nextInt(Beginning.length)] +
                    Middle[rand.nextInt(Middle.length)] +
                    End[rand.nextInt(End.length)];

        }

    }

    private static <T> T nextRandomFromArray(T[] array) {
        return array[nextInt(array.length)];
    }

    private static int nextInt(int bound) {
        return new Random().nextInt(bound);
    }

    private static String nextPhoneNumber() {
        return String.format("0%d%07d", nextMobileOperatorCode(), nextInt(10000000));
    }

    private static int nextMobileOperatorCode() {
        return nextRandomFromArray(MOBILE_OPERATOR_CODES);
    }

    private static String nextDescription() {
        return nextRandomFromArray(DESCRIPTIONS);
    }

    private static String nextTourName() {
        return nextRandomFromArray(TOUR_NAMES);
    }

    private static String nextCity() {
        return nextRandomFromArray(CITIES);
    }

    private static Tour.TourStatus nextTourStatus() {
        return nextRandomFromArray(Tour.TourStatus.values());
    }


    private static long nextAmount() {
        return nextInt(100) * 100 + 100;
    }

    private static Tourist nextTourist() {
        Tourist tourist = new Tourist();
        tourist.setUserName(NameGenerator.generateName());
        tourist.setEmail(tourist.getUserName() + nextInt(100) + "@gmail.com");
        tourist.setLastName(NameGenerator.generateName());
        tourist.setFirstName(NameGenerator.generateName());
        tourist.setRoles(new HashSet<>(Arrays.asList(userRole)));
        tourist.setPassword("12345678AAa");
        tourist.setPhone(nextPhoneNumber());
        return tourist;
    }

    private static Guide nextGuide() {
        Guide guide = new Guide();
        guide.setUserName(NameGenerator.generateName());
        guide.setEmail(guide.getUserName() + nextInt(100) + "@gmail.com");
        guide.setLastName(NameGenerator.generateName());
        guide.setFirstName(NameGenerator.generateName());
        guide.setRoles(new HashSet<>(Arrays.asList(guideRole,userRole)));
        guide.setPassword("12345678AAa");
        guide.setPhone(nextPhoneNumber());
        return guide;
    }

    private static Tour nextTour() {
        Tour tour = new Tour();
        tour.setDescription(nextDescription());
        tour.setName(nextTourName());
        tour.setStartCity(nextCity());
        tour.setStatus(nextTourStatus());
        tour.setFromDate(new Date(Calendar.DATE));
        tour.setByDate(new Date(Calendar.DATE + 15));
        tour.setCities(Arrays.asList(nextCity(), nextCity(), nextCity()));
        return tour;
    }

    private static Group nextGroup(Tour tour) {
        Group group = new Group();
        group.setTour(tour);

        return group;

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner cmd(TouristAccountService touristService, GuideAccountService guideService, TourService tourService, GroupService groupService) {
        return (args) -> {

            if (true) {
                int amount = 20;
                LocalDateTime now = LocalDateTime.now();

                for (int i = 0; i < amount; i++) {

                    Tourist tourist = nextTourist();
                    Tour tour = nextTour();
                    Group group = nextGroup(tour);

                    touristService.addNewUser(tourist);

                    tourService.addNewTour(tour);

                    groupService.addGroup(group);



                }

                for (int i = 0; i < amount/4; i++) {
                    Guide guide = nextGuide();
                    guideService.addNewUser(guide);
                }

            }


        };
    }

}
