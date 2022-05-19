package pl.sggw.foodsharingservice.service;

import pl.sggw.foodsharingservice.model.types.RoleType;
import pl.sggw.foodsharingservice.model.view.UserView;

public interface AdminService {

    UserView setStatus(long userId, boolean status);

    UserView deleteUserRequest(long userId);

    UserView grantRole(long userId, RoleType roleType);

    UserView rejectRole(long userId, RoleType roleType);
}
