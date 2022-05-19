package pl.sggw.foodsharingservice;

public class ErrorMessages {
  public static final String USER_NOT_EXISTS_WITH_ID_MESSAGE = "User with id: %s does not exist!";
  public static final String USER_NOT_EXISTS_WITH_USERNAME_MESSAGE =
      "User with username: %s does not exist!";
  public static final String USER_ALREADY_EXISTS_WITH_USERNAME_MESSAGE =
      "User with username: %s already exists!";
  public static final String ROLE_NOT_EXISTS_WITH_NAME_MESSAGE = "Role %s does not exist!";
  public static final String INVALID_PASSWORD_MESSAGE = "Invalid password!";
  public static final String QUERY_TOO_SHORT_MESSAGE = "Query too short: %s ! Query must be at least 3 chars long.";
  public static final String NOTICE_NOT_EXISTS_WITH_AUTHOR_ID_MESSAGE =
          "Notice with user: %s and id: %s does not exist!";
}
