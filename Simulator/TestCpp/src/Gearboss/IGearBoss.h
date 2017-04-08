/*
 * IGearBoss.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_GEARBOSS_IGEARBOSS_H_
#define SRC_GEARBOSS_IGEARBOSS_H_

#include "SnobotLib/Modules/ISubsystem.h"

class IGearBoss: public ISubsystem
{
public:
    virtual ~IGearBoss() {}

    /**
     * Moves gear to its high position. This is to get the gear onto the peg.
     */
    virtual void moveGearHigh() = 0;

    /**
     * This is to get the Gear Boss down and away from the gear. This is the
     * second part of the process of putting the gear on the peg.
     */
    virtual void moveGearLow() = 0;

    /**
     * This returns the position of the gear bucket.
     * 
     * @return true for gear high and false for gear low
     */
    virtual bool isGearUp() = 0;
};


#endif /* SRC_GEARBOSS_IGEARBOSS_H_ */
