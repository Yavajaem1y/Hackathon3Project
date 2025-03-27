package com.androidlesson.domain.main.interfaces;

import com.androidlesson.domain.main.models.NewsHeroPreviewItem;
import com.androidlesson.domain.main.models.NewsPreviewItem;

public interface NewsPreviewCallback {
    public void getNewsPreview(NewsHeroPreviewItem newsHeroPreviewItem);
}
