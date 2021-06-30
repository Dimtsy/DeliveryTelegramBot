package ru.home.mywizard_bot.botapi.handlers.askdestiny;

import com.vdurmont.emoji.Emoji;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.mywizard_bot.botapi.BotState;
import ru.home.mywizard_bot.botapi.InputMessageHandler;
import ru.home.mywizard_bot.service.MainMenuService;
import ru.home.mywizard_bot.service.ReplyMessagesService;
import ru.home.mywizard_bot.utils.Emojis;

import java.util.ArrayList;
import java.util.List;


/**
 * Спрашивает пользователя- хочет ли он получить предсказание.
 */

@Slf4j
@Component
public class AskDestinyHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private MainMenuService mainMenuService;

    public AskDestinyHandler(ReplyMessagesService messagesService,MainMenuService mainMenuService) {
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
    }

    @Override
    public BotApiMethod<?> processCallbackQueryHandler(CallbackQuery buttonQuery) {
        return null;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_DESTINY;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();

//        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.askDestiny", Emojis.POINTDOWN);

//        replyToUser.setReplyMarkup(getInlineMessageButtons());


        return mainMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.hello", Emojis.POINTDOWN));
    }

//    private InlineKeyboardMarkup getInlineMessageButtons() {
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//
//        InlineKeyboardButton buttonYes = new InlineKeyboardButton().setText("Да");
//        InlineKeyboardButton buttonNo = new InlineKeyboardButton().setText("Нет, спасибо");
//        InlineKeyboardButton buttonIwillThink = new InlineKeyboardButton().setText("Я подумаю");
//        InlineKeyboardButton buttonIdontKnow = new InlineKeyboardButton().setText("Еще не определился");
//
//        //Every button must have callBackData, or else not work !
//        buttonYes.setCallbackData("buttonYes");
//        buttonNo.setCallbackData("buttonNo");
//        buttonIwillThink.setCallbackData("buttonIwillThink");
//        buttonIdontKnow.setCallbackData("-");
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        keyboardButtonsRow1.add(buttonYes);
//        keyboardButtonsRow1.add(buttonNo);
//
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//        keyboardButtonsRow2.add(buttonIwillThink);
//        keyboardButtonsRow2.add(buttonIdontKnow);
//
//
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow1);
//        rowList.add(keyboardButtonsRow2);
//
//        inlineKeyboardMarkup.setKeyboard(rowList);
//
//        return inlineKeyboardMarkup;
//    }


}



