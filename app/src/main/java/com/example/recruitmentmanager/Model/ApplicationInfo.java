package com.example.recruitmentmanager.Model;

public class ApplicationInfo {
    String title;
    int result;
    RecruitmentInfo recruitment_news;

    public RecruitmentInfo getRecruitment_news() {
        return recruitment_news;
    }

    public void setRecruitment_news(RecruitmentInfo recruitment_news) {
        this.recruitment_news = recruitment_news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }


}
