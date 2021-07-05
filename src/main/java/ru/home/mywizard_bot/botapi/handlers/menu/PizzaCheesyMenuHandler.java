package ru.home.mywizard_bot.botapi.handlers.menu;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.mywizard_bot.MyWizardTelegramBot;
import ru.home.mywizard_bot.botapi.BotState;
import ru.home.mywizard_bot.botapi.InputMessageHandler;
import ru.home.mywizard_bot.cache.UserDataCache;
import ru.home.mywizard_bot.model.UserProfileData;
import ru.home.mywizard_bot.service.MainMenuService;
import ru.home.mywizard_bot.service.PredictionService;
import ru.home.mywizard_bot.service.ReplyMessagesService;
import ru.home.mywizard_bot.service.UsersProfileDataService;
import ru.home.mywizard_bot.utils.Emojis;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class PizzaCheesyMenuHandler implements InputMessageHandler {

        private MyWizardTelegramBot myWizardBot;
        private UserDataCache userDataCache;
        private ReplyMessagesService messagesService;
        private PredictionService predictionService;
        private UsersProfileDataService profileDataService;
        private MainMenuService mainMenuService;
        String regex = "\\d{1,2}";

        public PizzaCheesyMenuHandler (@Lazy MyWizardTelegramBot myWizardBot, UserDataCache userDataCache, ReplyMessagesService messagesService,
                                PredictionService predictionService,
                                UsersProfileDataService profileDataService, MainMenuService mainMenuService) {
            this.userDataCache = userDataCache;
            this.messagesService = messagesService;
            this.predictionService = predictionService;
            this.profileDataService = profileDataService;
            this.mainMenuService = mainMenuService;
            this.myWizardBot = myWizardBot;
        }
        @Override
        public BotApiMethod<?> processCallbackQueryHandler(CallbackQuery buttonQuery) {
            return processUsersInputCallback(buttonQuery);
        }
        @Override
        public SendMessage handle(Message message) {
            return processUsersInput(message);
        }

        @Override
        public BotState getHandlerName() {
            return BotState.PIZZA_CHEESY;
        }

        private BotApiMethod<?> processUsersInputCallback(CallbackQuery buttonQuery){

            final int messId = buttonQuery.getMessage().getMessageId();
            final long chatId = buttonQuery.getMessage().getChatId();
            final int userId = buttonQuery.getFrom().getId();
            final String inlineMessId = buttonQuery.getInlineMessageId();

            BotState botState = userDataCache.getUsersCurrentBotState(userId);

            BotApiMethod<?> callBackAnswer = null;



            if (botState.equals(BotState.PIZZA_CHEESY)){
                if (buttonQuery.getData().equals("buttonBack")){
                    callBackAnswer = editMessageText(String.format("<b>Большая</b>%nТесто, соус сырный, сыр мраморный, " +
                            "сыр сливочный, творог + коробка.%nВес: 920 гр.%n Цена: 17р."),buttonQuery);
                    myWizardBot.sendRemoveClock(buttonQuery);
                    userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA_CHEESY3);
                }

                if (buttonQuery.getData().equals("buttonForwar")){
                    callBackAnswer = editMessageText(String.format("<b>Средняя</b>%nТесто, соус сырный, сыр мраморный, " +
                            "сыр сливочный, творог + коробка.%nВес: 600 гр.%n Цена: 13р."),buttonQuery);
                    myWizardBot.sendRemoveClock(buttonQuery);
                    userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA_CHEESY2);
                }
            }

            if (botState.equals(BotState.PIZZA_CHEESY2)){
                if (buttonQuery.getData().equals("buttonBack")){
                    callBackAnswer = editMessageText(String.format("<b>Маленькая</b>%nТесто, соус сырный, сыр мраморный, " +
                            "сыр сливочный, творог + коробка.%nВес: 360 гр.%n Цена: 8р."),buttonQuery);
                    myWizardBot.sendRemoveClock(buttonQuery);
                    userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA_CHEESY);
                }

                if (buttonQuery.getData().equals("buttonForwar")){
                    callBackAnswer = editMessageText(String.format("<b>Большая</b>%nТесто, соус сырный, сыр мраморный, " +
                            "сыр сливочный, творог + коробка.%nВес: 920 гр.%n Цена: 17р."),buttonQuery);
                    myWizardBot.sendRemoveClock(buttonQuery);
                    userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA_CHEESY3);
                }
            }
            if (botState.equals(BotState.PIZZA_CHEESY3)){
                if (buttonQuery.getData().equals("buttonBack")){
                    callBackAnswer = editMessageText(String.format("<b>Средняя</b>%nТесто, соус сырный, сыр мраморный, " +
                            "сыр сливочный, творог + коробка.%nВес: 600 гр.%n Цена: 13р."),buttonQuery);
                    myWizardBot.sendRemoveClock(buttonQuery);
                    userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA_CHEESY2);
                }

                if (buttonQuery.getData().equals("buttonForwar")){
                    callBackAnswer = editMessageText(String.format("<b>Маленькая</b>%nТесто, соус сырный, сыр мраморный, " +
                            "сыр сливочный, творог + коробка.%nВес: 360 гр.%n Цена: 8р."),buttonQuery);
                    myWizardBot.sendRemoveClock(buttonQuery);
                    userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA_CHEESY);
                }
            }

            return callBackAnswer;
        }
        private SendMessage processUsersInput(Message inputMsg) {
            String usersAnswer = inputMsg.getText();
            int userId = inputMsg.getFrom().getId();
            long chatId = inputMsg.getChatId();


//        UserProfileData profileData = userDataCache.getUserProfileData(userId);
            BotState botState = userDataCache.getUsersCurrentBotState(userId);
//        Map<BotState, Integer> userBasket;
            SendMessage replyToUser = mainMenuService.getMainMenuPizzaNumbers(chatId, "Выберите или введите количество:");



            if (usersAnswer.matches(regex)){


                if (botState.equals(BotState.PIZZA_CHEESY)) {
                    System.out.println("PIZZA_CHEESY");
                    myWizardBot.sendMessageExecute(mainMenuService.getMainMenuMessagePizza(chatId, String.format("Добавлено в корзину%nХотите что-то еще?")));
//                replyToUser = mainMenuService.getMainMenuMessagePizza(chatId, String.format("Добавлено в корзину%nХотите что-то еще?"));
                    addBasket(BotState.PIZZA_CHEESY,inputMsg);
                }

                if (botState.equals(BotState.PIZZA_CHEESY2)) {
                    System.out.println("PIZZA_CHEESY2");
                    myWizardBot.sendMessageExecute(mainMenuService.getMainMenuMessagePizza(chatId, String.format("Добавлено в корзину%nХотите что-то еще?")));
                    addBasket(BotState.PIZZA_CHEESY2, inputMsg);
                }
                if (botState.equals(BotState.PIZZA_CHEESY3)) {
                    System.out.println("PIZZA_CHEESY3");
                    myWizardBot.sendMessageExecute(mainMenuService.getMainMenuMessagePizza(chatId, String.format("Добавлено в корзину%nХотите что-то еще?")));
                    addBasket(BotState.PIZZA_CHEESY3, inputMsg);
                }
                replyToUser =null;
            }else {
                if (usersAnswer.equals("Пицца «Сырная»")||botState.equals(BotState.PIZZA_CHEESY)
                        ||botState.equals(BotState.PIZZA_CHEESY2)||botState.equals(BotState.PIZZA_CHEESY3)){
                    myWizardBot.sendPhoto(chatId,"", "static/images/pizza_cheesy.jpg");

                    replyToUser = mainMenuService.getMainMenuPizzaNumbers(chatId, String.format("<b>Маленькая</b>%nТесто, соус сырный, сыр мраморный, " +
                            "сыр сливочный, творог + коробка.%nВес: 360 гр.%n Цена: 8р."));
                    replyToUser.setReplyMarkup(getInlineMessageButtonsForwardBack());
                    replyToUser.setParseMode("HTML");
                    myWizardBot.sendMessageExecute(replyToUser);

                    replyToUser = mainMenuService.getMainMenuPizzaNumbers(chatId, "Выберите или введите количество:");



                    userDataCache.setUsersCurrentBotState(userId, BotState.PIZZA_CHEESY);
                }
            }

            return replyToUser;
        }

        private void addBasket(BotState botState, Message inputMsg){
            String usersAnswer = inputMsg.getText();
            int userId = inputMsg.getFrom().getId();
            long chatId = inputMsg.getChatId();


            UserProfileData profileData = userDataCache.getUserProfileData(userId);
            if (profileDataService.getUserProfileData(chatId)!=null){
                profileData = profileDataService.getUserProfileData(chatId);

                if (profileData.getUserBasket().get(botState)==null){
                    profileData.getUserBasket().put(botState, Integer.valueOf(usersAnswer));
                }else {
                    if((Integer.parseInt(usersAnswer)+
                            profileData.getUserBasket().get(botState))<=999){
                        profileData.getUserBasket().put(botState, Integer.parseInt(usersAnswer)+
                                profileData.getUserBasket().get(botState));
                    }else {
                        myWizardBot.sendMessageExecute(mainMenuService.getMainMenuMessagePizza(chatId, String.format("Товар не был добавлен. Пожалуста проверьте свою корзину," +
                                " что-бы продолжить действие ее следует очистить или выбрать меньшее число товаров.")));
                    }

                }
                profileDataService.deleteUsersProfileDataChatId(chatId);

            }else {
                Map<BotState,Integer> userMap = new TreeMap<>();
                userMap.put(botState, Integer.valueOf(usersAnswer));
                profileData.setUserBasket(userMap);
            }
            profileData.setChatId(chatId);
            profileDataService.saveUserProfileData(profileData);
            System.out.println(Integer.parseInt(usersAnswer)+" "+
                    profileData.getUserBasket().get(botState));
        }

        private InlineKeyboardMarkup getInlineMessageButtonsForwardBack() {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

            InlineKeyboardButton buttonBack = new InlineKeyboardButton().setText("«Назад");
            InlineKeyboardButton buttonForwar = new InlineKeyboardButton().setText("Вперед»");


            //Every button must have callBackData, or else not work !
            buttonBack.setCallbackData("buttonBack");
            buttonForwar.setCallbackData("buttonForwar");


            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(buttonBack);
            keyboardButtonsRow1.add(buttonForwar);



            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(keyboardButtonsRow1);

            inlineKeyboardMarkup.setKeyboard(rowList);

            return inlineKeyboardMarkup;
        }
        private EditMessageText editMessageText(String description, CallbackQuery buttonQuery){
            final int messId = buttonQuery.getMessage().getMessageId();
            final long chatId = buttonQuery.getMessage().getChatId();
            final String inlineMessId = buttonQuery.getInlineMessageId();

            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setMessageId(messId);
            editMessageText.setChatId(chatId);
            editMessageText.setInlineMessageId(inlineMessId);
            editMessageText.setText(description);
            editMessageText.setReplyMarkup(getInlineMessageButtonsForwardBack());
            editMessageText.setParseMode("HTML");
            return editMessageText;
        }
    }


