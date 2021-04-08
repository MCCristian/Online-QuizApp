package com.example.licenceapp.Common;

import com.example.licenceapp.Model.Question;
import com.example.licenceapp.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static String categoryId, categoryName;
    public static User currentUser;

    // Create global variable list of Question
    public static List<Question> questionList = new ArrayList<>();
}
