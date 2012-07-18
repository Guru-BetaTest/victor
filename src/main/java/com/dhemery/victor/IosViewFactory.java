package com.dhemery.victor;

/**
 * A factory that creates view drivers.
 */
public interface IosViewFactory {
    /**
     * Create a driver that represents the views identified by a query.
     * @param query a query that identifies a set of views.
     */
    IosView view(By query);
}