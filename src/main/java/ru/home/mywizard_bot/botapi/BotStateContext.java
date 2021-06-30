package ru.home.mywizard_bot.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines message handlers for each state.
 */
@Component
public class BotStateContext {
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();
//Передается с помощью Спринга список List с Хэндлерами реализующими Интрфейс InputMessageHandler
    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        // и тут же в конструкторе наполняется Мапа messageHandlers всеми Хэндлерами
        // теперь все хэндлеры хроняться в messageHandlers и их можно найти
        // по их статусу. С помощью handler.getHandlerName() передается Имя, а handler Имя и метод
        // который и обрабатывает дальше.
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }
    public BotApiMethod<?> processInputCallbackQuery(BotState currentState, CallbackQuery buttonQuery) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.processCallbackQueryHandler(buttonQuery);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isFillingProfileState(currentState)) {
            return messageHandlers.get(BotState.FILLING_PROFILE);
        }
        if (isFillingProfileStateStartMenu(currentState)) {
            return messageHandlers.get(BotState.START_MENU);
        }
        if (isFillingProfileStatePizza(currentState)) {
            return messageHandlers.get(BotState.PIZZA);
        }
//Если не подходит не один из статусов то остается назначенный статус в ТелеграмФасаде
// в данном случае это ASK_DESTINY и сработает обработчик из AskDestinyHandler
        return messageHandlers.get(currentState);
    }

    private boolean isFillingProfileState(BotState currentState) {
        switch (currentState) {
            case ASK_NAME:
            case ASK_AGE:
            case ASK_GENDER:
            case ASK_NUMBER:
            case ASK_MOVIE:
            case ASK_SONG:
            case ASK_COLOR:
            case FILLING_PROFILE:
            case PROFILE_FILLED:
                return true;
            default:
                return false;
        }
    }
    private boolean isFillingProfileStateStartMenu(BotState currentState) {
        switch (currentState) {
         case START_MENU_ACT:
         case START_MENU:
         return true;
         default:
         return false;
        }
    }
    private boolean isFillingProfileStatePizza(BotState currentState) {
        switch (currentState) {
            case PIZZA:
            case PIZZA_CHEESY:
            case PIZZA_CHEESY2:
            case PIZZA_CHEESY3:
                return true;
            default:
                return false;
        }
    }


}





