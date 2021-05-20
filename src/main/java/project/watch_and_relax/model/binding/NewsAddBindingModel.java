package project.watch_and_relax.model.binding;

public class NewsAddBindingModel {
    private String newsTitle;
    private String category;
    private String newsBody;

    public NewsAddBindingModel() {
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
