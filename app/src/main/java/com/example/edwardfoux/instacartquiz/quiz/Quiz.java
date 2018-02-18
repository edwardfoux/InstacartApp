package com.example.edwardfoux.instacartquiz.quiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Quiz implements Parcelable {
    private String quizName;

    private List<String> quizOptions;

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public List<String> getQuizOptions() {
        return quizOptions;
    }

    public void setQuizOptions(List<String> quizOptions) {
        this.quizOptions = quizOptions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quizName);
        dest.writeStringList(this.quizOptions);
    }

    public Quiz() {
    }

    protected Quiz(Parcel in) {
        this.quizName = in.readString();
        this.quizOptions = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel source) {
            return new Quiz(source);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}
