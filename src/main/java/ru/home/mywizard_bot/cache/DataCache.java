package ru.home.mywizard_bot.cache;

import ru.home.mywizard_bot.botapi.BotState;
import ru.home.mywizard_bot.model.UserProfileData;

import java.util.Stack;


public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    UserProfileData getUserProfileData(int userId);

    void saveUserProfileData(int userId, UserProfileData userProfileData);

 void setUsersCurrentBotStateStack(int userId, Stack<BotState> botStateStack) ;

 Stack<BotState> getUsersCurrentBotStateStack(int userId) ;
}
