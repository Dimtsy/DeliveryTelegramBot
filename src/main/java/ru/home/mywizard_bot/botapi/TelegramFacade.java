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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
                botState = BotState.ASK_DESTINY;
                myWizardBot.sendPhoto(chatId, messagesService.getReplyText("reply.hello"), "static/images/wizard_logo.jpg");
//                myWizardBot.sendPhoto(chatId, messagesService.getReplyText("reply.hello"), "/app/src/main/resources/static/images/wizard_logo.jpg");
                break;
            case "???????????????? ????????????????????????":
                botState = BotState.FILLING_PROFILE;
                break;
            case "?????? ????????????":
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "?????????????? ????????????":
                myWizardBot.sendDocument(chatId, "???????? ????????????", getUsersProfile(userId));
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "????????????":
                botState = BotState.SHOW_HELP_MENU;
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
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();

        BotApiMethod<?> callBackAnswer = null;

        if (buttonQuery.getData().equals("-")){
            callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "???????????????????????????? ?????????????? ????????");
            //            ?????????????? ???????????? ???? ????????????
            myWizardBot.sendRemoveClock(buttonQuery);
        }


        //From Destiny choose buttons
        if (buttonQuery.getData().equals("buttonYes")) {
            //            ???????????? ?????????????????? ?? ???????????????????? ?????? ??????????????, ?? ???? ???????????????????? ??????????????????
//            EditMessageText editMessageText = new EditMessageText();
//            editMessageText.setMessageId(messId);
//            editMessageText.setChatId(chatId);
//            editMessageText.setText("?????? ???????? ?????????? ?");
//            editMessageText.setInlineMessageId(inlineMessId);
//            editMessageText.setReplyMarkup(getInlineMessageButtons2());
//???????????? ???????????????????? Inlain
//            EditMessageReplyMarkup callBackAnswer1 = new EditMessageReplyMarkup();
//            callBackAnswer1.setReplyMarkup(inlineMessId);
//            callBackAnswer1(chatId,messId);

            callBackAnswer = new SendMessage(chatId, "?????? ???????? ?????????? ?");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
            //            ?????????????? ???????????? ???? ????????????
            myWizardBot.sendRemoveClock(buttonQuery);
        } else if (buttonQuery.getData().equals("buttonNo")) {
            callBackAnswer = sendAnswerCallbackQuery("??????????????????????, ?????????? ???????????? ??????????", false, buttonQuery);
        } else if (buttonQuery.getData().equals("buttonIwillThink")) {
            callBackAnswer = sendAnswerCallbackQuery("???????????? ???????????? ???? ????????????????????????????", true, buttonQuery);
        }

        //From Gender choose buttons
        else if (buttonQuery.getData().equals("buttonMan")) {
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("??");
            userDataCache.saveUserProfileData(userId, userProfileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
            callBackAnswer = new SendMessage(chatId, "???????? ?????????????? ??????????");
            //            ?????????????? ???????????? ???? ????????????
            myWizardBot.sendRemoveClock(buttonQuery);
        } else if (buttonQuery.getData().equals("buttonWoman")) {
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("??");
            userDataCache.saveUserProfileData(userId, userProfileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
            callBackAnswer = new SendMessage(chatId, "???????? ?????????????? ??????????");
            //            ?????????????? ???????????? ???? ????????????
            myWizardBot.sendRemoveClock(buttonQuery);

        } else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }


        return callBackAnswer;


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
