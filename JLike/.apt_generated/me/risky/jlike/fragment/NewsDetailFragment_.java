//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package me.risky.jlike.fragment;

import java.util.List;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import me.risky.jlike.R.layout;
import me.risky.jlike.bean.WelfareDetail;
import me.risky.jlike.service.WelfareService_;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class NewsDetailFragment_
    extends NewsDetailFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_news_detail, container, false);
        }
        return contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        welfareDao = WelfareService_.getInstance_(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static NewsDetailFragment_.FragmentBuilder_ builder() {
        return new NewsDetailFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        progressBar = ((ProgressBar) hasViews.findViewById(me.risky.jlike.R.id.progressBar));
        listview = ((ListView) hasViews.findViewById(me.risky.jlike.R.id.listview));
        reLoadImage = ((ImageView) hasViews.findViewById(me.risky.jlike.R.id.reLoadImage));
        afterInject();
    }

    @Override
    public void loadSuccess(final List<WelfareDetail> list) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                NewsDetailFragment_.super.loadSuccess(list);
            }

        }
        );
    }

    @Override
    public void loadError() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                NewsDetailFragment_.super.loadError();
            }

        }
        );
    }

    @Override
    public void loadFinish() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                NewsDetailFragment_.super.loadFinish();
            }

        }
        );
    }

    @Override
    public void parseResponse(final String response) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    NewsDetailFragment_.super.parseResponse(response);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public NewsDetailFragment build() {
            NewsDetailFragment_ fragment_ = new NewsDetailFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
