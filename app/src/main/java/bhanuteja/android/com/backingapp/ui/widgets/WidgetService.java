package bhanuteja.android.com.backingapp.ui.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by root on 6/7/17.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this.getApplicationContext());
    }
}
