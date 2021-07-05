package ru.home.mywizard_bot.botapi.handlers.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mywizard_bot.botapi.BotState;
import ru.home.mywizard_bot.botapi.InputMessageHandler;
import ru.home.mywizard_bot.cache.UserDataCache;
import ru.home.mywizard_bot.model.UserProfileData;
import ru.home.mywizard_bot.service.MainMenuService;
import ru.home.mywizard_bot.service.PredictionService;
import ru.home.mywizard_bot.service.ReplyMessagesService;
import ru.home.mywizard_bot.service.UsersProfileDataService;
import ru.home.mywizard_bot.utils.Emojis;

@Component
public class StartMenuHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private PredictionService predictionService;
    private UsersProfileDataService profileDataService;
    private MainMenuService mainMenuService;
    String regex = "\\d+";

    public StartMenuHandler(MainMenuService mainMenuService, UserDataCache userDataCache, ReplyMessagesService messagesService,
                            PredictionService predictionService,
                            UsersProfileDataService profileDataService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.predictionService = predictionService;
        this.profileDataService = profileDataService;
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
        return BotState.START_MENU;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = mainMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.chooseMenu", Emojis.CHOOSEMENU));
        if (botState.equals(BotState.START_MENU)){
            replyToUser = mainMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.chooseMenu", Emojis.CHOOSEMENU));
            userDataCache.setUsersCurrentBotState(userId, BotState.START_MENU_ACT);

        }
////        if (usersAnswer.equals(messagesService.getReplyText("reply.pizza", Emojis.PIZZA))){
////            replyToUser = mainMenuService.getMainMenuMessagePizza(chatId, "Выберите блюдо");
////            userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA);
////        }
//        if (usersAnswer.equals(messagesService.getReplyText("reply.sushi", Emojis.SUSHI))){
//            replyToUser = mainMenuService.getMainMenuMessageSushi(chatId, "Выберите блюдо");
//            userDataCache.setUsersCurrentBotState(userId, BotState.SUSHI);
//        }
////        if (usersAnswer.equals(messagesService.getReplyText("reply.basket", Emojis.BASKET))){
////            replyToUser = mainMenuService.getMainMenuMessageBasket(chatId);
////            userDataCache.setUsersCurrentBotState(userId, BotState.BASKET_ACT);
////        }
//        if (usersAnswer.equals(messagesService.getReplyText("reply.checkout", Emojis.CHECKOUT))){
//            replyToUser = mainMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.pizza", Emojis.PIZZA));
//            userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA);
//        }
//        if (usersAnswer.equals(messagesService.getReplyText("reply.about", Emojis.ABOUT))){
//            replyToUser = mainMenuService.getMainMenuMessage(chatId, messagesService.getReplyText("reply.pizza", Emojis.PIZZA));
//            userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA);
//        }

        return replyToUser;
    }

}
