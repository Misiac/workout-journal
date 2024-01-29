package com.misiac.workoutjournal.util;

public final class MessageProvider {

    //Workout Controller
    public static final String WORKOUT_CREATED = "Workout created successfully";
    public static final String WORKOUT_DELETED = "Workout deleted successfully";
    public static final String WORKOUT_UPDATED = "Workout updated successfully";
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
    public static final String SET_DOES_NOT_BELONG = "This workout exercise does not belong to this user";
    public static final String WORKOUT_DOES_NOT_BELONG = "This workout does not belong to this user";
    public static final String USER_DOES_NOT_EXIST = "User does not exists";


    //Validation
    public static final String REQUEST_NAME_BLANK = "Name can't be blank";
    public static final String DATE_NULL = "Date can't be null";
    public static final String DATE_FUTURE = "Date can't be in future";
    public static final String NAME_SHORT = "Workout name can't be longer than 50 characters";
    public static final String LOAD_NULL = "Load can't be null";
    public static final String LOAD_NEGATIVE = "Load can't be negative";
    public static final String REPS_NULL = "Reps can't be null";
    public static final String REPS_NEGATIVE = "Reps have to be positive";
    public static final String SET_NUMBER_NULL = "Set number can't be null";
    public static final String SET_NUMBER_NEGATIVE = "Set number has to be positive";
    public static final String EXERCISE_TYPE_NULL = "Exercise has to have type";
    public static final String SEQUENCE_NUMBER_NULL = "Sequence number can't be null";
    public static final String SEQUENCE_NUMBER_NEGATIVE = "Sequence number can't be negative";
    public static final String EXERCISE_TYPE_ID_NULL = "Exercise type ID can't be null";
    public static final String EXERCISE_TYPE_ID_NEGATIVE = "Exercise type ID has to be positive number";


}
