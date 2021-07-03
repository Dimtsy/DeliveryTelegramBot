package ru.home.mywizard_bot.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.mywizard_bot.MyWizardTelegramBot;
import ru.home.mywizard_bot.botapi.BotState;

import ru.home.mywizard_bot.model.UserProfileData;
import ru.home.mywizard_bot.utils.Emojis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Управляет отображением главного меню в чате.
 *
 * @author Sergei Viacheslaev
 */

@Service
public class MainMenuService {
//    private UserProfileData profileData;
    static public String messageBasketReply;
    private ReplyMessagesService messagesService;
    private UsersProfileDataService profileDataService;
    private MyWizardTelegramBot myWizardBot;


    public MainMenuService(@Lazy MyWizardTelegramBot myWizardBot, UsersProfileDataService profileDataService, ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
        this.profileDataService = profileDataService;
        this.myWizardBot = myWizardBot;

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
    public SendMessage getMainMenuMessageBasket(final long chatId, final String textMessage, Map<BotState,Integer> map) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboardBasket(chatId,map);
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
    private ReplyKeyboardMarkup getMainMenuKeyboardBasket(final long chatId,Map<BotState,Integer> map) {
        AtomicInteger sum = new AtomicInteger();


        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
//-----------
//        UserProfileData profileData = profileDataService.getUserProfileData(chatId);



        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.back", Emojis.BACK)));
        row1.add(new KeyboardButton(messagesService.getReplyText("reply.arrowscount",
                Emojis.ARROWSCOUNT)));
        keyboard.add(row1);

        messageBasketReply = String.format(" Корзина:%n%n");
        map.forEach((t,v)->{
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(messagesService.getReplyText(getReplyTextBasket(t), Emojis.X)));
            keyboard.add(row);
            messageBasketReply = messageBasketReply.concat(String.format("%s%n %s X %s = %s%n",
                    messagesService.getReplyText(getReplyTextBasket(t),""),v,getReplyPriceBasket(t),
                    getReplyPriceBasket(t)*v));
            sum.set(sum.get() + getReplyPriceBasket(t) * v);
        });
        messageBasketReply = messageBasketReply.concat(String.format("%n Итого: %s р.",sum));

//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(String.format(messagesService.getReplyText("reply.basketMemo",
//                Emojis.X,Emojis.ARROWSCOUNT)));
//        myWizardBot.sendMessageExecute(sendMessage);


        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton((messagesService.getReplyText("reply.checkout",
                Emojis.CHECKOUT))));
        keyboard.add(row2);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
    private String getReplyTextBasket(BotState botState){
        String replyText= null;
        switch (botState) {
            case PIZZA_CHEESY:
                replyText = "reply.pizzaCheesy";
             break;
            case PIZZA_CHEESY2:
                replyText = "reply.pizzaCheesy2";
                break;
            case PIZZA_CHEESY3:
                replyText = "reply.pizzaCheesy3";
                break;

        }
        return replyText;
    }
    private int getReplyPriceBasket(BotState botState){
        int replyPrice= 0;
        switch (botState) {
            case PIZZA_CHEESY:
                replyPrice = 8;
                break;
            case PIZZA_CHEESY2:
                replyPrice = 13;
                break;
            case PIZZA_CHEESY3:
                replyPrice = 17;
                break;

        }
        return replyPrice;
    }

}
