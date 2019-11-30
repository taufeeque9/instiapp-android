package app.insti.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

import app.insti.interfaces.CardInterface;

public class Body implements CardInterface {
    @SerializedName("id")
    private String bodyID;

    @SerializedName("str_id")
    private String bodyStrID;

    @SerializedName("name")
    private String bodyName;

    @SerializedName("short_description")
    private String bodyShortDescription;

    @SerializedName("description")
    private String bodyDescription;

    @SerializedName("image_url")
    private String bodyImageURL;

    @SerializedName("children")
    private List<Body> bodyChildren;

    @SerializedName("parents")
    private List<Body> bodyParents;

    @SerializedName("events")
    private List<Event> bodyEvents;

    @SerializedName("followers_count")
    private int bodyFollowersCount;

    @SerializedName("website_url")
    private String bodyWebsiteURL;

    @SerializedName("blog_url")
    private String bodyBlogURL;

    @SerializedName("user_follows")
    private boolean bodyUserFollows;

    @SerializedName("roles")
    private List<Role> bodyRoles;

    public Body(@NonNull String bodyID) {
        this.bodyID = bodyID;
    }

    public String getBodyID() {
        return bodyID;
    }

    public void setBodyID(String bodyID) {
        this.bodyID = bodyID;
    }

    public String getBodyStrID() {
        return bodyStrID;
    }

    public void setBodyStrID(String bodyStrID) {
        this.bodyStrID = bodyStrID;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    public String getBodyShortDescription() {
        return bodyShortDescription;
    }

    public void setBodyShortDescription(String bodyShortDescription) {
        this.bodyShortDescription = bodyShortDescription;
    }

    public String getBodyDescription() {
        return bodyDescription;
    }

    public void setBodyDescription(String bodyDescription) {
        this.bodyDescription = bodyDescription;
    }

    public String getBodyImageURL() {
        return bodyImageURL;
    }

    public void setBodyImageURL(String bodyImageURL) {
        this.bodyImageURL = bodyImageURL;
    }

    public List<Body> getBodyChildren() {
        return bodyChildren;
    }

    public void setBodyChildren(List<Body> bodyChildren) {
        this.bodyChildren = bodyChildren;
    }

    public List<Body> getBodyParents() {
        return bodyParents;
    }

    public void setBodyParents(List<Body> bodyParents) {
        this.bodyParents = bodyParents;
    }

    public List<Event> getBodyEvents() {
        return bodyEvents;
    }

    public void setBodyEvents(List<Event> bodyEvents) {
        this.bodyEvents = bodyEvents;
    }

    public int getBodyFollowersCount() {
        return bodyFollowersCount;
    }

    public void setBodyFollowersCount(int bodyFollowersCount) {
        this.bodyFollowersCount = bodyFollowersCount;
    }

    public String getBodyWebsiteURL() {
        return bodyWebsiteURL;
    }

    public void setBodyWebsiteURL(String bodyWebsiteURL) {
        this.bodyWebsiteURL = bodyWebsiteURL;
    }

    public String getBodyBlogURL() {
        return bodyBlogURL;
    }

    public void setBodyBlogURL(String bodyBlogURL) {
        this.bodyBlogURL = bodyBlogURL;
    }

    public boolean getBodyUserFollows() {
        return bodyUserFollows;
    }

    public void setBodyUserFollows(boolean bodyUserFollows) {
        this.bodyUserFollows = bodyUserFollows;
    }

    public List<Role> getBodyRoles() {
        return bodyRoles;
    }

    public void setBodyRoles(List<Role> bodyRoles) {
        this.bodyRoles = bodyRoles;
    }

    public long getId() {
        return getBodyID().hashCode();
    }

    public String getTitle() {
        return getBodyName();
    }

    public String getSubtitle() {
        return getBodyShortDescription();
    }

    public String getAvatarUrl() {
        return getBodyImageURL();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Objects.equals(bodyID, body.getBodyID());
    }

    public int getBadge() { return 0; }
}