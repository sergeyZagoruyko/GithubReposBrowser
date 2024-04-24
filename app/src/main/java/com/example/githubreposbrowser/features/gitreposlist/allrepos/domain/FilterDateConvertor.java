package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import static com.example.githubreposbrowser.utils.Constants.RANGE_TWO_DOTS;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.GitReposFilterType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class FilterDateConvertor {

    private static final String DATE_FILTER_TYPE_PREFIX = "created:";

    @Inject
    public FilterDateConvertor() {
    }

    public String getDateRange(@NonNull final GitReposFilterType filterType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();

        switch (filterType) {
            case LAST_DAY -> calendar.add(Calendar.DAY_OF_MONTH, -1);
            case LAST_WEEK -> calendar.add(Calendar.WEEK_OF_YEAR, -1);
            case LAST_MONTH -> calendar.add(Calendar.MONTH, -1);
        }

        final Date fromDate = calendar.getTime();
        final String fromDateStr = dateFormat.format(fromDate);
        final String currentDateStr = dateFormat.format(new Date());

        return new StringBuilder(DATE_FILTER_TYPE_PREFIX)
                .append(fromDateStr)
                .append(RANGE_TWO_DOTS)
                .append(currentDateStr)
                .toString();
    }
}
