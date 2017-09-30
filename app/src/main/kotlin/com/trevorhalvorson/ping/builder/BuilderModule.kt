package com.trevorhalvorson.ping.builder

import com.trevorhalvorson.ping.injection.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
open class BuilderModule {

    @PerActivity
    @Provides
    internal fun provideBuilderView(view: BuilderActivity): BuilderContract.View {
        return view
    }

    @PerActivity
    @Provides
    internal fun provideBuilderPresenter(view: BuilderContract.View, api: BuilderApi):
            BuilderContract.Presenter {
        return BuilderPresenter(view, api)
    }

}