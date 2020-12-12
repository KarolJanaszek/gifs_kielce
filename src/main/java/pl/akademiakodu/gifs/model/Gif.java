package pl.akademiakodu.gifs.model;

public class Gif {
    private Long id;
    private String name;
    private String tag;
    private Boolean favorite;
    private Long categoryId;

    public Gif() {
    }

    public Gif(String name, Boolean favorite, Long categoryId) {
        this.name = name;
        this.favorite = favorite;
        this.categoryId = categoryId;
        this.tag = null;
    }

    public Gif(String name, String tag, Boolean favorite, Long categoryId) {
        this.name = name;
        this.tag = tag;
        this.favorite = favorite;
        this.categoryId = categoryId;
    }

    public Gif(Long id, String name, String tag, Boolean favorite, Long categoryId) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.favorite = favorite;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return "/gifs/" + name + ".gif";
    }

    public String getDetailsUrl(){
        return "/gif/" + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
