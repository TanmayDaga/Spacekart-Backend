package net.in.spacekart.backend;

public class ViewSpaceKart {

    public static class Guest {
    }

    public static class User extends Guest {
    }

    public static class UserAuthenticated extends User {
    }

    public static class Staff extends UserAuthenticated {
    }

    public static class Admin extends Staff {
    }
}
