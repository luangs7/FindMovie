
package com.example.luan.findmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Filme implements Parcelable, RealmModel {

@SerializedName("poster_path")
@Expose
private String posterPath;
@SerializedName("adult")
@Expose
private Boolean adult;
@SerializedName("overview")
@Expose
private String overview;
@SerializedName("release_date")
@Expose
private String releaseDate;
@SerializedName("genre_ids")
@Expose
@Ignore
private List<String> genreIds;
@SerializedName("id")
@Expose
@PrimaryKey
private String id;
@SerializedName("original_title")
@Expose
private String originalTitle;
@SerializedName("original_language")
@Expose
private String originalLanguage;
@SerializedName("title")
@Expose
private String title;
@SerializedName("backdrop_path")
@Expose
private String backdropPath;
@SerializedName("popularity")
@Expose
private String popularity;
@SerializedName("vote_count")
@Expose
private String voteCount;
@SerializedName("video")
@Expose
private Boolean video;
@SerializedName("vote_average")
@Expose
private String voteAverage;

    public String getPosterPath() {
        return "http://image.tmdb.org/t/p/w185/" + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = "http://image.tmdb.org/t/p/w185/" + posterPath;
    }

    public String getBackdropPath() {
        return "http://image.tmdb.org/t/p/w185/" +backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = "http://image.tmdb.org/t/p/w185/" + backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<String> genreIds) {
        this.genreIds = genreIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeValue(this.adult);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeStringList(this.genreIds);
        dest.writeString(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeString(this.popularity);
        dest.writeString(this.voteCount);
        dest.writeValue(this.video);
        dest.writeString(this.voteAverage);
    }

    public Filme() {
    }

    protected Filme(Parcel in) {
        this.posterPath = in.readString();
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.genreIds = in.createStringArrayList();
        this.id = in.readString();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readString();
        this.voteCount = in.readString();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = in.readString();
    }

    public static final Creator<Filme> CREATOR = new Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel source) {
            return new Filme(source);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };
}