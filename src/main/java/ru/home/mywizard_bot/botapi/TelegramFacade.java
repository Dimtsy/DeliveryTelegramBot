package ru.home.mywizard_bot.botapi;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.mywizard_bot.MyWizardTelegramBot;
import ru.home.mywizard_bot.cache.UserDataCache;
import ru.home.mywizard_bot.model.UserProfileData;
import ru.home.mywizard_bot.service.MainMenuService;
import ru.home.mywizard_bot.service.ReplyMessagesService;
import ru.home.mywizard_bot.utils.Emojis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Sergei Viacheslaev
 */
@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private MainMenuService mainMenuService;
    private MyWizardTelegramBot myWizardBot;
    private ReplyMessagesService messagesService;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, MainMenuService mainMenuService,
                          @Lazy MyWizardTelegramBot myWizardBot, ReplyMessagesService messagesService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
        this.myWizardBot = myWizardBot;
        this.messagesService = messagesService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }


        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }


    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.START_MENU;
//                userDataCache.setUsersCurrentBotState(userId, botState);
//                return mainMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.hello", Emojis.POINTDOWN));

//                myWizardBot.sendPhoto(chatId, messagesService.getReplyText("reply.hello", Emojis.POINTDOWN), "static/images/wizard_logo.jpg");
//                myWizardBot.sendPhoto(chatId, messagesService.getReplyText("reply.hello"), "/app/src/main/resources/static/images/wizard_logo.jpg");
                break;
            case "Пицца\uD83C\uDF55":
                botState = BotState.PIZZA;
                break;
            case "Суши\uD83C\uDF63":
                botState = BotState.SUSHI;
                break;
            case "Корзина\uD83D\uDED2":
//                myWizardBot.sendDocument(chatId, "Ваша анкета", getUsersProfile(userId));
                botState = BotState.BASKET;
                break;
            case "Оформить заказ\uD83D\uDE96":
                botState = BotState.CHECKOUT;
                break;
            case "О нас\uD83C\uDFEE":
                botState = BotState.ABOUT;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }


        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }


    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        BotState botState;
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();

        BotApiMethod<?> callBackAnswer = null;
        botState = userDataCache.getUsersCurrentBotState(userId);
        userDataCache.setUsersCurrentBotState(userId, botState);

        callBackAnswer = botStateContext.processInputCallbackQuery(botState, buttonQuery);
        return callBackAnswer;

//        if (buttonQuery.getData().equals("-")){
//            callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");
//            //            Убирает часики на кнопке
//            myWizardBot.sendRemoveClock(buttonQuery);
//        }
//
//
//        //From Destiny choose buttons
//        if (buttonQuery.getData().equals("buttonYes")) {
//            //            Меняем сообщение и клавиатуру при нажатии, а не выкидываем следующее
////            EditMessageText editMessageText = new EditMessageText();
////            editMessageText.setMessageId(messId);
////            editMessageText.setChatId(chatId);
////            editMessageText.setText("Как тебя зовут ?");
////            editMessageText.setInlineMessageId(inlineMessId);
////            editMessageText.setReplyMarkup(getInlineMessageButtons2());
////Меняем клавиатуру Inlain
////            EditMessageReplyMarkup callBackAnswer1 = new EditMessageReplyMarkup();
////            callBackAnswer1.setReplyMarkup(inlineMessId);
////            callBackAnswer1(chatId,messId);
//
//            callBackAnswer = new SendMessage(chatId, "Как тебя зовут ?");
//            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
//            //            Убирает часики на кнопке
//            myWizardBot.sendRemoveClock(buttonQuery);
//        } else if (buttonQuery.getData().equals("buttonNo")) {
//            callBackAnswer = sendAnswerCallbackQuery("Возвращайся, когда будешь готов", false, buttonQuery);
//        } else if (buttonQuery.getData().equals("buttonIwillThink")) {
//            callBackAnswer = sendAnswerCallbackQuery("Данная кнопка не поддерживается", true, buttonQuery);
//        }
//
//        //From Gender choose buttons
//        else if (buttonQuery.getData().equals("buttonMan")) {
//            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
//            userProfileData.setGender("М");
//            userDataCache.saveUserProfileData(userId, userProfileData);
//            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
//            callBackAnswer = new SendMessage(chatId, "Твоя любимая цифра");
//            //            Убирает часики на кнопке
//            myWizardBot.sendRemoveClock(buttonQuery);
//        } else if (buttonQuery.getData().equals("buttonWoman")) {
//            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
//            userProfileData.setGender("Ж");
//            userDataCache.saveUserProfileData(userId, userProfileData);
//            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
//            callBackAnswer = new SendMessage(chatId, "Твоя любимая цифра");
//            //            Убирает часики на кнопке
//            myWizardBot.sendRemoveClock(buttonQuery);
//
//        } else {
//            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
//        }



    }


    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    @SneakyThrows
    public File getUsersProfile(int userId) {
        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        File profileFile = ResourceUtils.getFile("classpath:static/docs/users_profile.txt");
//        File profileFile = ResourceUtils.getFile("/app/src/main/resources/static/docs/users_profile.txt");
        try (FileWriter fw = new FileWriter(profileFile.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(userProfileData.toString());
        }


        return profileFile;

    }


}
