package ru.home.mywizard_bot.botapi.handlers.fillingprofile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.mywizard_bot.botapi.BotState;
import ru.home.mywizard_bot.botapi.InputMessageHandler;
import ru.home.mywizard_bot.cache.UserDataCache;
import ru.home.mywizard_bot.model.UserProfileData;
import ru.home.mywizard_bot.service.PredictionService;
import ru.home.mywizard_bot.service.ReplyMessagesService;
import ru.home.mywizard_bot.service.UsersProfileDataService;
import ru.home.mywizard_bot.utils.Emojis;

import java.util.ArrayList;
import java.util.List;


/**
 * Формирует анкету пользователя.
 */

@Slf4j
@Component
public class FillingProfileHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private PredictionService predictionService;
    private UsersProfileDataService profileDataService;
    String regex = "\\d+";

    public FillingProfileHandler(UserDataCache userDataCache, ReplyMessagesService messagesService,
                                 PredictionService predictionService, UsersProfileDataService profileDataService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.predictionService = predictionService;
        this.profileDataService = profileDataService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_NAME);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_NAME)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askName");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
        }

        if (botState.equals(BotState.ASK_AGE)) {
            profileData.setName(usersAnswer);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askAge");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GENDER);
        }

        if (botState.equals(BotState.ASK_GENDER)) {
            if (!usersAnswer.matches(regex)){
                replyToUser = messagesService.getReplyMessage(chatId, "reply.noIntegerError");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GENDER);
            }else {
                profileData.setAge(Integer.parseInt(usersAnswer));
                replyToUser = messagesService.getReplyMessage(chatId, "reply.askGender");
                replyToUser.setReplyMarkup(getGenderButtonsMarkup());
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_NUMBER);
            }

        }

        if (botState.equals(BotState.ASK_NUMBER)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askNumber");
            profileData.setGender(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
        }

        if (botState.equals(BotState.ASK_COLOR)) {
            if (!usersAnswer.matches(regex)){
                replyToUser = messagesService.getReplyMessage(chatId, "reply.noIntegerError");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
            }else {
                replyToUser = messagesService.getReplyMessage(chatId, "reply.askColor");
                profileData.setNumber(Integer.parseInt(usersAnswer));
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_MOVIE);
            }

        }

        if (botState.equals(BotState.ASK_MOVIE)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askMovie");
            profileData.setColor(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_SONG);
        }

        if (botState.equals(BotState.ASK_SONG)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askSong");
            profileData.setMovie(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
        }

        if (botState.equals(BotState.PROFILE_FILLED)) {
            profileData.setSong(usersAnswer);
            profileData.setChatId(chatId);
            if (profileDataService.getUserProfileData(chatId)!=null){
                profileDataService.deleteUsersProfileDataChatId(chatId);
            }

            profileDataService.saveUserProfileData(profileData);

            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

            String profileFilledMessage = messagesService.getReplyText("reply.profileFilled",
                    profileData.getName(), Emojis.SPARKLES);
            String predictionMessage = predictionService.getPrediction();

            replyToUser = new SendMessage(chatId, String.format("%s%n%n%s %s", profileFilledMessage, Emojis.SCROLL, predictionMessage));
            replyToUser.setParseMode("HTML");
        }

        userDataCache.saveUserProfileData(userId, profileData);

        return replyToUser;
    }

    private InlineKeyboardMarkup getGenderButtonsMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonGenderMan = new InlineKeyboardButton().setText("М");
        InlineKeyboardButton buttonGenderWoman = new InlineKeyboardButton().setText("Ж");

        //Every button must have callBackData, or else not work !
        buttonGenderMan.setCallbackData("buttonMan");
        buttonGenderWoman.setCallbackData("buttonWoman");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonGenderMan);
        keyboardButtonsRow1.add(buttonGenderWoman);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }


}



