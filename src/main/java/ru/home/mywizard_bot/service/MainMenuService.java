package ru.home.mywizard_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.mywizard_bot.utils.Emojis;

import java.util.ArrayList;
import java.util.List;

/**
 * Управляет отображением главного меню в чате.
 *
 * @author Sergei Viacheslaev
 */
@Service
public class MainMenuService {
    private ReplyMessagesService messagesService;

    public MainMenuService(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        final SendMessage mainMenuMessage =
                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

        return mainMenuMessage;
    }
    public SendMessage getMainMenuMessagePizza(final long chatId, final String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboardPizza();
        final SendMessage mainMenuMessage =
                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

        return mainMenuMessage;
    }
    public SendMessage getMainMenuMessageSushi(final long chatId, final String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboardSushi();
        final SendMessage mainMenuMessage =
                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

        return mainMenuMessage;
    }
    public SendMessage getMainMenuPizzaNumbers(final long chatId, final String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyNumbers();
        final SendMessage mainMenuMessage =
                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

        return mainMenuMessage;
    }
    private ReplyKeyboardMarkup getMainMenuKeyboard() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.pizza", Emojis.PIZZA)));
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.sushi", Emojis.SUSHI)));
        row2.add(new KeyboardButton(messagesService.getReplyText("reply.basket", Emojis.BASKET)));
        row2.add(new KeyboardButton(messagesService.getReplyText("reply.checkout", Emojis.CHECKOUT)));
        row3.add(new KeyboardButton(messagesService.getReplyText("reply.about", Emojis.ABOUT)));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

//        KeyboardRow row1 = new KeyboardRow();
//        KeyboardRow row2 = new KeyboardRow();
//        KeyboardRow row3 = new KeyboardRow();
//        row1.add(new KeyboardButton("Получить предсказание"));
//        row2.add(new KeyboardButton("Моя анкета"));
//        row2.add(new KeyboardButton("Скачать анкету"));
//        row3.add(new KeyboardButton("Помощь"));
//        keyboard.add(row1);
//        keyboard.add(row2);
//        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(final long chatId,
                                                  String textMessage,
                                                  final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getMainMenuKeyboardPizza() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.back", Emojis.BACK)));
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.mainMenu", Emojis.MAINMENU)));
        row2.add(new KeyboardButton("Пицца «Сырная»"));
        row3.add(new KeyboardButton("Пицца «Маргарита»"));
        row4.add(new KeyboardButton("Пицца «Охотничья»"));
        row5.add(new KeyboardButton(messagesService.getReplyText("reply.basket", Emojis.BASKET)));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboardSushi() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.back", Emojis.BACK)));
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.mainMenu", Emojis.MAINMENU)));
        row2.add(new KeyboardButton("Ролл «Филадельфия»"));
        row3.add(new KeyboardButton("Ролл «Калифорния»"));
        row4.add(new KeyboardButton("Ролл «Цезарь»"));
        row5.add(new KeyboardButton(messagesService.getReplyText("reply.basket", Emojis.BASKET)));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getMainMenuKeyNumbers() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        row1.add(new KeyboardButton("1"));
        row1.add(new KeyboardButton("2"));
        row1.add(new KeyboardButton("3"));
        row2.add(new KeyboardButton("4"));
        row2.add(new KeyboardButton("5"));
        row2.add(new KeyboardButton("6"));
        row3.add(new KeyboardButton("7"));
        row3.add(new KeyboardButton("8"));
        row3.add(new KeyboardButton("9"));
        row4.add(new KeyboardButton(messagesService.getReplyText("reply.back", Emojis.BACK)));
        row4.add(new KeyboardButton(messagesService.getReplyText("reply.mainMenu", Emojis.MAINMENU)));
        row5.add(new KeyboardButton(messagesService.getReplyText("reply.basket", Emojis.BASKET)));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
