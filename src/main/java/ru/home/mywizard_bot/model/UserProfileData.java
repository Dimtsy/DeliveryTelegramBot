package ru.home.mywizard_bot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.home.mywizard_bot.botapi.BotState;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Данные анкеты пользователя
 */

@Data//вместо геттеры сеттеров тустринг хэшкод и конструкторы
@FieldDefaults(level = AccessLevel.PRIVATE)//Вместо того что-бы писать что все поля приватные
@Document(collection = "userProfileData")//"userProfileData" название базы данных в Монго ДБ
public class UserProfileData implements Serializable {
    @Id//  поле id будет для базы данных айдишником
    String id;
    Map<BotState, Integer> userBasket;
//    String name;
//    String gender;
//    String color;
//    String movie;
//    String song;
//    int age;
//    int number;
    long chatId;


//    @Override
//    public String toString() {
//        return String.format("Имя: %s%nВозраст: %d%nПол: %s%nЛюбимая цифра: %d%n" +
//                        "Цвет: %s%nФильм: %s%nПесня: %s%n");
////        , getName());
////                getAge(), getGender(), getNumber(),
////                getColor(), getMovie(), getSong());
//    }
}
