package com.tour.utils;

import com.tour.model.*;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public final class Creator {



    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final BaseUser.Role ROLE_USER = BaseUser.Role.ROLE_USER;
    private static final BaseUser.Role ROLE_STAFF = BaseUser.Role.ROLE_STAFF;
    private static final BaseUser.Role ROLE_ADMIN = BaseUser.Role.ROLE_ADMIN;

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

        private static String generateName() {

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

    private static int nextPrice() {
        return nextRandomFromArray(PRICE);
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

    public static Tourist nextTourist() {
        Tourist tourist = new Tourist();
        tourist.setUserName(NameGenerator.generateName());
        tourist.setEmail(tourist.getUserName() + nextInt(100) + "@gmail.com");
        tourist.setLastName(NameGenerator.generateName());
        tourist.setFirstName(NameGenerator.generateName());
        tourist.setRoles(new HashSet<>(Arrays.asList(ROLE_USER)));
        tourist.setPassword("12345678AAa");
        tourist.setPhone(nextPhoneNumber());
        return tourist;
    }

    public static Tourist setupAdmin() {
        Tourist tourist = new Tourist();
        tourist.setUserName(Admin.ADMIN);
        tourist.setEmail(tourist.getUserName() + nextInt(100) + "@gmail.com");
        tourist.setLastName(NameGenerator.generateName());
        tourist.setFirstName(NameGenerator.generateName());
        tourist.setRoles(new HashSet<>(Arrays.asList(ROLE_USER, ROLE_STAFF, ROLE_ADMIN)));
        tourist.setPassword(Admin.PASSWORD);
        tourist.setPhone(nextPhoneNumber());
        return tourist;
    }

    private static Tourist setupTourist() {
        Tourist tourist = nextTourist();
        tourist.setUserName("ARHANGEL991");
        return tourist;
    }


    public static Guide nextGuide() {
        Guide guide = new Guide();
        guide.setUserName(NameGenerator.generateName());
        guide.setEmail(guide.getUserName() + nextInt(100) + "@gmail.com");
        guide.setLastName(NameGenerator.generateName());
        guide.setFirstName(NameGenerator.generateName());
        guide.setRoles(new HashSet<>(Arrays.asList(ROLE_STAFF, ROLE_USER)));
        guide.setPassword("12345678AAa");
        guide.setPhone(nextPhoneNumber());
        return guide;
    }


    public static Tour nextTour() {
        Tour tour = new Tour();
        tour.setDescription(nextDescription());
        tour.setName(nextTourName());
        tour.setStartCity(nextCity());
        tour.setStatus(nextTourStatus());
        tour.setFromDate(new GregorianCalendar(2017, Calendar.FEBRUARY, 11).getTime());
        tour.setByDate(new GregorianCalendar(2017, Calendar.FEBRUARY, 28).getTime());
        //        tour.setFromDate(ZonedDateTime.of(2017,Calendar.FEBRUARY,11,0,0,0,0, ZoneId.systemDefault()));
        //        tour.setByDate(ZonedDateTime.of(2017,Calendar.FEBRUARY,11,0,0,0,0, ZoneId.systemDefault()));
        tour.setPrice(nextPrice());
        tour.setCities(Arrays.asList(nextCity(), nextCity(), nextCity()));
        return tour;
    }

    public static Group nextGroup(Tour tour) {
        Group group = new Group();
        group.setTour(tour);

        return group;

    }
}
