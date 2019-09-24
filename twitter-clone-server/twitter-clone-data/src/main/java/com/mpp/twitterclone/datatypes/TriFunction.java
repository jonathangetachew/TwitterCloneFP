package com.mpp.twitterclone.datatypes;

/**
 * Created by Jonathan on 9/23/2019.
 */

@FunctionalInterface
public interface TriFunction<T, U, V, W> {
	W apply(T t, U u, V v);
}
