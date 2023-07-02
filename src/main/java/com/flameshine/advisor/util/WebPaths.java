package com.flameshine.advisor.util;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;

/**
 * A utility class, that contains web paths of the application.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebPaths {

    public static final String REGISTRATION = "/registration";
    public static final String LOGIN = "/login";
    public static final String HOME = "/home";
    public static final String ADVISE = "/advise";
    public static final String CART = "/cart";
    public static final String ABOUT = "/about";
    public static final String ERROR = "/error";
}
