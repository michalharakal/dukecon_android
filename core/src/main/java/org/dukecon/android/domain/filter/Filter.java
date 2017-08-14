package org.dukecon.android.domain.filter;

import java.util.List;

public interface Filter<T> {
    List<T> filter(List<T> events);
}
