
package app.movies.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Item {

    @SerializedName("tmdb")
    @Expose
    private Tmdb tmdb;
    @SerializedName("imdb")
    @Expose
    private Imdb imdb;
    @SerializedName("modified")
    @Expose
    private Modified modified;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("origin_name")
    @Expose
    private String originName;
    @SerializedName("poster_url")
    @Expose
    private String posterUrl;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("episode_current")
    @Expose
    private String episodeCurrent;

    public Tmdb getTmdb() {
        return tmdb;
    }

    public void setTmdb(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    public Imdb getImdb() {
        return imdb;
    }

    public void setImdb(Imdb imdb) {
        this.imdb = imdb;
    }

    public Modified getModified() {
        return modified;
    }

    public void setModified(Modified modified) {
        this.modified = modified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEpisodeCurrent() {
        return episodeCurrent;
    }

    public void setEpisodeCurrent(String episodeCurrent) {
        this.episodeCurrent = episodeCurrent;
    }

}
