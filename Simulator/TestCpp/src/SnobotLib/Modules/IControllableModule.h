/*
 * IControllableModule.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_ICONTROLLABLEMODULE_H_
#define SRC_SNOBOTLIB_ICONTROLLABLEMODULE_H_


class IControllableModule
{
public:
    virtual ~IControllableModule()
    {
    }

    /**
     * Setting sensor and device states.
     */
    virtual void control() = 0;

    /**
     * Stops all sensors and motors
     */
    virtual void stop() = 0;
};




#endif /* SRC_SNOBOTLIB_ICONTROLLABLEMODULE_H_ */
