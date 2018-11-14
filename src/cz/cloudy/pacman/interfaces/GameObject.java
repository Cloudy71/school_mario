package cz.cloudy.pacman.interfaces;

import cz.cloudy.pacman.core.GameObjectCollector;

public abstract class GameObject {
    private int id;

    public GameObject() {
        this.id = GameObjectCollector.getRequestedId().getId();
    }

    /**
     * Is invoked when GameObject is created.
     */
    public abstract void create();

    /**
     * Is invoked when new frame is rendered.
     */
    public abstract void update();

    /**
     * Is invoked when destroy is executed on actual GameObject.
     */
    public abstract void dispose();


    /**
     * Invokes destroy process.
     */
    public final void destroy() {
        dispose();
    }

    /**
     * Returns automatically generated id for actual object.
     *
     * @return current object's id
     */
    public final int getId() {
        return id;
    }
}
