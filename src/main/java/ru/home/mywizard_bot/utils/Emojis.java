package ru.home.mywizard_bot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum Emojis {
    BACK(EmojiParser.parseToUnicode(":arrow_left:")),
    MAINMENU(EmojiParser.parseToUnicode(":arrow_up:")),
    SPARKLES(EmojiParser.parseToUnicode(":sparkles:")),
    SCROLL(EmojiParser.parseToUnicode(":scroll:")),
    MAGE(EmojiParser.parseToUnicode(":mage:")),
    PIZZA(EmojiParser.parseToUnicode(":pizza:")),
    SUSHI(EmojiParser.parseToUnicode(":sushi:")),
    BASKET(EmojiParser.parseToUnicode(":shopping_trolley:")),
    CHECKOUT(EmojiParser.parseToUnicode(":oncoming_taxi:")),
    ABOUT(EmojiParser.parseToUnicode(":izakaya_lantern:")),
    POINTDOWN(EmojiParser.parseToUnicode(":point_down:")),
    ARROWSCOUNT(EmojiParser.parseToUnicode(":arrows_counterclockwise:")),
    X(EmojiParser.parseToUnicode(":x:")),
    CHOOSEMENU(EmojiParser.parseToUnicode(":point_down:"));

    private String emojiName;

    @Override
    public String toString() {
        return String.format(emojiName);
    }
}
