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
    public static final String EXERCISE_DOES_NOT_EXIST = "Exercise type does not exist";
    public static final String MUSCLE_GROUP_ALREADY_EXISTS = "Muscle Group category already exist";
    public static final String EQUIPMENT_CATEGORY_ALREADY_EXISTS = "Equipment category already exist";
    public static final String EXERCISE_ALREADY_EXISTS = "Exercise already exist";
    public static final String CATEGORY_DOES_NOT_EXIST = "Category does not exist";
    public static final String EXERCISE_ALREADY_HAS_THIS_CAT = "Exercise already has this category";
    public static final String EXERCISE_DOES_NOT_HAVE_THIS_CAT = "Exercise does not have this category";
    public static final String ADMIN_REQUIRED = "Admin privileges required";
    public static final String WORKOUT_DOES_NOT_EXIST = "Workout does not exist";
    public static final String WORKOUT_EXERCISE_DOES_NOT_EXIST = "Workout exercise does not exist";
    public static final String WE_DOES_NOT_BELONG = "This workout exercise does not belong to this user";
    public static final String WORKOUT_DOES_NOT_BELONG = "This workout does not belong to this user";

    //DTOs
    public static final String REQUEST_NAME_BLANK = "Name can't be blank";
    public static final String EXERCISE_ID_NULL = "Exercise ID can't be null";
    public static final String EXERCISE_ID_NEGATIVE = "Exercise ID has to be positive number";
    public static final String LOAD_NEGATIVE = "Load can't be negative";
    public static final String REPS_NULL = "Reps can't be null";
    public static final String REPS_NEGATIVE = "Reps have to be positive";
    public static final String SET_NUMBER_NULL = "Set number can't be null";
    public static final String SET_NUMBER_NEGATIVE = "Set number has to be positive";
    public static final String DATE_NULL = "Date can't be null";
    public static final String DATE_FUTURE = "Date can't be in future";
    public static final String EXERCISES_EMPTY = "Workout has to have exercises";

}
