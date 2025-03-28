package com.androidlesson.domain.main.interfaces;

import com.androidlesson.domain.main.models.NewsEventPreviewItem;
import com.androidlesson.domain.main.models.NewsHeroPreviewItem;

public interface NewsPreviewCallback {
    public void getHeroPreview(NewsHeroPreviewItem newsHeroPreviewItem);
    public void getEventPreview(NewsEventPreviewItem newsEventPreviewItem);
}
