package ru.home.mywizard_bot.botapi.handlers.menu;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mywizard_bot.MyWizardTelegramBot;
import ru.home.mywizard_bot.botapi.BotState;
import ru.home.mywizard_bot.botapi.InputMessageHandler;
import ru.home.mywizard_bot.cache.UserDataCache;
import ru.home.mywizard_bot.model.UserProfileData;
import ru.home.mywizard_bot.service.MainMenuService;
import ru.home.mywizard_bot.service.ReplyMessagesService;
import ru.home.mywizard_bot.service.UsersProfileDataService;
import ru.home.mywizard_bot.utils.Emojis;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class BasketMenuHandler implements InputMessageHandler {
    private UsersProfileDataService profileDataService;
    private MainMenuService mainMenuService;
    private MyWizardTelegramBot myWizardBot;
    private ReplyMessagesService messagesService;
    private UserDataCache userDataCache;
    private String messageBasketReply;

    public BasketMenuHandler(@Lazy MyWizardTelegramBot myWizardBot,ReplyMessagesService messagesService,
            UserDataCache userDataCache, MainMenuService mainMenuService,UsersProfileDataService profileDataService) {
        this.mainMenuService = mainMenuService;
        this.profileDataService = profileDataService;
        this.myWizardBot = myWizardBot;
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotApiMethod<?> processCallbackQueryHandler(CallbackQuery buttonQuery) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.BASKET;
    }

    private SendMessage processUsersInput(Message inputMsg){
        String usersAnswer = inputMsg.getText();

        int userId = inputMsg.getFrom().getId();

        AtomicInteger sum = new AtomicInteger();
        long chatId = inputMsg.getChatId();
        SendMessage replyToUser;

        UserProfileData profileData = profileDataService.getUserProfileData(chatId);
        Map<BotState,Integer> productMap =profileData.getUserBasket();
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        switch (usersAnswer){
            case"Назад⬅️":

                break;
            case"Очистить\uD83D\uDD04":
                profileData.getUserBasket().forEach((t,v)->{
                    if(messagesService.getReplyText(getReplyTextBasket(t), Emojis.X).equals(usersAnswer)){
                        profileDataService.deleteUsersProfileData(profileData.getId());
                    }
                });
                break;
            case"Оформить заказ\uD83D\uDE96":

                break;
            default:
                AtomicReference<BotState> stat=new AtomicReference<>();
//                profileData.getUserBasket().entrySet().remove()
//                profileData.getUserBasket().entrySet().removeIf(entry -> "Sample".equalsIgnoreCase(entry.getKey()));
                productMap.forEach((t,v)->{
                    System.out.println("зашел1");
                    if(messagesService.getReplyText(getReplyTextBasket(t), Emojis.X).equals(usersAnswer)){
                        stat.set(t);
                        System.out.println("зашел2 " +t+stat);
                    }
                });
                productMap.remove(stat);
                System.out.println(productMap);
        }

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(String.format(messagesService.getReplyText("reply.basketMemo",
                    Emojis.X,Emojis.ARROWSCOUNT)));
            myWizardBot.sendMessageExecute(sendMessage);

            messageBasketReply = String.format(" Корзина:%n%n");
        productMap.forEach((t,v)->{
                messageBasketReply = messageBasketReply.concat(String.format("%s%n %s X %s = %s%n",
                        messagesService.getReplyText(getReplyTextBasket(t),""),v,getReplyPriceBasket(t),
                        getReplyPriceBasket(t)*v));
                sum.set(sum.get() + getReplyPriceBasket(t) * v);
            });
            messageBasketReply = messageBasketReply.concat(String.format("%n Итого: %s р.",sum));

            replyToUser = mainMenuService.getMainMenuMessageBasket(chatId,messageBasketReply,profileData.getUserBasket());


            userDataCache.setUsersCurrentBotState(userId, BotState.BASKET_ACT);



        return replyToUser;
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
