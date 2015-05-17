package com.github.state;

import com.github.Robot;

/**
 * <p>
 * </p>
 *
 * @author kisel.nikolay@gmail.com
 * @since May 17, 2015.
 */
public interface State {

    public void move(Robot robot);

    public State getState(Robot robot);
}
