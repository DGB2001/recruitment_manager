package com.example.recruitmentmanager.Model;

public class ApplicationInfo {
    int id, recruitment_news_id;
    String title, content;
    Integer result;
    RecruitmentInfo recruitment_news;
    CandidateInfo candidate;
    TechnicalInfo master_technical;
    LevelInfo master_level;

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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public CandidateInfo getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateInfo candidate) {
        this.candidate = candidate;
    }

    public TechnicalInfo getMaster_technical() {
        return master_technical;
    }

    public void setMaster_technical(TechnicalInfo master_technical) {
        this.master_technical = master_technical;
    }

    public LevelInfo getMaster_level() {
        return master_level;
    }

    public void setMaster_level(LevelInfo master_level) {
        this.master_level = master_level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRecruitment_news_id() {
        return recruitment_news_id;
    }

    public void setRecruitment_news_id(int recruitment_news_id) {
        this.recruitment_news_id = recruitment_news_id;
    }
}
