package ru.home.mywizard_bot.cache;

import org.springframework.stereotype.Component;
import ru.home.mywizard_bot.botapi.BotState;
import ru.home.mywizard_bot.model.UserProfileData;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * In-memory cache.
 * usersBotStates: user_id and user's bot state
 * usersProfileData: user_id  and user's profile data.
 */

@Component
public class UserDataCache implements DataCache {
    private Map<Integer, BotState> usersBotStates = new HashMap<>();
    private Map<Integer, UserProfileData> usersProfileData = new HashMap<>();
    private Map<Integer, Stack<BotState>> usersBotStatesStack = new HashMap<>();

    @Override
    public void setUsersCurrentBotStateStack(int userId, Stack<BotState> botStateStack) {
        usersBotStatesStack.put(userId, botStateStack);
    }

    @Override
    public Stack<BotState> getUsersCurrentBotStateStack(int userId) {
        Stack<BotState> botStateStack = usersBotStatesStack.get(userId);
        if (botStateStack == null) {
            botStateStack = new Stack<>();
            botStateStack.push(BotState.START_MENU);
        }
        return botStateStack;
    }

    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.START_MENU;
        }

        return botState;
    }

    @Override
    public UserProfileData getUserProfileData(int userId) {
        UserProfileData userProfileData = usersProfileData.get(userId);
        if (userProfileData == null) {
            userProfileData = new UserProfileData();
        }
        return userProfileData;
    }

    @Override
    public void saveUserProfileData(int userId, UserProfileData userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }
}
