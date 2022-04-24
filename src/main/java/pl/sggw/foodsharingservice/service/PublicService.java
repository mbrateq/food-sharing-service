package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.dto.CreateUserDto;
import pl.sggw.foodsharingservice.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface PublicService {

    User addUser(CreateUserDto createUserDto);

}
