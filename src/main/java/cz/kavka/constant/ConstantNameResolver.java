package cz.kavka.constant;


public class ConstantNameResolver {

    private ConstantNameResolver() {

    }

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String MISSING_FILE = "missingFile";
    public static final String REDIRECT = "redirect:";
    public static final String SOMETHING_WRONG = "Něco se pokazilo... ";


    //=== Projects constants ===
    public static final String PROJECTS_HOME_ADMIN = "/admin/projekty";
    public static final String PROJECT_INDEX_ADMIN_TEMPLATE = "admin/projects/index";
    public static final String PROJECT_INDEX_PUBLIC_TEMPLATE = "public/pages/projects";
    public static final String PROJECT_CREATE_ADMIN_TEMPLATE = "admin/projects/create";
    public static final String PROJECT_EDIT_ADMIN_TEMPLATE = "admin/projects/edit";


    //=== Photo constants ===
    public static final String PHOTO_CREATE_ADMIN = "/admin/foto/novy";
    public static final String PHOTO_INDEX_PUBLIC_TEMPLATE = "public/pages/photos";
    public static final String PHOTO_INDEX_ADMIN_TEMPLATE = "admin/photos/index";
    public static final String PHOTO_CREATE_ADMIN_TEMPLATE = "admin/photos/create-photo";
    public static final String PHOTO_INDEX_ADMIN = "/admin/foto";


    //=== Album constants ===
    public static final String ALBUM_CREATE_ADMIN = "/admin/album/novy";
    public static final String ALBUM_DETAIL_ADMIN = "/admin/album/";
    public static final String ALBUM_CREATE_ADMIN_TEMPLATE = "admin/photos/create-album";
    public static final String ALBUM_DETAIL_ADMIN_TEMPLATE = "admin/photos/album-detail";
    public static final String ALBUM_EDIT_ADMIN_TEMPLATE = "admin/photos/edit-album";


    //=== About me constants ===
    public static final String ABOUT_INDEX_PUBLIC_TEMPLATE = "public/pages/about-me";
    public static final String ABOUT_ME = "aboutMeDto";
    public static final String ABOUT_INDEX_ADMIN_TEMPLATE = "admin/about-me/index";
    public static final String ABOUT_INDEX_ADMIN = "/o-me/admin";
    public static final String ABOUT_EDIT_ADMIN_TEMPLATE = "admin/about-me/edit";

    //=== Contact constants ===
    public static final String CONTACT = "contactDto";
    public static final String CONTACT_INDEX_ADMIN = "/admin/kontakty";
    public static final String CONTACT_INDEX_PUBLIC_TEMPLATE = "public/pages/contacts";
    public static final String CONTACT_INDEX_ADMIN_TEMPLATE = "admin/contacts/index";
    public static final String CONTACT_EDIT_ADMIN_TEMPLATE = "admin/contacts/edit";


    //=== User constants ===
    public static final String USER_LOGIN_TEMPLATE = "public/pages/login";
    public static final String USER_LOGIN = "/login";
    public static final String USER_ADMIN = "/admin";
    public static final String USER_REGISTRATION_TEMPLATE = "public/pages/registration";
    public static final String USER_FORGOTTEN_PASSWORD_TEMP = "public/pages/forgotten-password";
    public static final String USER_ADMIN_PROFILE_TEMP = "admin/user/profile";
    public static final String USER_PASSWORD_CHANGE_TEMP = "admin/user/password-change";
    public static final String USER_PROFILE = "/profile";


}
