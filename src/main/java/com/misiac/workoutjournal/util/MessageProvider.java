package com.misiac.workoutjournal.util;

public final class MessageProvider {

    //Workout Controller
    public static final String WORKOUT_CREATED = "Workout created successfully";
    public static final String WORKOUT_DELETED = "Workout deleted successfully";
    public static final String EXERCISE_UPDATED = "Exercise updated successfully";
    public static final String EXERCISE_DELETED = "Exercise deleted successfully";

    //Admin Controller

    public static final String CATEGORY_CREATED = "Category created successfully";
    public static final String EXERCISE_CREATED = "Exercise created successfully";
    public static final String CATEGORY_BOUND = "Category successfully bound to the exercise";
    public static final String CATEGORY_UNBOUND = "Category successfully unbound from the exercise";

    //Exceptions
    //Templates
    private static final String ENTITY_DOES_NOT_EXIST = " does not exist";
    private static final String ENTITY_ALREADY_EXISTS = " already exist";

    //Final
    public static final String EXERCISE_DOES_NOT_EXIST = "Exercise type" + ENTITY_DOES_NOT_EXIST;
    public static final String MUSCLE_GROUP_ALREADY_EXISTS = "Muscle Group category" + ENTITY_ALREADY_EXISTS;
    public static final String EQUIPMENT_CATEGORY_ALREADY_EXISTS = "Equipment category" + ENTITY_ALREADY_EXISTS;
    public static final String EXERCISE_ALREADY_EXISTS = "Exercise" + ENTITY_ALREADY_EXISTS;

    public static final String CATEGORY_DOES_NOT_EXIST = "Category " + ENTITY_DOES_NOT_EXIST;
    public static final String EXERCISE_ALREADY_HAS_THIS_CAT = "Exercise already has this category";
    public static final String EXERCISE_DOES_NOT_HAVE_THIS_CAT = "Exercise does not have this category";
    public static final String ADMIN_REQUIRED = "Admin privileges required";

    public static final String WORKOUT_DOES_NOT_EXIST = "Workout" + ENTITY_DOES_NOT_EXIST;
    public static final String WORKOUT_EXERCISE_DOES_NOT_EXIST = "Workout exercise " + ENTITY_DOES_NOT_EXIST;
    public static final String WE_DOES_NOT_BELONG = "This workout exercise does not belong to this user";
    public static final String WORKOUT_DOES_NOT_BELONG = "This workout does not belong to this user";
}
