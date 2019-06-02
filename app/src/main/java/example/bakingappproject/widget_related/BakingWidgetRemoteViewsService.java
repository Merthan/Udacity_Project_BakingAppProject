package example.bakingappproject.widget_related;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingWidgetRemoteViewsService extends RemoteViewsService {
    @Override public RemoteViewsFactory onGetViewFactory(Intent intent) { return new BakingWidgetRemoteViewsFactory(this.getApplicationContext(), intent); }
}