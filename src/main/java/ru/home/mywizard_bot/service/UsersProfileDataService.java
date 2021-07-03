package ru.home.mywizard_bot.service;

import org.springframework.stereotype.Service;
import ru.home.mywizard_bot.model.UserProfileData;
import ru.home.mywizard_bot.repository.UsersProfileMongoRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Сохраняет, удаляет, ищет анкеты пользователя.
 *
 * @author Sergei Viacheslaev
 */
@Service
public class UsersProfileDataService {

    private UsersProfileMongoRepository profileMongoRepository;

    public UsersProfileDataService(UsersProfileMongoRepository profileMongoRepository) {
        this.profileMongoRepository = profileMongoRepository;
    }

    public List<UserProfileData> getAllProfiles(long chatId) {
        List<UserProfileData> userProfileDataList = new ArrayList<>();

        profileMongoRepository.findAll().forEach(t->{
            if(t.getChatId()==chatId) {
                userProfileDataList.add(t);
            }
        });
        return userProfileDataList;
    }

    public void saveUserProfileData(UserProfileData userProfileData) {
        profileMongoRepository.save(userProfileData);
    }

    public void deleteUsersProfileData(String profileDataId) {
        profileMongoRepository.deleteById(profileDataId);
    }
    public void deleteUsersProfileDataChatId(long chatId) {
        profileMongoRepository.deleteByChatId(chatId);
    }

    public UserProfileData getUserProfileData(long chatId) {
        return profileMongoRepository.findByChatId(chatId);
    }


}
