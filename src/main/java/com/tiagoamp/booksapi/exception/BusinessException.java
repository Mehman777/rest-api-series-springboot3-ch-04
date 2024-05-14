package com.tiagoamp.booksapi.exception;

public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public static enum BusinessError implements IError {
        USER_NOT_FOUND("A-1001", "User not found."),
        ROLE_NOT_FOUND("A-1002", "Role not found."),
        DUPLICATE_USER("A-1003", "Duplicate user detected."),
        INVALID_PARAMETER("A-1004", "Invalid parameter error."),
        ACCESS_DENIED("A-1005", "Access denied."),
        GENERIC_ERROR("A-1006", "Unknown error."),
        REPOSITORY_ERROR("A-1007", "Repository error."),
        SERVICE_NOT_FOUND("A-1008", "Service not found."),
        INCORRECT_PASSWORD("A-1009", "Incorrect password."),
        NOT_FOUND("A-1010", "Not found."),
        USER_IS_REMOVED("A-1011", "User is removed."),
        FILE_NOT_FOUND("A-1012", "File not found"),
        TOKEN_NOT_FOUND("A-1013", "Token not found"),
        TIME_EXPIRED("A-1014", "Time expired"),
        PUBLISHED_STATUS("A-1015", "Incorrect published status"),
        EMAIL_IS_NOT_VALID("A-1016", "e-mail is not valid"),
        OBJECT_IS_EMPTY("A-1017", "The inserted object is empty"),
        CONFIRM_TIME_EXPIRED("A-1018", "Time expired.Send e-mail again"),
        USER_ALREADY_EXIST("A-1019", "User already exist"),
        ACCOUNT_IS_ACTIVE("A-1020", "Your account has been activated"),
        ACCOUNT_IS_NOT_ACTIVATED("A-1021", "Your account has not been activated"),
        PUBLISHER_CANT_UPDATE("A-1022", "Pablisher can't update this book"),
        CHOOSE_CATEGORY("A-1023", "Category is not chosen"),
        TOKEN_IS_NULL("A-1024", "Token is null"),
        CATALOG_IS_EMPTY("A-1025", "Catalog is empty"),
        EPUB_TITLE_IS_NULL("A-1026", "EPUB Title can not be null"),
        EPUB_AUTHOR_IS_NULL("A-1027", "EPUB Author can not be null"),
        EPUB_LANGUAGE_IS_NULL("A-1028", "EPUB Language can not be null"),
        EPUB_DESCRIPTION_IS_NULL("A-1029", "EPUB Description not be null"),
        CATALOG_NOT_FOUND("A-1030", "Catalog not found"),
        BOOKS_NOT_FOUND("A-1031", "Books not found"),
        ENTER_USERNAME("A-1032", "Enter your username"),
        USER_OR_LESS_SCHEDULE_NOT_FOUND("A-1033", "User or lesson_schedule not found"),
        BOOK_ALREADY_BOUGHT("A-1034", "You have already bought this book"),
        BOOK_HAS_BEEN_ADDED("A-1035", "Has been added to cart"),
        BOOK_HAS_BEEN_DELETED_FROM_CART("A-1036", "Has been deleted from cart"),
        IS_EMPTY("A-1037", "Is empty"),
        NAME_IS_EMPTY("A-1038", "Name is empty"),
        SURNAME_IS_EMPTY("A-1039", "Surname is empty"),
        MIDDLENAME_IS_EMPTY("A-1040", "Middlename is empty"),
        PASSWORD_IS_EMPTY("A-1041", "Password is empty"),
        STAR_SHOULD_BE_LESS_THAN_FIVE("A-1042", "Value should be equal or less than 5"),
        STAR_SHOULD_BE_GREAT_THAN_ZERO("A-1043", "Value should be greater than 0"),
        COMMENT_SHOULD_BE_LESS("A-1044", "Comment should be equal or less than 1000 characters"),
        BIRTH_DAY_IS_EMPTY("A-1045", "Birth date is empty"),
        USER_IS_NOT_TEACHER("A-1046", "User is not teacher"),
        EMAIL_ALREADY_EXIST("A-1047", "e_mail already exist"),
        USE_ONLY_LATIN_LETTERS("A-1048", "name and surname must be only latin letters"),
        CHECK_CONTENT("A-1049", "Content is free or already bought"),
        ALREADY_BOUGHT("A-1050", "Already bought"),
        MULTIMEDIA_OR_COVER_IS_EMPTY("A-1051", "Multimedia or Cover files is null"),
        NAME_OR_DESCRIPTION_IS_NULL("A-1052", "Name or description is null"),
        THEMATIC_PLAN_CALENDAR("A-1054", "Thematic plan calendar not found"),
        INCORRECT_DATE("A-1055", "Incorrect Date"),
        INCORRECT_TIME("A-1056", "Incorrect time"),
        NOT_DIRECTORY("A-1057", "Not a directory. Do nothing "),
        MUST_BUY("A-1058", "Sorry, you must buy ...  "),
        DONT_USE_SPACES("A-1059", "Don't use spaces in middlename,surname,name"),
        IMAGE_IS_EMPTY("A-1060", "Image is empty"),
        LESS_THAN_100("A-1061", "Page of notebook must be less than or equal to 100"),

        UNKNOWN_DEVICE_TYPE("A-1062", "UNKNOWN DEVICE TYPE"),
        UNKNOWN_CONNECTIVITY("A-1053", "UNKNOWN CONNECTIVITY"),
        UNKNOWN_PROPERTY_TYPE("A-1063", "UNKNOWN PROPERTY TYPE"),

        UNKNOWN_ROLE("A-1070", "Unknown Role"),

        UNKNOWN_ROOM_TYPE("A-1071", "Unknown Room Type"),

        PAGE_EXIST("A-1064", "Page already exist"),
        MORE_THAN_LIMIT_PAGE("A-1065", "Page of notebook must be less than or equal to limit page "),
        BIRTH_DAY_ERROR("A-1066", "Birth date is not compatible"),
        STUD_DIRECTOR_ERROR("A-1067", "Student cannot be Director"),
        STUD_VICE_DIRECTOR_ERROR("A-1068", "Student cannot be Vice-Director"),
        STUD_CLASS_TEACHER_ERROR("A-1069", "Student cannot be Class-Teacher"),
        NOTEBOOK_EXIST_DB_DOESNT_FILESYSTEM("A-1072", "Notebook exists in database but doesn't exist in filesystem"),
        NOTEBOOK_EXIST_FILESYSTEM_DOESNT_DB("A-1073", "Notebook exists in file system but doesn't exist in database"),
        NOTEBOOK_ALREADY_EXIST("A-1074", "Notebook already exist"),
        NOTEBOOK_DOESNT_EXIST("A-1075", "Notebook doesn't exist"),
        PREVIOUSE_PAGE_DOESNT_EXIST("A-1076", "The previous page doesn't exist"),
        PAGE_SEQUENCE_WAS_VIOLATED("A-1077", "Page sequence was violated "),
        PAGE_NUMBER_CANNOT_BE_ZERO("A-1078", "Page number cannot be zero"),
        NOTEBOOK_PATH_AND_USER_IS_DIFFERENT("A-1079", "UserId in the notebook path and current userId is different"),
        FOLDER_DOES_NOT_EXIST("A-1080", "Folder does not exist"),
        FOLDER_COULD_NOT_DELETED("A-1081", "Folder colud not deleted"),
        NOTEBOOK_IS_ARCHIVED("A-1082", "Notebook is archived"),
        NOTEBOOK_ALREADY_ARCHIVED("A-1083", "Notebook already archived"),
        LESSON_SCHEDULE_GROUP_NOT_FOUND("A-1084", "Lesson Schedule Group not found"),
        SCHOOL_IS_EMPTY("A-1085", "School field is empty"),
        CALENDAR_DAY_IS_EMPTY("A-1086", "CalendarDay is empty"),
        THEMATIC_PLAN_IS_EMPTY("A-1087", "ThematicPlan is empty"),
        THEMATIC_PLAN_CALENDAR_EXISTS("A-1088", " combination of 'courseBatch+calendarDay+classTiming+teacher' - parameters exist in ThematicPlanCalendar"),
        YOU_DONT_HAVE_PERMISSION_NOTEBOOK("A-1089", " You don't currently have permission to access this notebook "),
        NOTEBOOK_ALREADY_EXIST_AND_ARCHIVED("A-1090", "Notebook already exists and archived"),
        ARCHIVED_NOTEBOOK_EXIST_DB_DOESNT_FILESYSTEM("A-1091", "Archived notebook exists in database, doesn't exist in filesystem"),
        UNKNOWN_THEMATICPLANTYPE("A-1092", "UNKNOWN THEMATICPLANTYPE"),
        ROOT_CATEGORY_NOT_FOUND("A-1093", "Root category not found"),
        CATEGORY_NOT_FOUND("A-1094", "Category not found"),
        SUB_CATEGORY_NOT_FOUND("A-1095", "Sub category not found"),
        BOARD_CONTENT_NOT_FOUND("A-1096", "Board content not found"),
        PAGE_NOT_FOUND("A-1097", "Page not found"),
        PAGE_NUMBER_EXIST("A-1098", "Page number exist"),
        VERY_LARGE_COVER_SIZE("A-1099", "The cover is too large"),
        FOREIGN_KEY_CONSTRAINT("A-1100", "Foreign key constraint");

        private final String code;
        private final String msg;

        BusinessError(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getMessage() {
            return this.msg;
        }
    }

    public BusinessException(IError error) {
        super(error);
    }

    public BusinessException(IError error, String message) {
        super(error, message);
    }

    public BusinessException(IError error, Throwable cause) {
        super(error, cause);
    }
}
