/*
 * IClimber.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_CLIMBER_ICLIMBER_H_
#define SRC_CLIMBER_ICLIMBER_H_

#include "SnobotLib/Modules/ISubsystem.h"

class IClimber: public ISubsystem
{
public:
    virtual ~IClimber()
    {

    }

    /**
     * Catch the Rope
     */
    virtual void catchRope() = 0;

    /**
     * Climb the Rope
     */
    virtual void climbRope() = 0;
};



#endif /* SRC_CLIMBER_ICLIMBER_H_ */
