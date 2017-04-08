/*
 * IUpdateableModule.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SNOBOTLIB_IUPDATEABLEMODULE_H_
#define SRC_SNOBOTLIB_IUPDATEABLEMODULE_H_


class IUpdateableModule
{
public:
    virtual ~IUpdateableModule()
    {

    }

    /**
     * Gathering and storing current sensor information. Ex. Motor Speed.
     */
    virtual void update() = 0;
};




#endif /* SRC_SNOBOTLIB_IUPDATEABLEMODULE_H_ */
