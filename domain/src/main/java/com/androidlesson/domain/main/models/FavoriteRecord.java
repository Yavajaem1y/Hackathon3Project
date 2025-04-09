package com.androidlesson.domain.main.models;

import java.util.Objects;

public class FavoriteRecord {
    private String id,name,info,avatarUrl,type;

    public FavoriteRecord() {
    }

    public FavoriteRecord(String id, String name, String info, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.avatarUrl=avatarUrl;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteRecord that = (FavoriteRecord) o;

        // Сравнивай значимые поля, например:
        return id == that.id; // или Objects.equals(id, that.id), если id может быть null
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // тоже зависит от твоей логики
    }
}
