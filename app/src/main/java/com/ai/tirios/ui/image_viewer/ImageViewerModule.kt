package com.ai.tirios.ui.image_viewer

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class ImageViewerModule {
    @Provides
    internal fun viewModule(dataManager: DataManager) = ImageViewerViewModel(dataManager)
}